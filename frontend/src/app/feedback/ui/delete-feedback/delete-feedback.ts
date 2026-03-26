import {Component, inject, input, output, signal} from '@angular/core';
import {Button} from "primeng/button";
import {MessageService} from "primeng/api";
import {Dialog} from "primeng/dialog";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";

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
    messageService = inject(MessageService);
    refreshTable = output<void>();
    feedbackData = input.required<SystemFeedbackDTO>();

    openDialog() {
        this.visible.set(true);
    }

    confirmDelete() {
        const feedbackId = this.feedbackData().id;
        if (feedbackId) {
            this.feedbackService._delete(feedbackId).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Deleted',
                        detail: 'Event deleted!'
                    });
                }
            });
        }
    }
}
