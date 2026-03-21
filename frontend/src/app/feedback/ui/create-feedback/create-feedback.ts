import {Component, inject, output, signal} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {Message} from "primeng/message";
import {InputTextModule} from 'primeng/inputtext';
import {DatePicker} from 'primeng/datepicker';
import {Dialog} from "primeng/dialog";
import {Button} from "primeng/button";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {Textarea} from "primeng/textarea";
import {ToastService} from "../../../toast-service/toast-service";

@Component({
    selector: 'app-create-feedback',
    standalone: true,
    imports: [
        Message,
        InputTextModule,
        DatePicker,
        FormsModule,
        Button,
        Dialog,
        Textarea
    ],
    templateUrl: './create-feedback.html',
})
export class CreateFeedback {
    feedbackService = inject(SystemFeedbackControllerService);
    visible = signal<boolean>(false);
    refreshTable = output<void>();
    toastService = inject(ToastService);
    newFeedback: SystemFeedbackDTO = {
        email: "",
        message: "",
        createdAt: ""
    };

    onSubmit(form: NgForm) {
        if (form.valid) {
            this.feedbackService.createSystemFeedback(this.newFeedback).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    form.resetForm();
                    this.toastService.showSuccess("Feedback created succesfully");
                },
                error: (err) => {
                    this.toastService.showError("Error creating feedback");
                }
            });
        }
    }
}

