import {Component, ElementRef, inject, OnInit, signal, ViewChild} from '@angular/core';
import {HobbyGroupCard} from "../../ui/hobby-group-card/hobby-group-card";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";
import {FormsModule} from "@angular/forms";
import {InputText} from "primeng/inputtext";
import {IconField} from "primeng/iconfield";
import {InputIcon} from "primeng/inputicon";
import {CreateHobbyGroup} from "../../ui/create-hobby-group/create-hobby-group";
import {Button} from "primeng/button";


@Component({
    selector: 'app-home-page',
    imports: [
        HobbyGroupCard,
        FormsModule,
        InputText,
        IconField,
        InputIcon,
        CreateHobbyGroup,
        Button,
    ],
    templateUrl: './home-page.html',
    standalone: true,
})
export class HomePage implements OnInit {
    hobbyGroupService = inject(HobbyGroupControllerService);
    hobbyGroups = signal<HobbyGroupDto[]>([]);
    visible = signal<boolean>(false);

    searchQuery = signal<string>('');
    filteredHobbyGroups = signal<HobbyGroupDto[]>([]);

    totalRecords = signal<number>(0);
    loading = signal<boolean>(false);
    currentPage = signal<number>(0);

    @ViewChild('scrollAnchor') scrollAnchor!: ElementRef;

    ngOnInit(): void {
        this.getHobbyGroups(0)

    }

    ngAfterViewInit(): void {
        const observer = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && !this.loading() &&
                this.filteredHobbyGroups().length < this.totalRecords()) {
                this.loadMore();
            }
        }, {threshold: 0.1});

        if (this.scrollAnchor) {
            observer.observe(this.scrollAnchor.nativeElement);
        }
    }

    loadMore() {
        const nextPage = this.currentPage() + 1;
        this.currentPage.set(nextPage);
        this.getHobbyGroups(nextPage);
    }

    getHobbyGroups(page: number) {
        this.loading.set(true);
        this.hobbyGroupService.getAllHobbyGroups({size: 5, page: page})
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
        this.hobbyGroupService.filterAllHobbyGroupsByName(this.searchQuery().trim(), {size: 5, page: page})
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

    onSearch(query: string) {
        this.searchQuery.set(query);
        this.currentPage.set(0);
        this.hobbyGroups.set([]);
        this.getFilteredHobbyGroups(0);
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
