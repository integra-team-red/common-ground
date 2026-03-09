import {ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {providePrimeNG} from 'primeng/config';
import {CommonGroundTheme} from "./theme/theme";
import {ApiModule, Configuration} from '../../typescript-client';
import {MessageService} from "primeng/api";

export const appConfig: ApplicationConfig = {
    providers: [
        importProvidersFrom(
            ApiModule.forRoot(() => new Configuration({
                basePath: ''
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
        MessageService
    ]
};
