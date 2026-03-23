import {Component, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Sidebar} from "./sidebar/feature/sidebar/sidebar";
import {Toast} from "primeng/toast";

@Component({
    selector: 'app-root',
    imports: [RouterOutlet, Sidebar, Toast],
    templateUrl: './app.html',
    styleUrl: './app.css',
})
export class App {
    protected readonly title = signal('frontend');
}
