import {Component, output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {IconField} from "primeng/iconfield";
import {InputIcon} from "primeng/inputicon";
import {InputText} from "primeng/inputtext";

@Component({
    selector: 'app-searchbar',
    imports: [
        FormsModule,
        IconField,
        InputIcon,
        InputText
    ],
    templateUrl: './searchbar.html',
})
export class Searchbar {
    searchTerm: string = '';
    search = output<string>();

    onSearch(value: string) {
        this.searchTerm = value;
        this.search.emit(value ?? '');
    }
}
