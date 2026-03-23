import {Component, inject} from '@angular/core';
import {CheckboxModule} from "primeng/checkbox";
import {CommonModule} from "@angular/common";
import {
    AbstractControl,
    FormControl,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrors,
    Validators
} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {Password} from "primeng/password";
import {Message} from "primeng/message";
import {RegisterRequest} from "@app/api/model/registerRequest";
import {AuthControllerService} from "@app/api/api/authController.service";
import {Router, RouterLink} from "@angular/router";
import {ToastService} from "../toast-service/toast-service";

const passwordValidators = [
    Validators.required,
    Validators.minLength(8),
    Validators.pattern(/^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])/)
];

@Component({
    selector: 'app-registration',
    templateUrl: './registration.html',
    imports: [
        CommonModule,
        FormsModule,
        ButtonModule,
        CheckboxModule,
        InputTextModule,
        Password,
        ReactiveFormsModule,
        Message,
        RouterLink,
    ],
})

export class Registration {
    toastService = inject(ToastService);
    authService = inject(AuthControllerService);
    router = inject(Router);

    passwordMatchValidator = (control: AbstractControl): ValidationErrors | null => {
        const password = control.get('password');
        const confirmPassword = control.get('confirmPassword');
        if (password && confirmPassword && password.value !== confirmPassword.value) {
            return {passwordMismatch: true};
        }
        return null;
    }

    registerForm = new FormGroup({
        username: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', passwordValidators),
        confirmPassword: new FormControl('', passwordValidators),
    }, {
        validators: this.passwordMatchValidator
    });

    onSubmit() {
        if (!this.registerForm.valid) {
            this.registerForm.markAllAsTouched();
        } else {
            const requestPayload: RegisterRequest = {
                username: this.registerForm.value.username!,
                email: this.registerForm.value.email!,
                password: this.registerForm.value.password!
            };
            this.authService.registerNewUser(requestPayload).subscribe({
                next: () => {
                    this.toastService.showSuccess("User registered successfully!");
                    this.registerForm.reset();
                    setTimeout(() => {
                        this.router.navigate(['/login']).then(() => {});
                    }, 1500);
                },
                error: (err) => {
                    const backendError = typeof err.error === 'string' ? err.error : (err.error?.message || '');
                    if (backendError.toLowerCase().includes('username') ||
                        backendError.toLowerCase().includes('exists')) {
                        this.registerForm.get('username')?.setErrors({usernameTaken: true});
                        this.toastService.showError("Username already exists!");
                        this.toastService.showError("Could not register user!");
                    }
                }
            })
        }
    }
}
