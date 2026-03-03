import {Routes} from '@angular/router';
import {ThemeShowcaseComponent} from "./theme/feature/theme-showcase/theme-showcase";
import {Home} from "./home/feature/home";

export const routes: Routes = [
    {
        path: '',
        title: "Home",
        component: Home
    },
    {
        path: 'theme',
        title: "Theme",
        component: ThemeShowcaseComponent,
    }
];
