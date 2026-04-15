import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Sidebar } from "./sidebar/feature/sidebar/sidebar";
import { Toast } from "primeng/toast";

@Component({
    selector: 'app-root',
    imports: [RouterOutlet, Sidebar, Toast],
    templateUrl: './app.html',
    styleUrl: './app.css',
})
export class App {
    private router = inject(Router);

    get hideSidebar(): boolean {
        return this.router.url.includes('/login') || this.router.url.includes('/registration');
    }
}
