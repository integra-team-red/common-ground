import {Component, input, output} from '@angular/core';
import {TableLazyLoadEvent, TableModule} from "primeng/table";
import {DatePipe} from "@angular/common";
import {SystemFeedbackDTO} from "@app/api/model/systemFeedbackDTO";
import {UpdateFeedback} from "../update-feedback/update-feedback";
import {DeleteEvent} from "../delete-feedback/delete-feedback";

@Component({
    selector: 'app-feedback-table',
    standalone: true,
    imports: [TableModule, DatePipe, UpdateFeedback, DeleteEvent],
    templateUrl: './feedbacks-table.html'
})
export class FeedbackTable {
    feedbacks = input<SystemFeedbackDTO[]>([]);
    rows = input<number>(0);
    totalRecords = input<number>(0);

    refresh = output<void>();
    lazyEvent = output<TableLazyLoadEvent>();

    protected loadFeedbacksLazy($event: TableLazyLoadEvent) {
        this.lazyEvent.emit($event);
    }
}
