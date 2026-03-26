import {Component, inject, input, output, signal} from "@angular/core";
import {InputTextModule} from "primeng/inputtext";
import {DatePickerModule} from "primeng/datepicker";
import {FormsModule, NgForm} from "@angular/forms";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";
import {MessageService} from "primeng/api";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {Button} from "primeng/button";
import {Dialog} from "primeng/dialog";
import {Message} from "primeng/message";
import{Textarea} from "primeng/textarea";

@Component({
    selector: 'app-update-feedback',
    standalone: true,
    imports: [
        InputTextModule,
        DatePickerModule,
        FormsModule,
        Button,
        Dialog,
        Message,
        Textarea
    ],
    templateUrl: './update-feedback.html',
})
export class UpdateFeedback{
    feedbackService = inject(SystemFeedbackControllerService);
    visible = signal<boolean>(false);
    messageService = inject(MessageService);
    refreshTable = output<void>();
    feedbackData = input.required<SystemFeedbackDTO>();


    newFeedback: SystemFeedbackDTO = {email: "", message: "", createdAt: ""};

    openDialog() {
        this.newFeedback = { ...this.feedbackData() };

        if (this.newFeedback.createdAt) {
            this.newFeedback.createdAt = new Date(this.newFeedback.createdAt) as any;
        }
        this.visible.set(true);
    }
    onSubmit(form: NgForm) {
        if (form.valid && this.newFeedback.id) {
            this.feedbackService.update(this.newFeedback.id, this.newFeedback).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succes',
                        detail: 'The feedback has updated'
                    });
                },
                error: (err) => {
                    console.error(err);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'The data could not be saved'
                    });
                }
            });
        }
    }


}
