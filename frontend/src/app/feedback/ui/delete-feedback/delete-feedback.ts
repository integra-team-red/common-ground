import {Component, inject, input, output, signal} from '@angular/core';
import {Button} from "primeng/button";
import {Dialog} from "primeng/dialog";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";
import{ToastService} from "../../../toast-service/toast-service";

@Component({
    selector: 'app-delete-feedback',
    imports: [
        Button,
        Dialog
    ],
    templateUrl: './delete-feedback.html',
})
export class DeleteEvent {
    feedbackService = inject(SystemFeedbackControllerService);
    visible = signal<boolean>(false);
    refreshTable = output<void>();
    feedbackData = input.required<SystemFeedbackDTO>();
    toastService=inject(ToastService);

    openDialog() {
        this.visible.set(true);
    }

    confirmDelete() {
        const feedbackId = this.feedbackData().id;
        if (feedbackId) {
            this.feedbackService.deleteSystemFeedback(feedbackId).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    this.toastService.showSuccess("Succesfully deleted feedback ");
                },
                error: () => {
                this.toastService.showError("Could not delete feedback ");
            }
            });
        }
    }
}
