import {Component, input, output} from '@angular/core';
import {TableModule} from "primeng/table";
import {LocationDto} from "@app/api/model/locationDto";
import {Button} from "primeng/button";

@Component({
    selector: 'app-locations-table',
    imports: [
        TableModule,
        Button
    ],
    templateUrl: './locations-table.html',
    styleUrl: './locations-table.css',
    standalone: true
})
export class LocationsTable {
    locations = input<LocationDto[]>([]);
    totalRecords = input<number>(0);
    rows = input<number>(10);
    delete = output<LocationDto>({});
    pageChange = output<any>();

    onDelete(location: LocationDto) {
        this.delete.emit(location);
    }

    onLazyLoad(event: any) {
        this.pageChange.emit(event);
    }
}
