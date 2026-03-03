import {Component, inject, OnInit, signal} from '@angular/core';
import {HobbyGroupCard} from "../ui/hobby-group-card/hobby-group-card";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {PageHobbyGroupDto} from "@app/api/model/pageHobbyGroupDto";
import {Button} from "primeng/button";
import {Dialog} from "primeng/dialog";
import {FormsModule} from "@angular/forms";
import {Message} from "primeng/message";
import {InputText} from "primeng/inputtext";

@Component({
    selector: 'app-home',
    imports: [
        HobbyGroupCard,
        Button,
        Dialog,
        FormsModule,
        Message,
        InputText
    ],
    templateUrl: './home.html',
    styleUrl: './home.css',
    standalone: true
})
export class Home implements OnInit {
    hobbyGroupService = inject(HobbyGroupControllerService);

    hobbyGroups = signal<HobbyGroupDto[]>([])
    visible = signal<boolean>(false);

    newHobbyGroup: HobbyGroupDto = {
        name: "",
        description: "",
        radiusKm: 10
    }

    ngOnInit(): void {
        this.getHobbyGroups()
    }

    getHobbyGroups() {
        this.hobbyGroupService.getAllHobbyGroups({
            size: 10,
            page: 0
        }).subscribe((response: PageHobbyGroupDto) => {
            this.hobbyGroups.set(response.content ?? [])
        })
    }

    protected onSubmit() {
        this.hobbyGroupService.createNewHobbyGroup(this.newHobbyGroup).subscribe();
    }
}
