import {Component, output} from '@angular/core';
import {ButtonDirective, ButtonIcon, ButtonLabel} from "primeng/button";
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {InputText} from "primeng/inputtext";
import {Message} from "primeng/message";
import {LoginRequest} from "@app/api/model/loginRequest";
import {Password} from "primeng/password";
import {RouterLink} from "@angular/router";

@Component({
    selector: 'app-login-form',
    imports: [
        FormsModule,
        InputText,
        Message,
        ReactiveFormsModule,
        ButtonDirective,
        ButtonIcon,
        ButtonLabel,
        Password,
        RouterLink
    ],
    templateUrl: './login-form.html',
})
export class LoginForm {
    request: LoginRequest = {
        username: "",
        password: "",
    }
    onLogin = output<LoginRequest>();

    protected onSubmit(form: NgForm): void {
        if (!form.valid) {
            form.control.markAllAsTouched();
            return;
        }

        this.onLogin.emit(this.request);
    }
}
