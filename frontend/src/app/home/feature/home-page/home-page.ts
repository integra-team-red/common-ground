import {AfterViewInit, Component, ElementRef, inject, OnInit, signal, ViewChild, effect, computed} from '@angular/core';
import {HobbyGroupCard} from "../../ui/hobby-group-card/hobby-group-card";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";
import {FormsModule} from "@angular/forms";
import {CreateHobbyGroup} from "../../ui/create-hobby-group/create-hobby-group";
import {Searchbar} from "../../../searchbar/searchbar";
import {Pageable} from "@app/api/model/pageable";
import {PageHobbyGroupDto} from "@app/api/model/pageHobbyGroupDto";
import {Button} from "primeng/button";
import {UserDetailsService} from '../../../services/UserDetailsService/user-details-service';
import {EmptyStateCard} from '../../ui/empty-state-card/empty-state-card';

type State = 'loading' | 'data' | 'empty';
import * as L from 'leaflet';
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
        EmptyStateCard,
    ],
    templateUrl: './home-page.html',
    standalone: true,
})
export class HomePage implements OnInit,AfterViewInit {

    hobbyGroupService = inject(HobbyGroupControllerService);
    userDetailsService=inject(UserDetailsService);
    eventService=inject(EventControllerService);

    hobbyGroups = signal<HobbyGroupDto[]>([]);
    visible = signal<boolean>(false);

    searchQuery = signal<string>('');
    filteredHobbyGroups = signal<HobbyGroupDto[]>([]);



    totalRecords = signal<number>(0);
    loading = signal<boolean>(false);
    currentPage = signal<number>(0);
    showMapMobile = signal<boolean>(false);

    private map: L.Map | undefined;

    @ViewChild('scrollAnchor') scrollAnchor!: ElementRef;


    uiState = computed<State>(() => {
        if (this.loading()) return 'loading';
        return this.filteredHobbyGroups().length == 0 ? 'empty' : 'data';
    });

    ngOnInit(): void {
    }

    constructor() {
        effect(() => {
            const locationId = this.userDetailsService.selectedLocation()?.id;
            if (locationId) {
                this.getHobbyGroupsByLocation(0);
            }
        });
    }

    ngAfterViewInit(): void {
        this.map = this.initMap('map-desktop');
        this.loadMapEvents();

        const observer = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && !this.loading() &&
                this.filteredHobbyGroups().length < this.totalRecords()) {
                this.loadMore();
            }
        }, { threshold: 0.1 });

        if (this.scrollAnchor) observer.observe(this.scrollAnchor.nativeElement);
    }

    loadMore() {
        const nextPage = this.currentPage() + 1;
        this.currentPage.set(nextPage);
        this.getHobbyGroups(nextPage);

    }

    private initMap(containerId: string): L.Map {
        const map = L.map(containerId).setView([44.4268, 26.1025], 13);
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; OpenStreetMap'
        }).addTo(map);

        const storedLocation = localStorage.getItem('selectedLocation');
        if (storedLocation) {
            try {
                const loc = JSON.parse(storedLocation);
                map.setView([loc.latitude, loc.longitude], 13);
                L.marker([loc.latitude, loc.longitude]).addTo(map).bindPopup(`Location: ${loc.name}`);
            } catch (e) { console.error(e); }
        }
        return map;
    }

    toggleMap(visible: boolean) {
        this.showMapMobile.set(visible);
        if (this.map) {
            this.map.remove();
        }

        setTimeout(() => {
            if (visible) {
                this.map = this.initMap('map-mobile');
            } else {
                this.map = this.initMap('map-desktop');
            }
            this.loadMapEvents();
        }, 100);
    }

    private addEventMarker(lat: number, lng: number, title: string): void {
        if (this.map) {
            L.marker([lat, lng]).addTo(this.map).bindPopup(`<b>${title}</b>`);
        }
    }

    loadMapEvents(): void {
        this.eventService.getAllEventsForMap().subscribe({
            next: (events: EventMapDto[]) => {
                events.forEach(event => {
                    if (event.latitude !== undefined && event.longitude !== undefined) {
                        this.addEventMarker(event.latitude, event.longitude, event.title ?? 'Unknown');
                    }
                });
            }
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

    getHobbyGroupsByLocation(page: number) {
        this.loading.set(true);
        const locationId = this.userDetailsService.selectedLocation()?.id;
        this.hobbyGroupService.getAllHobbyGroups({ size: 4, page: page },locationId)
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
            this.getHobbyGroupsByLocation(0);
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
