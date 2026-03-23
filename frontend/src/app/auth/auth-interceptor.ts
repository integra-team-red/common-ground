import { HttpInterceptorFn } from "@angular/common/http";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const token = document.cookie
        .split('; ')
        .find(row => row.startsWith('jwt='))
        ?.split('=')[1]
        .trim();

    if (token) {
        req = req.clone({
            setHeaders: {
                'Authorization': `Bearer ${token}`
            }
        });
    }

    return next(req);
};
