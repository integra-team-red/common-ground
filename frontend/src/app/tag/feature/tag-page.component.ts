import {Component, inject, OnInit, signal} from '@angular/core';
import {TagControllerService} from "@app/api/api/tagController.service";
import {TagDto} from "@app/api/model/tagDto";
import {PageTagDto} from "@app/api/model/pageTagDto";
import {TagTable} from "../ui/tag-table/tag-table";
import {InputTextModule} from "primeng/inputtext";
import {Button} from "primeng/button";
import {FormsModule} from "@angular/forms";
import {CreateTag} from "../ui/create-tag/create-tag";
import {UpdateTag} from "../ui/update-tag/update-tag";
import {TableLazyLoadEvent} from "primeng/table";
import {ToastService} from "../../toast-service/toast-service";
import {Searchbar} from "../../searchbar/searchbar";
import {Pageable} from "@app/api/model/pageable";

@Component({
    selector: 'app-tag-page.component',
    imports: [
        TagTable,
        InputTextModule,
        Button,
        FormsModule,
        CreateTag,
        UpdateTag,
        Searchbar,
    ],
    templateUrl: './tag-page.component.html',
    styleUrl: './tag-page.component.css',
    standalone: true
})

export class TagPageComponent implements OnInit {
    tagService: TagControllerService = inject(TagControllerService);
    toastService: ToastService = inject(ToastService);

    tags = signal<TagDto[]>([]);
    searchValue = "";
    currentTag = signal<TagDto>({ label: "" });

    visibleAddForm = signal<boolean>(false);
    visibleUpdateForm = signal<boolean>(false);

    rows = signal(10);
    totalRecords = signal<number>(0);
    currentPage = 0;
    currentSize = this.rows;

    ngOnInit(): void {
        this.getTags();
    }

    getTags(): void {
        if (this.searchValue == "") {
            this.tagService.getAllTags({
                size: this.currentSize(),
                page: this.currentPage
            }).subscribe((response: PageTagDto) => {
                this.tags.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            });
        } else {
            this.tagService.filterTags(
                this.searchValue, {
                    size: this.currentSize(),
                    page: this.currentPage
                }).subscribe((response: PageTagDto) => {
                this.tags.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            });
        }
    }

    loadTagsLazy(event: TableLazyLoadEvent) {
        this.currentPage = Math.floor((event.first ?? 0) / (event.rows ?? this.rows()));
        this.currentSize.set(event.rows ?? this.rows());
        this.getTags();
    }

    createTag(tag: TagDto) {
        this.tagService.createNewTag(tag).subscribe({
            next: () => {
                this.getTags();
                this.visibleAddForm.set(false);
                this.toastService.showSuccess("Tag was created successfully.");
            },
            error: (err) => {
                console.error("Error creating tag", err);
                this.toastService.showError(err);
            }
        });
    }

    updateTag(tag: TagDto) {
        this.tagService.updateTag(this.currentTag().id ?? -1, tag).subscribe({
            next: () => {
                this.getTags();
                this.visibleUpdateForm.set(false);

                this.toastService.showSuccess("Tag was updated successfully.");
            },
            error: () => {
                this.toastService.showError("Could not update tag.");
            }
        })
    }

    deleteTag() {
        this.tagService.deleteTag(this.currentTag().id ?? -1).subscribe({
            next: () => {
                this.getTags();

                this.toastService.showSuccess("Tag was deleted successfully.");
            },
            error: () => {
                this.toastService.showError("Could not delete tag");
            }
        })
    }

    onTagCreateClick() {
        this.visibleAddForm.set(true);
    }

    onTagUpdateClick(tagId: number) {
        this.tagService.getTagById(tagId).subscribe((response: TagDto) => {
            this.currentTag.set(response);
            this.visibleUpdateForm.set(true);
        });
    }

    onTagDeleteClick(tagId: number) {
        this.tagService.getTagById(tagId).subscribe((response: TagDto) => {
            this.currentTag.set(response);
            this.deleteTag();
        });
    }

    onSearch(searchTerm: string) {
        if (!searchTerm || searchTerm.trim() === '') {
            this.getTags();
            return;
        }
        const pageable: Pageable = { page: 0, size: this.rows() };
        this.tagService.filterTags(searchTerm, pageable).subscribe({
            next: (response: PageTagDto) => {
                this.tags.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            },
        });
    }

}
