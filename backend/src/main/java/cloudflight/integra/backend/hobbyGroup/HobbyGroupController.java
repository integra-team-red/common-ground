package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/hobbygroups")
public class HobbyGroupController {
    private final HobbyGroupService service;
    private final HobbyGroupMapper mapper;

    public HobbyGroupController(HobbyGroupService service, HobbyGroupMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(
        summary = "Get all Hobby Groups",
        operationId = "getAllHobbyGroups",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))),
        }
    )
    public Page<HobbyGroupDto> getAll(Pageable pageable) throws InterruptedException {
        return service.getAll(pageable).map(mapper::toDto);
    }

    @GetMapping("/filter")
    public Page<HobbyGroupDto> filterByName(@RequestParam String name, Pageable pageable) {
        return service.filterByName(name, pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public HobbyGroupDto getById(@PathVariable UUID id) {
        return service.getById(id).map(mapper::toDto).orElse(null);
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
    public HobbyGroupDto create(@RequestBody HobbyGroupDto groupDto) {
        return mapper.toDto(service.create(mapper.toEntity(groupDto)));
    }

    @PutMapping("/{id}")
    public HobbyGroupDto update(@PathVariable UUID id, @RequestBody HobbyGroupDto groupDto) {
        return service.update(id, mapper.toEntity(groupDto)).map(mapper::toDto).orElse(null);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
