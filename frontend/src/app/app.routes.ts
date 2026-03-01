import {Routes} from '@angular/router';
import {HomePage} from './home/feature/home-page/home-page';
import {ThemeShowcaseComponent} from "./theme/feature/theme-showcase/theme-showcase";

export const routes: Routes = [
    {
        path: '',
        title: "Home",
        component: HomePage,
        data: {
            icon: "pi-house"
        }
    },
    {
        path: 'theme',
        title: "Theme",
        component: ThemeShowcaseComponent,
    }
];
