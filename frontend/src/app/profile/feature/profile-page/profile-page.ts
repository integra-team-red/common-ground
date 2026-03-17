import {Component, inject} from '@angular/core';
import {Card} from "primeng/card";
import {UserDetailsService} from "../../../services/UserDetailsService/user-details-service";
import {DatePipe} from "@angular/common";

@Component({
    selector: 'app-profile-page',
    imports: [
        Card,
        DatePipe
    ],
    templateUrl: './profile-page.html',
})
export class ProfilePage {
    private userDetailsService = inject(UserDetailsService);

    user = this.userDetailsService.getCurrentUser() ?? {username: "", email: "", joinedDate: ""};
}
