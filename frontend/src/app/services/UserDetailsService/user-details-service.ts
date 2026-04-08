import {computed, inject, Injectable, signal} from '@angular/core';

import {UserDto} from "@app/api/model/userDto";
import {firstValueFrom} from "rxjs";
import {LocationDto} from "@app/api/model/locationDto";
import {PageLocationDto} from "@app/api/model/pageLocationDto";
import {UserControllerService} from "@app/api/api/userController.service";
import {LocationControllerService} from "@app/api/api/locationController.service";

@Injectable({
    providedIn: 'root',
})
export class UserDetailsService {
    userService = inject(UserControllerService);
    locationService = inject(LocationControllerService);

    private currentUser = signal<UserDto | null>(null);
    private userLocations = signal<LocationDto[]>([]);
    private userId = signal<string | undefined>('');
    selectedLocation = signal<LocationDto | undefined>(undefined);

    getUserLocations = computed(() => this.userLocations());
    getUserId = computed(() =>this.currentUser()?.id)
    getCurrentUser = computed(() => this.currentUser());
    isAdmin = computed(() => this.currentUser()?.role === UserDto.RoleEnum.Admin);


    refreshLocations() {

        this.locationService.getLocations({ page: 0, size: 10 }, this.userId()).subscribe({
            next: (response: PageLocationDto) => {
                this.userLocations.set(response.content ?? []);
                const prevLoc = this.selectedLocation();
                if(prevLoc) {
                    const match = this.userLocations().find(i => i.id === prevLoc.id);
                    if(match) {
                        this.selectedLocation.set(match);
                    }
                    else {
                        this.selectedLocation.set(this.userLocations()[0]);
                    }
                }
            },
            error: (err) => {
                console.error('Error getting locations:', err);
            }
        });
    }

    async loadCurrentUser() {
        try {
            const user = await firstValueFrom(
                this.userService.getUserInformation()
            );
            this.currentUser.set(user);
            this.userId.set(user.id);
            this.refreshLocations();
        } catch {
            this.currentUser.set(null); // not logged in yet
        }
    }

}
