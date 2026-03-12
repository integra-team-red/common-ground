import {Component, input, output} from '@angular/core';
import {Dialog} from "primeng/dialog";
import {FormsModule} from "@angular/forms";
import {Message} from "primeng/message";
import {Button} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {TagDto} from "@app/api/model/tagDto";

@Component({
    selector: 'app-update-tag-form',
    imports: [
        Dialog,
        FormsModule,
        Message,
        Button,
        InputTextModule
    ],
    templateUrl: './update-tag.html',
    styleUrl: './update-tag.css',
})

export class UpdateTag {
    updateTag = input.required<TagDto>();
    visible = input.required<boolean>();
    create = output<TagDto>();
    hide = output<void>();

    protected onSubmit(): void {
        this.create.emit(this.updateTag());
    }

    protected onHide(): void {
        this.hide.emit();
    }
}
