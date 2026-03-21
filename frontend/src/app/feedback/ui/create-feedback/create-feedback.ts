import {Component, inject, output, signal} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {Message} from "primeng/message";
import {InputTextModule} from 'primeng/inputtext';
import {DatePicker, DatePickerModule} from 'primeng/datepicker';
import {Dialog} from "primeng/dialog";
import {Button} from "primeng/button";
import {MessageService} from "primeng/api";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {Textarea} from "primeng/textarea";

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
    messageService = inject(MessageService);
    refreshTable = output<void>();

    newFeedback: SystemFeedbackDTO = {
        email: "",
        message: "",
        createdAt: ""
    };

    onSubmit(form: NgForm) {
        if (form.valid) {
            this.feedbackService.create(this.newFeedback).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    form.resetForm();

                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Feedback successfully created!'
                    });
                },
                error: (err) => {
                    console.error('Error creating feedback', err);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to create feedback.'
                    });
                }
            });
        }
    }
}

