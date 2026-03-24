import {Component, computed, inject, input, output} from '@angular/core';
import {Card} from "primeng/card";
import {Avatar} from "primeng/avatar";
import {Chip} from "primeng/chip";
import {Button} from "primeng/button";
import {TagControllerService} from "@app/api/api/tagController.service";
import {forkJoin, of, switchMap} from "rxjs";
import {toObservable, toSignal} from "@angular/core/rxjs-interop";
import {UserDetailsService} from "../../../services/UserDetailsService/user-details-service";
import {HobbyGroupDto} from "@app/api/model/hobbyGroupDto";
import {HobbyGroupControllerService} from "@app/api/api/hobbyGroupController.service";
import {ToastService} from "../../../toast-service/toast-service";
import {DeleteHobbyGroup} from "../delete-hobby-group/delete-hobby-group";

@Component({
    selector: 'app-hobby-group-card',
    imports: [
        Card,
        Avatar,
        Chip,
        Button,
        DeleteHobbyGroup,
    ],
    standalone: true,
    templateUrl: './hobby-group-card.html'
})
export class HobbyGroupCard {

    hobbyGroupDto = input.required<HobbyGroupDto>()
    hobbyGroupService = inject(HobbyGroupControllerService);
    tagService = inject(TagControllerService)
    userDetailsService = inject(UserDetailsService);
    groupUpdated = output<void>();
    toastService = inject(ToastService);
    groupDeleted = output<void>();

    isMember = computed(() =>
        (this.hobbyGroupDto().memberIds as unknown as string[])
            ?.includes(this.userDetailsService.getCurrentUser()?.id ?? '')
    );
    isOwner = computed(() =>
        this.hobbyGroupDto().ownerID === this.userDetailsService.getCurrentUser()?.id
    );
    private tags = toObservable(this.hobbyGroupDto).pipe(
        switchMap(group => {
            const ids = group.tagIds;
            if (!ids || ids.length === 0) return of([]);
            const requests = ids.map(id => this.tagService.getTagById(id));
            return forkJoin(requests);
        })
    );
    cardTags = toSignal(this.tags, {initialValue: []});

    join() {
        this.hobbyGroupService.joinHobbyGroup(this.hobbyGroupDto().id!)
            .subscribe({
                next: () => {
                    this.groupUpdated.emit();
                    this.toastService.showSuccess("You joined the group.")
                },
                error: () => {
                    this.toastService.showError("Failed to join group.");
                }
            });
    }

    leave() {
        this.hobbyGroupService.leaveHobbyGroup(this.hobbyGroupDto().id!)
            .subscribe({

                next: () => {
                    this.groupUpdated.emit();
                    this.toastService.showSuccess("You left the group");
                },
                error: () => {
                    this.toastService.showError("Failed to leave group.");
                }
            });
    }


}

