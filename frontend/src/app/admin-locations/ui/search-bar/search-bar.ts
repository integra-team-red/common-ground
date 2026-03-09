import {Component, output} from '@angular/core';
import {IconField} from "primeng/iconfield";
import {InputIcon} from "primeng/inputicon";
import {InputText} from "primeng/inputtext";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-search-bar',
    imports: [
        IconField,
        InputIcon,
        InputText,
        FormsModule
    ],
    templateUrl: './search-bar.html',
    styleUrl: './search-bar.css',
    standalone: true,
})
export class SearchBar {
    searchTerm: string = '';
    search = output<string>();

    onSearch(value: string) {
        this.searchTerm = value;
        this.search.emit(value ?? '');
    }
}
