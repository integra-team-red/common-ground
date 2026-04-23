import {AfterViewInit, Component, ElementRef, inject, OnInit, signal, ViewChild} from '@angular/core';
import {HobbyGroupCard} from "../../ui/hobby-group-card/hobby-group-card";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";
import {FormsModule} from "@angular/forms";
import {CreateHobbyGroup} from "../../ui/create-hobby-group/create-hobby-group";
import {Searchbar} from "../../../searchbar/searchbar";
import {Pageable} from "@app/api/model/pageable";
import {PageHobbyGroupDto} from "@app/api/model/pageHobbyGroupDto";
import {Button} from "primeng/button";
import * as L from 'leaflet';
import{UserDetailsService} from "../../../services/UserDetailsService/user-details-service";
import{EventControllerService} from "@app/api/api/eventController.service";
import {EventMapDto} from "@app/api/model/eventMapDto";

@Component({
    selector: 'app-home-page',
    imports: [
        HobbyGroupCard,
        FormsModule,
        CreateHobbyGroup,
        Searchbar,
        Button,
    ],
    templateUrl: './home-page.html',
    standalone: true,
})
export class HomePage implements OnInit,AfterViewInit {

    hobbyGroupService = inject(HobbyGroupControllerService);
    userDetailsService = inject(UserDetailsService);
    eventService=inject(EventControllerService);

    hobbyGroups = signal<HobbyGroupDto[]>([]);
    visible = signal<boolean>(false);

    searchQuery = signal<string>('');
    filteredHobbyGroups = signal<HobbyGroupDto[]>([]);

    totalRecords = signal<number>(0);
    loading = signal<boolean>(false);
    currentPage = signal<number>(0);

    private map: L.Map | undefined;
    userName = () => this.userDetailsService.getCurrentUser()?.username|| 'Guest';

    @ViewChild('scrollAnchor') scrollAnchor!: ElementRef;

    ngOnInit(): void {
        this.getHobbyGroups(0)
        this.loading.set(true)
    }
    ngAfterViewInit(): void {
        this.initMap();
        const observer = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && !this.loading() &&
                this.filteredHobbyGroups().length < this.totalRecords()) {
                this.loadMore();
            }
        }, { threshold: 0.1 });

        if (this.scrollAnchor) {
            observer.observe(this.scrollAnchor.nativeElement);
        }
    }
    private initMap(): void {
        this.map = L.map('map').setView([44.4268, 26.1025], 13);

        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(this.map);
        const storedLocation = localStorage.getItem('selectedLocation');

        if (storedLocation) {
            try {
                const loc = JSON.parse(storedLocation);
                this.map.setView([loc.latitude, loc.longitude], 13);
                L.marker([loc.latitude, loc.longitude]).addTo(this.map)
                    .bindPopup(`Your Location: ${loc.name}`);
            } catch (e) {
                console.error("Error parsing stored location", e);
            }
        }
        this.loadMapEvents();
    }

    loadMore() {
        const nextPage = this.currentPage() + 1;
        this.currentPage.set(nextPage);
        this.getHobbyGroups(nextPage);
    }

    private addEventMarker(lat: number, lng: number, title: string): void {
        const marker = L.marker([lat, lng]).addTo(this.map!);

        marker.bindPopup(`<b>${title}</b>`);
    }

    loadMapEvents(): void {
        this.eventService.getAllEventsForMap().subscribe({
            next: (events: EventMapDto[]) => {
                events.forEach(event => {
                    if (event.latitude !== undefined && event.longitude !== undefined) {
                        this.addEventMarker(event.latitude, event.longitude, event.title ?? 'Unknown event');
                    }                });
            },
            error: (err) => console.error("Error ", err)
        });
    }

    getHobbyGroups(page: number) {
        this.loading.set(true);
        this.hobbyGroupService.getAllHobbyGroups({ size: 4, page: page })
            .subscribe({
                next: (response) => {
                    const newItems = response.content ?? [];
                    this.totalRecords.set(response.totalElements ?? 0);

                    if (page === 0) {
                        this.hobbyGroups.set(newItems);
                    } else {
                        this.hobbyGroups.update(prev => [...prev, ...newItems]);
                    }
                    this.filteredHobbyGroups.set(this.hobbyGroups());
                    this.loading.set(false);
                },
                error: () => this.loading.set(false)
            });
    }

    getFilteredHobbyGroups(page: number) {
        this.loading.set(true);
        this.hobbyGroupService.filterAllHobbyGroupsByName(this.searchQuery().trim(), { size: 4, page: page })
            .subscribe({
                next: (response) => {
                    const newItems = response.content ?? [];
                    this.totalRecords.set(response.totalElements ?? 0);

                    if (page === 0) {
                        this.hobbyGroups.set(newItems);
                    } else {
                        this.hobbyGroups.update(prev => [...prev, ...newItems]);
                    }
                    this.filteredHobbyGroups.set(this.hobbyGroups());
                    this.loading.set(false);
                },
                error: () => this.loading.set(false)
            });
    }

    onSearch(searchTerm: string) {
        this.searchQuery.set(searchTerm);
        this.currentPage.set(0);
        this.hobbyGroups.set([]);
        if (!searchTerm || searchTerm.trim() === '') {
            this.getHobbyGroups(0);
            return;
        }
        const pageable: Pageable = { page: 0 };
        this.hobbyGroupService.filterAllHobbyGroupsByName(searchTerm, pageable).subscribe({
            next: (response: PageHobbyGroupDto) => {
                this.hobbyGroups.set(response.content ?? []);
                this.filteredHobbyGroups.set(this.hobbyGroups());
                this.totalRecords.set(response.totalElements ?? 0);
            },
        });
    }

    onScroll(event: any) {
        const element = event.target;
        const threshold = 100;
        const atBottom = element.scrollHeight - element.scrollTop <= element.clientHeight + threshold;
        if (atBottom && !this.loading() && this.filteredHobbyGroups().length < this.totalRecords()) {
            this.loadMore();
        }
    }

    onGroupUpdated() {
        this.refreshGroups();
    }

    onGroupDeleted() {
        this.refreshGroups();
    }

    refreshGroups() {
        this.currentPage.set(0);
        if (this.searchQuery().trim()) {
            this.getFilteredHobbyGroups(0);
        } else {
            this.getHobbyGroups(0);
        }
    }
}
