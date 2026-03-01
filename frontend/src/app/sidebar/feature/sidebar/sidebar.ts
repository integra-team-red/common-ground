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
    private router = inject(Router);
    visible: boolean = false;

    public navRoutes = this.router.config
        .map(route => ({
            path: `/${route.path}`,
            title: route.title,
            icon: route.data?.["icon"]
        }));
}
