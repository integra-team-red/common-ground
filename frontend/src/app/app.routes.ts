import {Routes} from '@angular/router';
import {ThemeShowcaseComponent} from "./theme/feature/theme-showcase/theme-showcase";
import {HomePage} from "./home/feature/home-page/home-page";
import {TagPageComponent} from "./tag/feature/tag-page.component";
import {LocationsPage} from "./admin-locations/feature/locations-page/locations-page";
import {Registration} from "./registration/registration";
import {Events} from "./events/feature/events-admin-management/events";
import {LoginPage} from "./login/feature/login-page/login-page";
import {FeedbackPage} from "./feedback/feature/feedbacks-admin-management/feedback-page";
import {authGuard} from "./auth/auth-guard";
import {ProfilePage} from "./profile/feature/profile-page/profile-page";

export const routes: Routes = [

    { path: '', redirectTo: '/login', pathMatch: 'full' },

    {
        path: 'login',
        component: LoginPage,
    },

    {
        path: 'registration',
        component: Registration,
    },

    {
        path: 'home',
        title: "Home",
        component: HomePage,
        data: {icon: 'pi-home'}
    },

    {
        path: 'events',
        title: "Events",
        component: Events,
        data: {icon: 'pi-calendar'}
    },

    {
        path:'feedback',
        title:"Feedback",
        component:FeedbackPage,
        data: {icon: 'pi-comment'}
    },

    {
        path: 'theme',
        title: "Theme",
        component: ThemeShowcaseComponent,
        canActivate: [authGuard],
        data: { isAdmin: true, icon: 'pi-palette' }
    },

    {
        path: 'locations',
        title: "Locations",
        component: LocationsPage,
        canActivate: [authGuard],
        data: { isAdmin: true, icon: 'pi-map-marker' }
    },

    {
        path: 'tag',
        title: "Tag",
        component: TagPageComponent,
        canActivate: [authGuard],
        data: { isAdmin: true, icon: 'pi-tag' }
    },
    {
        path: 'profile',
        title: "Profile",
        component: ProfilePage,
        data: { isProfile: true }
    }
];
