import {Component, input, output} from '@angular/core';
import {TableLazyLoadEvent, TableModule} from "primeng/table";
import {DatePipe} from "@angular/common";
import {EventDto} from "@app/api/model/eventDto";
import {UpdateEvent} from "../update-event/update-event";
import {DeleteEvent} from "../delete-event/delete-event";

@Component({
    selector: 'app-events-table',
    imports: [
        TableModule,
        DatePipe,
        UpdateEvent,
        DeleteEvent
    ],
    templateUrl: './events-table.html',
    styleUrl: './events-table.css',
    standalone: true
})
export class EventsTable {
    events = input<EventDto[]>([]);
    rows = input<number>(0);
    totalRecords = input<number>(0);
    refresh = output<void>();
    lazyEvent = output<TableLazyLoadEvent>();

    protected loadEventsLazy($event: TableLazyLoadEvent) {
        this.lazyEvent.emit($event);
    }
}
