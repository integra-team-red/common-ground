import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {TableLazyLoadEvent} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {SystemFeedbackControllerService} from "@app/api/api/systemFeedbackController.service";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {FeedbackTable} from "../../ui/feedbacks-table/feedbacks-table";
import {CreateFeedback} from "../../ui/create-feedback/create-feedback";
import {Searchbar} from "../../../searchbar/searchbar";
import {Pageable} from "@app/api/model/pageable";
import {PageSystemFeedbackDTO} from "@app/api/model/pageSystemFeedbackDTO";


@Component({
    selector: 'app-feedback-page',
    standalone: true,
    imports: [FeedbackTable, FormsModule, ToastModule, CreateFeedback, Searchbar],
    providers: [SystemFeedbackControllerService, MessageService],
    templateUrl: './feedback-page.html'
})
export class FeedbackPage implements OnInit {
    feedbackService = inject(SystemFeedbackControllerService);
    feedbacks = signal<SystemFeedbackDTO[]>([]);
    rows = signal<number>(9);
    totalRecords = signal<number>(0);
    pageNumber = signal<number>(0);
    searchQuery = signal<string>('');

    ngOnInit() {
        this.getFeedbacks();
    }

    getFeedbacks() {
        const query = this.searchQuery().trim();

        this.feedbackService.getAllSystemFeedbacks(
            {
                page: this.pageNumber(),
                size: this.rows()
            },
            query ? query : undefined
        ).subscribe({
            next: (response) => {
                this.feedbacks.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            },
            error: (err) => console.error('Error', err)
        });
    }

    loadFeedbacksLazy(event: TableLazyLoadEvent) {
        const page = Math.floor((event.first ?? 0) / (event.rows ?? this.rows()));
        const size = event.rows ?? this.rows();
        this.pageNumber.set(page);
        this.rows.set(size);
        this.getFeedbacks();
    }

    onSearch(searchTerm: string) {
        if (!searchTerm || searchTerm.trim() === '') {
            this.getFeedbacks();
            return;
        }
        const pageable: Pageable = {page: 0, size: this.rows()};
        this.feedbackService.findAllSystemFeedbacksByEmail(searchTerm, pageable).subscribe({
            next: (response: PageSystemFeedbackDTO) => {
                this.feedbacks.set(response.content ?? []);
                this.totalRecords.set(response.totalElements ?? 0);
            },
        });
    }
}
