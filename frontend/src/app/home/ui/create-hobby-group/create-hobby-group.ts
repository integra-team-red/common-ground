import {Component, inject, output, signal} from "@angular/core";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {MessageService} from "primeng/api";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";
import {TagDto} from "@app/api/model/tagDto";
import {PageTagDto} from "@app/api/model/pageTagDto";
import {TagControllerService} from "@app/api/api/tagController.service";
import {FormsModule, NgForm} from "@angular/forms";
import {Dialog} from "primeng/dialog";
import {InputText} from "primeng/inputtext";
import {Message} from "primeng/message";
import {MultiSelect} from "primeng/multiselect";
import {Button} from "primeng/button";

@Component({
    selector: 'app-create-hobby-group',
    standalone: true,
    imports: [
        Dialog,
        FormsModule,
        InputText,
        Message,
        MultiSelect,
        Button
    ],
    templateUrl: 'create-hobby-group.html',
})
export class CreateHobbyGroup {
    hobbyGroupService = inject(HobbyGroupControllerService);
    visible = signal<boolean>(false);
    messageService = inject(MessageService);

    refreshHobbyGroup = output<void>()

    newHobbyGroup: HobbyGroupDto = {
        name: "",
        description: "",
        radiusKm: 10,
        tagIds: []
    }
    tagService = inject(TagControllerService)
    tags = signal<TagDto[]>([]);
    selectedTags = signal<TagDto[]>([]);

    ngOnInit(): void {
        this.getTags()
    }

    getTags() {
        this.tagService.getAllTags({size: 10, page: 0})
            .subscribe((response: PageTagDto) => {
                this.tags.set(response.content ?? []);
            });
    }

    protected onSubmit(form: NgForm) {
        if (form.valid) {
            this.hobbyGroupService.createNewHobbyGroup(this.newHobbyGroup).subscribe({
                next: (response) => {
                    this.visible.set(false);
                    this.refreshHobbyGroup.emit();
                    form.resetForm();

                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Hobby Group created successfully!'
                    });
                    this.newHobbyGroup = {
                        name: "",
                        description: "",
                        radiusKm: 10,
                        tagIds: []
                    };
                },
                error: (err) => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to create group'
                    });
                }
            });
        }

    }
}
