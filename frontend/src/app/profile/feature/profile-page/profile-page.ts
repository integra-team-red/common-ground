import {Component, inject, OnInit, signal} from '@angular/core';
import {Card} from "primeng/card";
import {UserDetailsService} from "../../../services/UserDetailsService/user-details-service";
import {DatePipe} from "@angular/common";
import {CreateLocationDialog} from "../../../../shared-components/create-location-dialog/create-location-dialog";
import {LocationDto} from "@app/api/model/locationDto";
import {ToastService} from "../../../toast-service/toast-service";
import {Button} from "primeng/button";
import {LocationSelectionDropdown} from "../../ui/location-selection-dropdown/location-selection-dropdown";
import {LocationControllerService} from "@app/api/api/locationController.service";

@Component({
    selector: 'app-profile-page',
    imports: [
        Card,
        DatePipe,
        CreateLocationDialog,
        Button,
        LocationSelectionDropdown
    ],
    templateUrl: './profile-page.html',
})
export class ProfilePage implements OnInit {
    private userDetailsService = inject(UserDetailsService);
    private locationService= inject(LocationControllerService)
    private toastService = inject(ToastService);

    userId =  this.userDetailsService.getUserId;
    selectedLocation = this.userDetailsService.selectedLocation;
    locations = this.userDetailsService.getUserLocations;
    user = this.userDetailsService.getCurrentUser() ?? {username: "", email: "", joinedDate: ""};
    openCreateDialogVisible = false;


    ngOnInit() {
        this.userDetailsService.refreshLocations();
    }


    openCreateDialog() {
        this.openCreateDialogVisible = true;
    }

    handleCreateDialogSubmit(locationDto: LocationDto) {
        this.locationService.createLocation(locationDto).subscribe({
            next: () => {
                this.userDetailsService.refreshLocations();
                this.openCreateDialogVisible = false;
                this.toastService.showSuccess('Location created successfully.');
            },
            error: (err) => {
                console.error('Error creating location:', err);
                this.toastService.showError('Could not create location.');
            }
        });
    }

}
