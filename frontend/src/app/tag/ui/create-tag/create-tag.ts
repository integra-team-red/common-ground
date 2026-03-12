import {Component, input, output} from '@angular/core';
import {Dialog} from "primeng/dialog";
import {FormsModule} from "@angular/forms";
import {Message} from "primeng/message";
import {Button} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {TagDto} from "@app/api/model/tagDto";

@Component({
    selector: 'app-create-tag-form',
    imports: [
        Dialog,
        FormsModule,
        Message,
        Button,
        InputTextModule
    ],
    templateUrl: './create-tag.html',
    styleUrl: './create-tag.css',
})

export class CreateTag {
    visible = input.required<boolean>();
    create = output<TagDto>();
    hide = output<void>();

    newTag: TagDto = { label: "" };

    protected onSubmit(): void {
        this.create.emit(this.newTag);
    }

    protected onHide(): void {
        this.hide.emit();
    }
}
