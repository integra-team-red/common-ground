import {computed, inject, Injectable, signal} from '@angular/core';
import {UserControllerService} from "@app/api/api/userController.service";
import {UserDto} from "@app/api/model/userDto";
import {firstValueFrom} from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class UserDetailsService {
    userService = inject(UserControllerService);
    private currentUser = signal<UserDto | null>(null);

    getCurrentUser = computed(() => this.currentUser());
    isAdmin = computed(() => this.currentUser()?.role === UserDto.RoleEnum.Admin);

    async loadCurrentUser() {
        try {
            const user = await firstValueFrom(
                this.userService.getUserInformation()
            );
            this.currentUser.set(user);
        } catch {
            this.currentUser.set(null); // not logged in yet
        }
    }

}
