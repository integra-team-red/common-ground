import {Component, input} from '@angular/core';
import {Card} from "primeng/card";
import {Button} from "primeng/button";
import {Tag} from "primeng/tag";
import {Avatar} from "primeng/avatar";

@Component({
    selector: 'app-hobby-group-card',
    imports: [
        Card,
        Button,
        Tag,
        Avatar
    ],
    templateUrl: './hobby-group-card.html',
    styleUrl: './hobby-group-card.css',
    standalone: true
})
export class HobbyGroupCard {
    cardTitle = input.required<string>()
    cardContent = input.required<string>()
}
