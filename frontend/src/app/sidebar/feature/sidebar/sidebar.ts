import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
    selector: 'app-sidebar',
    imports: [CommonModule, RouterLink, RouterLinkActive],
    templateUrl: './sidebar.html',
    styleUrl: './sidebar.css',
})
export class Sidebar {
    private router = inject(Router);

    public navRoutes = this.router.config
        .map(route => ({
            path: `/${route.path}`,
            title: route.title,
            icon: route.data?.["icon"]
        }));
}
