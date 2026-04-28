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
import {AutoComplete, AutoCompleteCompleteEvent} from 'primeng/autocomplete';
import {LocationDto} from '@app/api/model/locationDto';
import {HobbyGroupDto} from '@app/api/model/hobbyGroupDto';
import {LocationControllerService} from '@app/api/api/locationController.service';
import {HobbyGroupControllerService} from '@app/api/api/hobbyGroupController.service';

@Component({
    selector: 'app-create-event',
    standalone: true,
    imports: [
        Message,
        InputTextModule,
        DatePickerModule,
        FormsModule,
        Button,
        Dialog,
        AutoComplete
    ],
    templateUrl: './create-event.html',
})
export class CreateEvent {
    eventService = inject(EventControllerService);
    locationService = inject(LocationControllerService);
    hobbyService = inject(HobbyGroupControllerService);
    visible = signal<boolean>(false);
    toastService = inject(ToastService);
    refreshTable = output<void>();

    locationValue = signal<string>('');
    locationItems = signal<LocationDto[]>([]);

    hobbyValue = signal<string>('');
    hobbyItems = signal<HobbyGroupDto[]>([]);


    newEvent: EventDto = {
        title: "",
        startTime: "",
        endTime: "",
        locationId: "",
        hobbyGroupId: ""
    };

    onSubmit(form: NgForm) {
        if (form.valid) {
            console.log(form);
            this.eventService.createEvent(form.form.value).subscribe({
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


    protected searchLocations($event: AutoCompleteCompleteEvent) {
        console.log($event);
        this.locationService.getByName($event.query, { page: 0, size: 10 }).subscribe({
            next: (response) => {
                this.locationItems.set(response.content ?? []);
            }
        })
    }

    protected searchHobbyGroups($event: AutoCompleteCompleteEvent) {
        console.log($event);
        this.hobbyService.filterAllHobbyGroupsByName($event.query, { page: 0, size: 10 }).subscribe({
            next: (response) => {
                this.hobbyItems.set(response.content ?? []);
            }
        })
    }
}
