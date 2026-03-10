import {Component, inject, input, output, signal} from '@angular/core';
import {EventControllerService} from "@app/api/api/eventController.service";
import {MessageService} from "primeng/api";
import {EventDto} from "@app/api/model/eventDto";
import {FormsModule, NgForm} from "@angular/forms";
import {Dialog} from "primeng/dialog";
import {Button} from "primeng/button";
import {Message} from "primeng/message";
import {DatePicker} from "primeng/datepicker";
import {InputText} from "primeng/inputtext";

@Component({
    selector: 'app-update-event',
    imports: [
        Dialog,
        FormsModule,
        Button,
        Message,
        DatePicker,
        InputText
    ],
    templateUrl: './update-event.html',
    styleUrl: './update-event.css',
})
export class UpdateEvent {
    eventService = inject(EventControllerService);
    visible = signal<boolean>(false);
    messageService = inject(MessageService);
    refreshTable = output<void>();

    eventData = input.required<EventDto>();
    editEvent: EventDto = {title: "", startTime: "", endTime: "", locationId: "", hobbyGroupId: ""};

    openDialog() {
        this.editEvent = { ...this.eventData() };

        if (this.editEvent.startTime) {
            this.editEvent.startTime = new Date(this.editEvent.startTime) as any;
        }
        if (this.editEvent.endTime) {
            this.editEvent.endTime = new Date(this.editEvent.endTime) as any;
        }

        this.visible.set(true);
    }

    onSubmit(form: NgForm) {
        if (form.valid && this.editEvent.id) {
            this.eventService.updateEvent(this.editEvent.id, this.editEvent).subscribe({
                next: (response) => {
                    this.visible.set(false);
                    this.refreshTable.emit();

                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Event successfully created!'
                    });
                },
                error: (err) => {
                    console.error('Error creating event', err);
                    this.messageService.add({severity: 'error', summary: 'Error', detail: 'Failed to update event.'});
                }
            });
        }
    }
}
