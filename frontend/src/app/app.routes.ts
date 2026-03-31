import {Routes} from '@angular/router';
import {ThemeShowcaseComponent} from "./theme/feature/theme-showcase/theme-showcase";
import {HomePage} from "./home/feature/home-page/home-page";
import {TagPageComponent} from "./tag/feature/tag-page.component";
import {LocationsPage} from "./admin-locations/feature/locations-page/locations-page";
import {Registration} from "./registration/registration";
import {Events} from "./events/feature/events-admin-management/events";
import {LoginPage} from "./login/feature/login-page/login-page";
import {FeedbackPage} from "./feedback/feature/feedbacks-admin-management/feedback-page";

export const routes: Routes = [
    {
        path: '',
        title: "Home",
        component: HomePage,
    },

    {
        path: 'events',
        title: "Events",
        component: Events,

    },

    {
        path: 'theme',
        title: "Theme",
        component: ThemeShowcaseComponent,
        data: { isAdmin: true }
    },
    {
        path: 'feedback',
        title: "Feedback",
        component: FeedbackPage
    },
    {
        path: 'registration',
        title: 'User Registration',
        component: Registration,
        data: { isAdmin: true }
    },
    {
        path: 'login',
        title: "Login",
        component: LoginPage,
    },
    {
        path: 'locations',
        title: "Locations",
        component: LocationsPage,
        data: { isAdmin: true }
    },

    {
        path: 'tag',
        title: "Tag",
        component: TagPageComponent,
        data: { isAdmin: true }
    },
];
