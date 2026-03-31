import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {CommonModule} from "@angular/common";
import {Button} from "primeng/button";
import {Drawer} from "primeng/drawer";

@Component({
    selector: 'app-sidebar',
    imports: [CommonModule, RouterLink, RouterLinkActive, Button, Drawer],
    templateUrl: './sidebar.html',
    styleUrl: './sidebar.css',
})
export class Sidebar {
    visible: boolean = false;
    private router = inject(Router);
    public navRoutes = this.router.config
        .filter(route => route.path !== '**' && route.title)
        .map(route => ({
            path: `/${route.path}`,
            title: route.title,
            icon: route.data?.["icon"],
            isAdmin: route.data?.["isAdmin"] === true,
        }));
    public generalRoutes = this.navRoutes
        .filter(r => !r.isAdmin);
    public adminRoutes = this.navRoutes
        .filter(r => r.isAdmin);
}
