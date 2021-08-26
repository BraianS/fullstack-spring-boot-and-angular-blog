import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AppResponse, BaseError } from 'src/app/core/interface/core.interface';
import { EmailValidation, NameValidation, passwordMatch, PasswordValidation } from 'src/app/core/validators/Validator';
import { AuthenticateService } from 'src/app/modules/login/shared/authenticate.service';
import { TokenService } from 'src/app/modules/login/shared/token.service';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-edit-user-profile',
  templateUrl: './edit-user-profile.component.html',
  styleUrls: ['./edit-user-profile.component.scss']
})
export class EditUserProfileComponent implements OnInit {

  nameForm: FormGroup;
  passwordForm: FormGroup;
  emailForm: FormGroup;
  isLoading: Boolean = false;
  isLoadingEmail: Boolean = false;
  isLoadingPassword: Boolean = false;

  emailErrorResponse: BaseError = new BaseError();
  passwordErrorResponse: BaseError = new BaseError();

  emailResponse: AppResponse = {} as AppResponse;
  passwordResponse: AppResponse = {} as AppResponse;

  username: string = "";
  user: any;

  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private authenticateService: AuthenticateService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private toastrService: ToastrService,
    private router: Router
  ) {

    this.passwordForm = this.formBuilder.group({
      oldPassword: ['', PasswordValidation],
      newPassword: ['', PasswordValidation],
      confirmPassword: this.formBuilder.control('', PasswordValidation),
    }, {
      validators: [passwordMatch]
    })

    this.emailForm = this.formBuilder.group({
      password: ['', PasswordValidation],
      email: ['', EmailValidation],
    })

    this.nameForm = this.formBuilder.group({
      name: ['', NameValidation]
    })
  }

  get passwordControl(): { [key: string]: AbstractControl } {
    return this.passwordForm.controls;
  }

  get nameControl(): { [key: string]: AbstractControl } {
    return this.nameForm.controls;
  }

  get emailControl(): { [key: string]: AbstractControl } {
    return this.emailForm.controls;
  }

  ngOnInit(): void {
    this.activatedRoute.parent?.params.subscribe((params: any) => {
      this.username = params.username;
      this.findUserByUsername();
    })
  }

  findUserByUsername() {
    this.userService.findByUsername(this.username).subscribe(response => {
      this.nameForm.controls['name'].setValue(this.user.name);
    })
  }

  updatePassword() {
    this.isLoadingPassword = true;
    this.userService.updatePassword(this.username, this.passwordForm.value).subscribe(response => {
      this.isLoadingPassword = false;
      this.passwordResponse = response;
      setTimeout(() => {
        this.passwordResponse = {} as AppResponse;
      }, 3000);

      this.cancelPasswordForm();
    }, (error => {
      this.isLoadingPassword = false;
      this.passwordErrorResponse = error;
    }))
  }

  cancelPasswordForm() {
    this.passwordForm.reset();
  }

  clearPasswordError() {
    this.passwordErrorResponse = new BaseError();
  }

  updateEmail() {
    this.isLoadingEmail = true;
    this.userService.updateEmail(this.username, this.emailForm.value).subscribe(response => {

      this.isLoadingEmail = false;
      this.cancelEmailForm();

      this.emailResponse = response;
      setTimeout(() => {
        this.emailResponse = {} as AppResponse;
      }, 3000);
    }, (error => {
      this.isLoadingEmail = false;
      this.emailErrorResponse = error;
    }))
  }

  cancelEmailForm() {
    this.emailForm.reset();
  }

  clearEmailError() {
    this.emailErrorResponse = new BaseError();
  }

  updateName() {
    this.isLoading = true;
    this.userService.updateName(this.username, this.nameForm.value).subscribe(response => {
      this.isLoading = false;
    }, (error => {
      this.isLoading = false;
    }))
  }

  cancelNameForm() {
    this.nameForm.controls['name'].setValue(this.user.name);
  }

  deleteAccount() {
    this.userService.findByUsername(this.username).subscribe(user => {
      this.user = user;
      this.userService.deleteById(this.user.id).subscribe(response => {
        this.toastrService.success(response.message || response.detail);
        this.authenticateService.logout();
       
      })
    })

  }

}
