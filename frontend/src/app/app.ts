import {Component, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Sidebar} from './sidebar/feature/sidebar/sidebar';
import {ReactiveFormsModule} from "@angular/forms";
import {Scroller} from "primeng/scroller";

@Component({
    selector: 'app-root',
    imports: [RouterOutlet, Sidebar, Scroller],
    templateUrl: './app.html',
    styleUrl: './app.css'
})
export class App {
    protected readonly title = signal('frontend');
}
