import { AbstractControl, FormGroup, Validators } from "@angular/forms";

export const NameValidation = [
    Validators.required,
    Validators.minLength(4),
    Validators.maxLength(50),
]

export  const UsernameValidation = [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(15),
    Validators.pattern("^[-a-zA-Z0-9\_\-]+(\s+[-a-zA-Z0-9\_\-]+)*$")
]

export const PasswordValidation = [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(20)
]

export const confirmPassword = [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(20)
]

export const EmailValidation = [
    Validators.required,
    Validators.email,
    Validators.maxLength(40)
]

export const TitleValidation = [
    Validators.required,
    Validators.maxLength(100),
    Validators.nullValidator
]

export const ContentValidation = [
    Validators.required,
    Validators.maxLength(5000),
    Validators.nullValidator
]

export function ConfirmedValidation(controlName: string, matchingControlName:string){
    return (formGroup:FormGroup) => {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];
        if(matchingControl.errors && !matchingControl.errors.confirmedValidator) {
            return ;
        }
        if(control.value !== matchingControl.value){
            matchingControl.setErrors({confirmedValidation:true});
        }
        else {
            matchingControl.setErrors(null);
        }
    
    }
}

export function passwordMatch(control: AbstractControl){

    const password = control.get('password') || control.get('newPassword');
    const confirmPassword = control.get('confirmPassword');
   
    if(password?.value !== confirmPassword?.value){
        return confirmPassword?.setErrors({notMatch:true});
    }
    return null;
}

