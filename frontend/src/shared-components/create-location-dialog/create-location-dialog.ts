import {Component, effect, model, output, signal,} from '@angular/core';
import {DialogModule} from "primeng/dialog";
import {InputText} from "primeng/inputtext";
import {Button} from "primeng/button";
import {LocationDto} from "@app/api/model/locationDto";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-create-edit-location-dialog',
    imports: [DialogModule, InputText, Button, FormsModule],
    templateUrl: './create-location-dialog.html',
    styleUrl: './create-location-dialog.css',
    standalone: true,
})
export class CreateLocationDialog {
    visible = model<boolean>(false);
    saved = output<LocationDto>();

    editableLocation = signal<LocationDto>({});

    constructor() {
        effect(() => {
            this.editableLocation.set({
                name: '',
                latitude: undefined,
                longitude: undefined
            });
        });
    }

    close() {
        this.visible.set(false);
    }

    onDialogSubmit() {
        if (this.isValid()) {
            this.saved.emit(this.editableLocation());
            this.close();
        }
    }

    coordinatesValid(lat: number | undefined, long: number | undefined): boolean {
        return (lat != undefined &&
            lat >= -90 &&
            lat <= 90 &&
            long != undefined &&
            long <= 180 &&
            long >= -180);
    }

    isValid(): boolean {
        return !!(this.editableLocation.name &&
            this.coordinatesValid(this.editableLocation().latitude, this.editableLocation().longitude));
    }
}
