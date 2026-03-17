import {Routes} from '@angular/router';
import {ThemeShowcaseComponent} from "./theme/feature/theme-showcase/theme-showcase";
import {HomePage} from "./home/feature/home-page/home-page";

export const routes: Routes = [
    {
        path: '',
        title: "Home",
        component: HomePage
    },
    {
        path: 'theme',
        title: "Theme",
        component: ThemeShowcaseComponent,
    }
];
