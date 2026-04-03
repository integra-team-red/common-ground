import {inject} from '@angular/core';
import {CanActivateFn, Router} from '@angular/router';
import {UserDetailsService} from "../services/UserDetailsService/user-details-service";

export const authGuard: CanActivateFn = (route, state) => {
    const userDetailService = inject(UserDetailsService);
    const router = inject(Router);

    if (userDetailService.isAdmin()) {
        return true;
    }
    router.navigate(['/']);
    return false;
};
