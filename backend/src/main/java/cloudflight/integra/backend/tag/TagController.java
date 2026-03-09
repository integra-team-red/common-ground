package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.TagDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;
    private final TagMapper mapper;

    public TagController(TagService service, TagMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    @Operation(
        summary = "Get all Tags",
        operationId = "getAllTags",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)
                )
            )
        }
    )
    public Page<TagDto> getAll(@PageableDefault(sort = "label", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.service.getAll(pageable).map(this.mapper::toDto);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get Tag by ID",
        operationId = "getTagById",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TagDto.class)
                )
            )}
    )
    public TagDto getById(@PathVariable Long id) {
        return mapper.toDto(this.service.getById(id));
    }

    @GetMapping("/normalized/{value}")
    @Operation(
        summary = "Filter Tags by normalized label",
        operationId = "FilterTags",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)
                )
            )
        }
    )
    public Page<TagDto> filter(
        @PathVariable String value,
        @PageableDefault(sort = "label", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return this.service.filterNormalizedLabel(value, pageable).map(this.mapper::toDto);
    }

    @PostMapping()
    @Operation(
        summary = "Add a new Tag to the repository",
        operationId = "createNewTag",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Tag added successfully; returns the added Tag",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TagDto.class)
                )
            )
        }
    )
    public TagDto create(@RequestBody TagDto dto) {
        return this.mapper.toDto(this.service.create(this.mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an existing Tag from the repository",
        operationId = "updateTag",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Tag updated successfully; returns the updated Tag",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TagDto.class)
                )
            )
        }
    )
    public TagDto update(@PathVariable Long id, @RequestBody TagDto dto) {
        return mapper.toDto(this.service.update(id, this.mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an existing Tag from the repository",
        operationId = "deleteTag",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Tag deleted successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = void.class)
                )
            )
        }
    )
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
