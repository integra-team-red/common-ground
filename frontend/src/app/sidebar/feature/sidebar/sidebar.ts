import {Component, computed, inject, OnInit} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {CommonModule} from "@angular/common";
import {Button} from "primeng/button";
import {Drawer} from "primeng/drawer";
import {UserDetailsService} from "../../../services/UserDetailsService/user-details-service";
import { FormsModule } from "@angular/forms";
import {CommonGroundTheme} from '../../../theme/theme';
import {ToggleSwitch} from 'primeng/toggleswitch';
import {usePreset} from '@primeuix/themes';
import Aura from '@primeuix/themes/aura';

@Component({
    selector: 'app-sidebar',
    imports: [CommonModule, RouterLink, RouterLinkActive, Button, Drawer, ToggleSwitch, FormsModule],
    templateUrl: './sidebar.html',
    styleUrl: './sidebar.css',
})
export class Sidebar implements OnInit {
    private router = inject(Router);
    private userDetailsService = inject(UserDetailsService);
    visible: boolean = false;
    isAuraTheme: boolean = false;

    user = this.userDetailsService.getCurrentUser;

    ngOnInit() {
        this.isAuraTheme = localStorage.getItem('app-use-aura') === 'true';
        usePreset(this.isAuraTheme ? Aura : CommonGroundTheme);
    }

    onThemeToggle() {
        usePreset(this.isAuraTheme ? Aura : CommonGroundTheme);
        localStorage.setItem('app-use-aura', String(this.isAuraTheme));
    }

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
        document.cookie = "jwt=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        this.router.navigate(['/login']);
    }
}
