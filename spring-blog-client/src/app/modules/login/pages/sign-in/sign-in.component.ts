import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { BaseError } from 'src/app/core/interface/core.interface';
import { PasswordValidation, UsernameValidation } from 'src/app/core/validators/Validator';
import { AuthenticateService } from '../../shared/authenticate.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  signInForm: FormGroup;
  errorResponse: BaseError = new BaseError();

  isLoading:Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authenticateService: AuthenticateService,
    private router: Router) {

    this.signInForm = this.formBuilder.group(
      {
        usernameOrEmail: this.formBuilder.control('', UsernameValidation),
        password: this.formBuilder.control('', PasswordValidation)
      }
    )
  }

  ngOnInit(): void {
  }

  get signInControls(): { [key: string]: AbstractControl } {
    return this.signInForm.controls;
  }

  onSubmit() {
    this.isLoading = true;
    this.authenticateService.signIn(this.signInForm.value).subscribe(result => {
      this.isLoading = false;
      this.router.navigate([""]);
    }, error => {
     this.isLoading = false;
      this.errorResponse = error;     
    })
  }

  clearForm(){
    this.signInForm.reset();
    this.router.navigate(['']);
  }

  clearBaseError(){
    this.errorResponse = new BaseError();
  }
}
