import {Component, inject, signal} from '@angular/core';
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
    ],
    providers: [EventControllerService],
    templateUrl: './events.html',
    styleUrl: './events.css',
})
export class Events {
    eventService = inject(EventControllerService);
    events = signal<EventDto[]>([])
    rows = signal<number>(9);
    totalRecords = signal<number>(0);
    pageNumber = signal<number>(0);
    searchQuery = signal<string>('');

    getEvents() {
        const query = this.searchQuery().trim();
        const currentPage = this.pageNumber();
        const currentSize = this.rows();

        if (query) {
            this.eventService.getAllEventsByTitleInput(query, {
                page: currentPage,
                size: currentSize
            }).subscribe((response) => {
                this.events.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            });
        } else {
            this.eventService.getAllEvents({page: currentPage, size: currentSize}).subscribe((response) => {
                this.events.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            });
        }
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
        })
    }

    onSearch() {
        this.pageNumber.set(0)
        this.getEvents();
    }
}
