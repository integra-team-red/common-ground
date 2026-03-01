import {Routes} from '@angular/router';
import {HomePage} from './home/feature/home-page/home-page';
import {ThemeShowcaseComponent} from "./theme/feature/theme-showcase/theme-showcase";

export const routes: Routes = [
    {
        path: '',
        component: HomePage,
    },
    {
        path: 'theme',
        component: ThemeShowcaseComponent,
    }
];
