import {Component, effect, input, model, output, signal} from '@angular/core';
import {DialogModule} from "primeng/dialog";
import {Button} from "primeng/button";
import {LocationDto} from "@app/api/model/locationDto";

@Component({
    selector: 'app-delete-location-dialog',
    imports: [DialogModule, Button],
    templateUrl: './delete-location-dialog.html',
    styleUrl: './delete-location-dialog.css',
    standalone: true,
})
export class DeleteLocationDialog {
    visible = model<boolean>(false);
    locationDto = input<LocationDto>({});
    confirmed = output<string>();

    editableLocation = signal<LocationDto>({});

    constructor() {
        effect(() => {
            const currentLocation = this.locationDto();
            this.editableLocation.set({...currentLocation});
        });
    }

    close() {
        this.visible.set(false);
    }

    onConfirm() {
        const location = this.editableLocation();
        if (location?.id) {
            this.confirmed.emit(location.id);
        }
        this.close();
    }
}
