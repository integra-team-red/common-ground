import {Component, inject, input} from '@angular/core';
import {Card} from "primeng/card";
import {Avatar} from "primeng/avatar";
import {Chip} from "primeng/chip";
import {Button} from "primeng/button";
import {TagControllerService} from "@app/api/api/tagController.service";
import {forkJoin, of, switchMap} from "rxjs";
import {toObservable, toSignal} from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-hobby-group-card',
    imports: [
        Card,
        Avatar,
        Chip,
        Button,
    ],
    templateUrl: './hobby-group-card.html'
})
export class HobbyGroupCard {

    cardTitle = input.required<string>()
    cardDescription = input.required<string>()
    cardTagsIds = input<number[]>()
    tagService = inject(TagControllerService)

    private tags = toObservable(this.cardTagsIds).pipe(
        switchMap(ids => {
            if (!ids || ids.length === 0) return of([]);
            const requests = ids.map(id => this.tagService.getTagById(id));
            return forkJoin(requests);
        })
    );

    cardTags = toSignal(this.tags, { initialValue: [] });
}

