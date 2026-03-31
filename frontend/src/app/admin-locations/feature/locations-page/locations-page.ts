import {Component, inject, OnInit, signal} from '@angular/core';
import {Button} from "primeng/button";
import {SearchBar} from "../../ui/search-bar/search-bar";
import {LocationsTable} from "../../ui/locations-table/locations-table";
import {CreateEditLocationDialog} from "../../ui/create-edit-location-dialog/create-edit-location-dialog";
import {DeleteLocationDialog} from "../../ui/delete-location-dialog/delete-location-dialog";
import {LocationControllerService} from "@app/api/api/locationController.service";
import {Pageable} from "@app/api/model/pageable";
import {LocationDto} from "@app/api/model/locationDto";
import {PageLocationDto} from "@app/api/model/pageLocationDto";
import {ToastService} from "../../../toast-service/toast-service";

@Component({
    selector: 'app-admin-locations',
    imports: [Button, SearchBar, LocationsTable, CreateEditLocationDialog, DeleteLocationDialog],
    templateUrl: './locations-page.html',
    styleUrl: './locations-page.css',
    standalone: true,
})
export class LocationsPage implements OnInit {

    locations = signal<LocationDto[]>([]);
    totalElements = signal(0);
    rows = signal(10);
    currentPage = signal(0);

    locationService = inject(LocationControllerService)
    toastService = inject(ToastService)

    selectedLocation = signal<LocationDto>({});
    openCreateDialogVisible = false;
    openDeleteDialogVisible = false;

    ngOnInit() {
        this.getLocations();
    }

    getLocations(page: number = 0, size: number = 10) {
        this.currentPage.set(page);
        this.rows.set(size);
        const pageable: Pageable = { page, size };
        this.locationService.getLocations(pageable).subscribe({
            next: (response: PageLocationDto) => {
                this.locations.set(response.content ?? []);
                this.totalElements.set(response.totalElements ?? 0);
            },
            error: (err) => {
                console.error('Error getting locations:', err);
                this.toastService.showError('Failed to load locations.');
            }
        });
    }

    onSearch(searchTerm: string) {
        if (!searchTerm || searchTerm.trim() === '') {
            this.getLocations();
            return;
        }
        const pageable: Pageable = { page: 0, size: this.rows() };
        this.locationService.getByName(searchTerm, pageable).subscribe({
            next: (response: PageLocationDto) => {
                this.locations.set(response.content ?? []);
                this.totalElements.set(response.totalElements ?? 0);
            },
            error: (err) => {
                console.error('Error searching locations:', err);
                this.toastService.showError('Search failed.');
            }
        });
    }

    onPageChange(event: any) {
        const page = event.first / event.rows;
        this.getLocations(page, event.rows);
    }

    openCreateDialog() {
        this.selectedLocation.set({});
        this.openCreateDialogVisible = true;
    }

    openDeleteDialog(location: LocationDto) {
        this.selectedLocation.set(location);
        this.openDeleteDialogVisible = true;
    }

    handleCreateDialogSubmit(locationDto: LocationDto) {
        this.locationService.createLocation(locationDto).subscribe({
            next: () => {
                this.getLocations();
                this.toastService.showSuccess('Location created successfully.');
            },
            error: (err) => {
                console.error('Error creating location:', err);
                this.toastService.showError('Could not create location.');
            }
        });
    }

    handleDeleteDialogSubmit(id: string) {
        this.locationService.deleteLocation(id).subscribe({
            next: () => {
                this.getLocations();
                this.toastService.showSuccess('Location deleted successfully.');
            },
            error: () => {
                this.toastService.showError("Could not delete location.");
            }
        });
    }
}
