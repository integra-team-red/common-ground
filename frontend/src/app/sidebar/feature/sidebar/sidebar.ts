import {Component, computed, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {CommonModule} from "@angular/common";
import {Button} from "primeng/button";
import {Drawer} from "primeng/drawer";
import {UserDetailsService} from "../../../services/UserDetailsService/user-details-service";

@Component({
    selector: 'app-sidebar',
    imports: [CommonModule, RouterLink, RouterLinkActive, Button, Drawer],
    templateUrl: './sidebar.html',
    styleUrl: './sidebar.css',
})
export class Sidebar {
    private router = inject(Router);
    private userDetailsService = inject(UserDetailsService);
    visible: boolean = false;

    user = this.userDetailsService.getCurrentUser;

    public navRoutes = this.router.config
        .filter(route => route.path !== '**' && route.title)
        .map(route => ({
            path: `/${route.path}`,
            title: route.title,
            icon: route.data?.["icon"],
            isAdmin: route.data?.["isAdmin"] === true,
            isProfile: route.data?.["isProfile"] === true,
        }));
    public generalRoutes = this.navRoutes
        .filter(r => !r.isAdmin && !r.isProfile);
    public adminRoutes = computed(() => {
        return this.userDetailsService.isAdmin()
            ? this.navRoutes.filter(r => r.isAdmin && !r.isProfile)
            : [];
    });

    logout() {
        localStorage.removeItem('userToken');
        this.router.navigate(['/login']);
    }
}
