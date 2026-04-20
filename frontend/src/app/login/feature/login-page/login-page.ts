import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {LoginForm} from "../../ui/login-form/login-form";
import {AuthControllerService} from "@app/api/api/authController.service";
import {LoginRequest} from "@app/api/model/loginRequest";
import {Router} from "@angular/router";
import {ToastService} from "../../../toast-service/toast-service";
import {UserDetailsService} from "../../../services/UserDetailsService/user-details-service";

@Component({
    selector: 'app-login-page',
    imports: [
        FormsModule,
        LoginForm,
    ],
    templateUrl: './login-page.html',
})
export class LoginPage {
    authService = inject(AuthControllerService);
    userDetailsService = inject(UserDetailsService);
    toastService = inject(ToastService);
    router = inject(Router);

    protected onSubmit(request: LoginRequest) {
        this.authService.loginUser(request).subscribe({
            next: async (res) => {
                await this.userDetailsService.loadCurrentUser();
                this.toastService.showSuccess("Logged in successfully!");

                if (res?.matrixWarningMessage) {
                    this.toastService.showError(res.matrixWarningMessage);
                }

                setTimeout(() => {
                    this.router.navigate(['/home']).then(() => {
                    });
                }, 1500);
            },
            error: () => {
                this.toastService.showError("Username or password are incorrect.");
            }
        })
    }
}
