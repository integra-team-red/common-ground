import {Component, inject, input, output, signal} from '@angular/core';
import {Button} from "primeng/button";
import {EventControllerService} from "@app/api/api/eventController.service";
import {MessageService} from "primeng/api";
import {EventDto} from "@app/api/model/eventDto";
import {Dialog} from "primeng/dialog";

@Component({
    selector: 'app-delete-event',
    imports: [
        Button,
        Dialog
    ],
    templateUrl: './delete-event.html',
    styleUrl: './delete-event.css',
})
export class DeleteEvent {
    eventService = inject(EventControllerService);
    messageService = inject(MessageService);
    eventData = input.required<EventDto>();
    refreshTable = output<void>();
    visible = signal<boolean>(false);

    openDialog() {
        this.visible.set(true);
    }

    confirmDelete() {
        const eventId = this.eventData().id;
        if (eventId) {
            this.eventService.deleteEvent(eventId).subscribe({
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
