import {Component, inject, output, signal} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {Message} from "primeng/message";
import {InputTextModule} from 'primeng/inputtext';
import {DatePickerModule} from 'primeng/datepicker';
import {EventControllerService} from "@app/api/api/eventController.service";
import {EventDto} from "@app/api/model/eventDto";
import {Dialog} from "primeng/dialog";
import {Button} from "primeng/button";
import {ToastService} from "../../../toast-service/toast-service";

@Component({
    selector: 'app-create-event',
    standalone: true,
    imports: [
        Message,
        InputTextModule,
        DatePickerModule,
        FormsModule,
        Button,
        Dialog
    ],
    templateUrl: './create-event.html',
})
export class CreateEvent {
    eventService = inject(EventControllerService);
    visible = signal<boolean>(false);
    toastService = inject(ToastService);
    refreshTable = output<void>();

    newEvent: EventDto = {
        title: "",
        startTime: "",
        endTime: "",
        locationId: "",
        hobbyGroupId: ""
    };

    onSubmit(form: NgForm) {
        if (form.valid) {
            this.eventService.createEvent(this.newEvent).subscribe({
                next: (response) => {
                    this.visible.set(false);
                    this.refreshTable.emit();
                    form.resetForm();

                    this.toastService.showSuccess("Event created");
                },
                error: () => {
                    this.toastService.showError("Could not create event.");
                }
            });
        }
    }
}
