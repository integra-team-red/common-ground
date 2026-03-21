import {Component, inject, input, output, signal} from "@angular/core";
import {InputTextModule} from "primeng/inputtext";
import {DatePickerModule} from "primeng/datepicker";
import {FormsModule, NgForm} from "@angular/forms";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {Button} from "primeng/button";
import {Dialog} from "primeng/dialog";
import {Message} from "primeng/message";
import{Textarea} from "primeng/textarea";
import{ToastService} from "../../../toast-service/toast-service";

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
    refreshTable = output<void>();
    feedbackData = input.required<SystemFeedbackDTO>();
    toastService=inject(ToastService);
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
            this.feedbackService.updateSystemFeedback(this.newFeedback.id, this.newFeedback).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    this.toastService.showSuccess("Succesfully updated feedback")
                },
                error: (err) => {
                    console.error(err);
                    this.toastService.showError("Could not update feedback ")
                }
            });
        }
    }
}
