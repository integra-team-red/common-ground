import {Component, input, output} from '@angular/core';
import {TableLazyLoadEvent, TableModule} from "primeng/table";
import {TagDto} from "@app/api/model/tagDto";
import {Chip} from "primeng/chip";
import {Button} from "primeng/button";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-tag-table',
    imports: [
        TableModule,
        Chip,
        Button,
        FormsModule
    ],
    templateUrl: './tag-table.html',
    styleUrl: './tag-table.css',
    standalone: true
})

export class TagTable {
    tags = input.required<TagDto[]>();
    rows = 10;
    totalRecords = input<number>(0);
    loadLazy = output<TableLazyLoadEvent>();

    updateClick = output<number>();
    deleteClick = output<number>();

    protected loadTagsLazy($event: TableLazyLoadEvent) {
        this.loadLazy.emit($event);
    }
}
