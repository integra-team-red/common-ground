package cloudflight.integra.backend.systemFeedback;

import cloudflight.integra.backend.systemFeedback.model.SystemFeedbackDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/systemfeedbacks")
public class SystemFeedbackController {

    private final SystemFeedbackMapper mapper;
    private final SystemFeedbackService service;

    public SystemFeedbackController(SystemFeedbackMapper mapper, SystemFeedbackService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    @Operation(
        summary = "Get all System Feedbacks",
        operationId = "getAllSystemFeedbacks",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
        }
    )
    public Page<SystemFeedbackDTO> getAll(
        @RequestParam(required = false) String search,
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.getAllPaginated(search, pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get System Feedback by Id",
        operationId = "getSystemFeedbackById",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SystemFeedbackDTO.class)))
        }
    )
    public SystemFeedbackDTO getById(@PathVariable UUID id) {
        return mapper.toDto(service.getById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a System Feedback",
        operationId = "createSystemFeedback",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SystemFeedbackDTO.class)))
        }
    )
    public SystemFeedbackDTO create(@RequestBody SystemFeedbackDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update a System Feedback",
        operationId = "updateSystemFeedback",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SystemFeedbackDTO.class)))
        }
    )
    public SystemFeedbackDTO update(@PathVariable UUID id, @RequestBody SystemFeedbackDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a System Feedback",
        operationId = "deleteSystemFeedback",
        responses = {
            @ApiResponse(responseCode = "200")
        }
    )
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
