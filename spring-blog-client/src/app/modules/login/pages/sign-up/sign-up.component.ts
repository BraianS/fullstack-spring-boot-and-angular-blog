import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { BaseError } from 'src/app/core/interface/core.interface';
import { EmailValidation, NameValidation, passwordMatch, PasswordValidation, UsernameValidation } from 'src/app/core/validators/Validator';
import { UserService } from 'src/app/modules/user/shared/user.service';
import { AuthenticateService } from '../../shared/authenticate.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  signUpForm: FormGroup;
  errorResponse: BaseError = new BaseError();
  emailResponse: BaseError = new BaseError();
  usernameResponse: BaseError = new BaseError();


  isLoading:Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authenticateService: AuthenticateService,
    private userService: UserService,
    private router: Router) {

    this.signUpForm = this.formBuilder.group(
      {
        name: this.formBuilder.control(null, NameValidation),
        username: this.formBuilder.control('', UsernameValidation),
        password: this.formBuilder.control('', PasswordValidation),
        confirmPassword: this.formBuilder.control('', PasswordValidation),
        email: this.formBuilder.control('', EmailValidation)

      },
      {
        validators: [passwordMatch]
      }
    )
  }

  ngOnInit(): void {

  }

  get signUpControls(): { [key: string]: AbstractControl } {
    return this.signUpForm.controls;
  }

  onSubmit() {
    this.isLoading = true;
    this.authenticateService.signUp(this.signUpForm.value).subscribe(result => {
      this.isLoading = false;
      this.router.navigate(['/login/signin'])
    },(error => {
      this.errorResponse = error;
    }))

  }

  checkPasswords(group: FormGroup) {
    const password = group.controls.password.value;
    const confirmPassword = group.controls.confirmPassword.value;

    return password === confirmPassword ? null : { notMatch: true };
  }

  checkUsernameAvailability(username: string) {
    if (this.signUpControls.username.valid) {
      this.userService.checkUsernameAvailability(username).subscribe(response => {
        console.log(this.usernameResponse);
        this.usernameResponse = response;
      }, error => {
        console.log(this.usernameResponse);
        this.usernameResponse = error;
      })
    }
  }

  checkEmailAvailability(email: string) {
    if (this.signUpControls.email.valid) {
      this.userService.checkEmailAvailability(email).subscribe(response => {
        console.log(this.emailResponse);
        this.emailResponse = response;
      }, error => {
        console.log(this.emailResponse);
        this.emailResponse = error;
      })
    }
  }

}
