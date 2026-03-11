import {Component, computed, inject, OnInit, signal} from '@angular/core';
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

    rows = signal<number>(10);
    totalRecords = signal<number>(0);
    pageNumber = signal<number>(0);

    searchQuery = signal<string>('');

    ngOnInit() {
    }

    getEvents() {
        this.eventService.getAllEvents({
            size: 3,
            page: this.pageNumber()
        }).subscribe((response: PageEventDto) => {
            this.events.set(response.content ?? [])
        })
    }

    loadEventsLazy(event: TableLazyLoadEvent) {
        const page = Math.floor((event.first ?? 0) / (event.rows ?? this.rows()));
        const size = event.rows ?? this.rows();

        this.eventService.getAllEvents({
            page,
            size
        }).subscribe((response: PageEventDto) => {
            this.events.set(response.content ?? []);
            this.totalRecords.set(response.totalElements ?? 0);
        });
    }

    filteredEvents = computed(() => {
        const query = this.searchQuery().toLowerCase().trim();
        const allEvents = this.events();
        if (!query) return allEvents;
        return allEvents.filter(event =>
            event.title?.toLowerCase().includes(query)
        );
    })
}
