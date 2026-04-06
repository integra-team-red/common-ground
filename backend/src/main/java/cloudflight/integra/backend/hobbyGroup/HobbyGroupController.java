package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import cloudflight.integra.backend.tag.TagService;
import cloudflight.integra.backend.user.UserService;
import cloudflight.integra.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/hobbygroups")
public class HobbyGroupController {
    private final HobbyGroupService hobbyGroupService;
    private final TagService tagService;
    private final HobbyGroupMapper mapper;
    private final UserService userService;

    public HobbyGroupController(
        HobbyGroupService service,
        TagService tagService,
        HobbyGroupMapper mapper,
        UserService userService
    ) {
        this.hobbyGroupService = service;
        this.tagService = tagService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Operation(
        summary = "Get all Hobby Groups",
        operationId = "getAllHobbyGroups",
        responses={
            @ApiResponse(content = @Content(mediaType = "application/json"))
        }
    )
    @GetMapping
    public Page<HobbyGroupDto> getAll(Pageable pageable) {
        return hobbyGroupService.getAll(pageable)
            .map(group -> mapper.toDto(group, tagService.getIdsFromTags(group.getTags())));
    }

    @GetMapping("/filter")
    @Operation(
        summary = "Get all Hobby Groups for which the title contain a given string",
        operationId = "filterAllHobbyGroupsByName",
        responses={
            @ApiResponse(content = @Content(mediaType = "application/json"))
        }
    )
    public Page<HobbyGroupDto> filterByName(@RequestParam String name, Pageable pageable) {
        return hobbyGroupService.filterByName(name, pageable)
            .map(group -> mapper.toDto(group, tagService.getIdsFromTags(group.getTags())));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get a single Hobby Group by its ID",
        operationId = "getHobbyGroup",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Found the group",
                content = @Content(
                    schema = @Schema(implementation = HobbyGroupDto.class))),
            @ApiResponse(responseCode = "404", description = "Hobby Group not found"),
            @ApiResponse(content=@Content(mediaType = "application/json"))
        }
    )
    public HobbyGroupDto getById(@PathVariable UUID id) {
        HobbyGroup group = hobbyGroupService.getById(id);
        return mapper.toDto(group, tagService.getIdsFromTags(group.getTags()));
    }


    @PostMapping
    @Operation(
        summary = "Add an hobbyGroup to the repository",
        operationId = "createNewHobbyGroup",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "HobbyGroup added successfully; returns the added hobbyGroup",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HobbyGroupDto.class))),
        })
    public HobbyGroupDto create(
        @RequestBody HobbyGroupDto groupDto,
        @AuthenticationPrincipal UserDetails currentUser
    ) {
        User owner = userService.getByUsername(currentUser.getUsername());
        HobbyGroup createHobbyGroup = hobbyGroupService.create(mapper.toEntity(
            groupDto,
            tagService.getTagsFromIds(groupDto.tagIds()),
            owner)
        );
        return mapper.toDto(createHobbyGroup, groupDto.tagIds());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Add an hobbyGroup to the repository",
        operationId = "updateHobbyGroup",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "HobbyGroup added successfully; returns the added hobbyGroup",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HobbyGroupDto.class))),
        })
    public HobbyGroupDto update(
        @PathVariable UUID id,
        @RequestBody HobbyGroupDto groupDto,
        @AuthenticationPrincipal UserDetails currentUser
    ) {
        User owner = userService.getByUsername(currentUser.getUsername());
        HobbyGroup updateHobbyGroup = hobbyGroupService.update(id, mapper.toEntity(
            groupDto,
            tagService.getTagsFromIds(groupDto.tagIds()),
            owner));

        return mapper.toDto(updateHobbyGroup, groupDto.tagIds());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a Hobby Group by ID",
        operationId = "deleteHobbyGroup",
        responses = {
            @ApiResponse(responseCode = "204", description = "Group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(content = @Content(mediaType = "application/json"))
        }
    )
    public void delete(@PathVariable UUID id) {
        hobbyGroupService.delete(id);
    }

    @PostMapping("/{id}/join")
    @Operation(
        summary = "Add the current user to the hobbyGroup",
        operationId = "joinHobbyGroup",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "HobbyGroup was joined successfully;",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HobbyGroupDto.class))),
            @ApiResponse(
                responseCode = "409",
                description = "User is already a member"),
            @ApiResponse(content = @Content(mediaType = "application.json"))
        })
    public HobbyGroupDto joinHobbyGroup(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        User newMember = userService.getByUsername(userDetails.getUsername());
        HobbyGroup updatedHobbyGroup = hobbyGroupService.addNewMemberToHobbyGroup(newMember, id);
        return mapper.toDto(updatedHobbyGroup, tagService.getIdsFromTags(updatedHobbyGroup.getTags()));
    }

    @DeleteMapping("/{id}/leave")
    @Operation(
        summary = "Leave  Hobby Group",
        operationId = "leaveHobbyGroup",
        responses = {
            @ApiResponse(
                responseCode = "409",
                description = "User was not a member",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HobbyGroupDto.class)))
        }
    )
    public HobbyGroupDto leaveHobbyGroup(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        User oldMember = userService.getByUsername(userDetails.getUsername());
        HobbyGroup updatedHobbyGroup = hobbyGroupService.deleteMemberFromHobbyGroup(oldMember, id);
        return mapper.toDto(updatedHobbyGroup, tagService.getIdsFromTags(updatedHobbyGroup.getTags()));
    }
}
