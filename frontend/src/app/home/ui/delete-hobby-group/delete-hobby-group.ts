import {Component, inject, input, output, signal} from '@angular/core';
import {Button} from "primeng/button";
import {Dialog} from "primeng/dialog";
import {ToastService} from "../../../toast-service/toast-service";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";

@Component({
    selector: 'app-delete-hobby-group',
    imports: [
        Button,
        Dialog
    ],
    templateUrl: './delete-hobby-group.html',
})
export class DeleteHobbyGroup {
    hobbyGroupService = inject(HobbyGroupControllerService);
    toastService = inject(ToastService);
    hobbyGroupDto = input.required<HobbyGroupDto>();
    visible = signal<boolean>(false);

    groupDeleted = output<string>();

    openDialog() {
        this.visible.set(true);
    }

    confirmDelete() {
        const hobbyGroupId = this.hobbyGroupDto().id;
        if (hobbyGroupId) {
            this.hobbyGroupService.deleteHobbyGroup(hobbyGroupId).subscribe({
                next: () => {
                    this.visible.set(false);
                    this.toastService.showSuccess("Hobby Group was successfully deleted.");
                    this.groupDeleted.emit(hobbyGroupId);
                },
                error: () => {
                    this.toastService.showError("Could not delete hobby group.");
                }

            });
        }
    }
}
