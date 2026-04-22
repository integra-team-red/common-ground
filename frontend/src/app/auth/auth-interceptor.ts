import {HttpErrorResponse, HttpInterceptorFn} from "@angular/common/http";
import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';
import {ToastService} from '../toast-service/toast-service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const router = inject(Router);
    const toastService = inject(ToastService);

    const token = document.cookie
        .split('; ')
        .find(row => row.startsWith('jwt='))
        ?.split('=')[1]
        ?.trim();

    if (token) {
        req = req.clone({
            setHeaders: {
                'Authorization': `Bearer ${token}`
            }
        });
    }

    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            if (error.status === 401 || error.status === 403) {
                toastService.showError("Your session has expired or you do not" +
                    " have permission to access this page. Please log in again.");
                router.navigate(['/login']);
            }
            return throwError(() => error);
        })
    );
};
