import {Component, inject, OnInit, signal} from '@angular/core';
import {IconField} from "primeng/iconfield";
import {InputIcon} from "primeng/inputicon";
import {InputText} from "primeng/inputtext";
import {TableLazyLoadEvent, TableModule} from "primeng/table";
import {EventControllerService} from "@app/api/api/eventController.service";
import {EventDto} from "@app/api/model/eventDto";
import {PageEventDto} from "@app/api/model/pageEventDto";
import {EventsTable} from "../../ui/events-table/events-table";

import {CreateEvent} from "../../ui/create-event/create-event";
import {FormsModule} from "@angular/forms";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";

@Component({
    selector: 'app-events',
    imports: [
        IconField,
        InputIcon,
        InputText,
        TableModule,
        EventsTable,
        CreateEvent,
        FormsModule,
        ToastModule,
    ],
    providers: [EventControllerService, MessageService],
    templateUrl: './events.html',
    styleUrl: './events.css',
})
export class Events implements OnInit {
    eventService = inject(EventControllerService);
    events = signal<EventDto[]>([])
    pageNumber = signal<number>(0);

    ngOnInit() {
        this.getEvents()
    }

    getEvents() {
        this.eventService.getAllEvents({
            size: 3,
            page: this.pageNumber()
        }).subscribe((response: PageEventDto) => {
            this.events.set(response.content ?? [])
        })
    }

    protected fetchEventsLazily($event: TableLazyLoadEvent) {
        this.pageNumber.set(this.pageNumber() + 1);
        this.eventService.getAllEvents({
            size: 3,
            page: this.pageNumber()
        }).subscribe((response: PageEventDto) => {
            const oldEvents = this.events();
            const newEvents = response.content ?? [];
            const allEvents = oldEvents.concat(newEvents);
            this.events.set(allEvents);
        })
    }
}
