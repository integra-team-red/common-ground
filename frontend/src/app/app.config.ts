import {ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {providePrimeNG} from 'primeng/config';
import {CommonGroundTheme} from "./theme/theme";
import {ApiModule, Configuration} from '../../typescript-client';
import {MessageService} from "primeng/api";
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {authInterceptor} from "./auth/auth-interceptor";

export const appConfig: ApplicationConfig = {
    providers: [
        importProvidersFrom(
            ApiModule.forRoot(() => new Configuration({
                basePath: '',
                withCredentials: true
            }))
        ),
        provideBrowserGlobalErrorListeners(),
        provideRouter(routes),
        providePrimeNG({
            theme: {
                preset: CommonGroundTheme,
                options: {
                    darkModeSelector: '.my-app-dark'
                }
            }
        }),
        MessageService,
        provideHttpClient(withInterceptors([authInterceptor])),
    ]
};
