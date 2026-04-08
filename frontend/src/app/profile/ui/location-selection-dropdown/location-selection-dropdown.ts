import { Component, input, model } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LocationDto } from "@app/api/model/locationDto";

@Component({
    selector: 'app-location-selection-dropdown',
    standalone: true,
    imports: [FormsModule, CommonModule],
    templateUrl: './location-selection-dropdown.html',
})
export class LocationSelectionDropdown {
    locations = input<LocationDto[]>([]);
    placeholder = input<string>('Choose a location');
    value = model<LocationDto | undefined>();


}
