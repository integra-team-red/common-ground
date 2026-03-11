package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupService;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.LocationService;
import cloudflight.integra.backend.location.model.Location;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;
    private final EventMapper mapper;
    private final LocationService locationService;

    private final HobbyGroupService hobbyGroupService;

    public EventController(
        EventService service,
        EventMapper mapper,
        LocationService locationService,
        HobbyGroupService hobbyGroupService
    ) {
        this.service = service;
        this.mapper = mapper;
        this.hobbyGroupService = hobbyGroupService;
        this.locationService = locationService;
    }

    @GetMapping
    @Operation(
        summary = "Get all Events",
        operationId = "getAllEvents",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(mediaType = "application/json")
            ),
        }
    )
    public Page<EventDto> getAll(
        @PageableDefault(sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.getAll(pageable).map(mapper::toDto);
    }


    @GetMapping("/after/{after}")
    @Operation(
        summary = "Get all Events After A Certain Time",
        operationId = "getAllEventsAfter",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))),
        }
    )
    public Page<EventDto> getAllAfter(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH::mm:ss.SSSSSS") LocalDateTime after, Pageable pageable
    ) {
        return service.getAllByTime(after, pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get Events by Id",
        operationId = "getById",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))),
        }
    )
    public EventDto getById(@PathVariable UUID id) {
        return service.getById(id)
            .map(mapper::toDto).orElse(null);
    }

    @GetMapping("/location/{locationId}")
    @Operation(
        summary = "Get all Events by Location Id",
        operationId = "getAllEventsByLocationId",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))),
        }
    )
    public Page<EventDto> getAllEventsByLocationId(@PathVariable UUID locationId, Pageable pageable) {
        return service.getByLocationId(locationId, pageable).map(mapper::toDto);
    }

    @PostMapping
    @Operation(
        summary = "Create an Event",
        operationId = "createEvent",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EventDto.class))),
        }
    )
    public EventDto create(@RequestBody EventDto dto) {
        Location location = locationService.getById(dto.locationId()).orElseThrow();
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId()).orElseThrow();
        return mapper.toDto(service.create(mapper.toEntity(dto, location, hobbyGroupById)));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an Event",
        operationId = "updateEvent",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EventDto.class))),
        }
    )
    public EventDto update(@PathVariable UUID id, @RequestBody EventDto dto) {
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId()).orElseThrow();
        Location location = locationService.getById(dto.locationId()).orElseThrow();
        return service.update(id, mapper.toEntity(dto, location, hobbyGroupById)).map(mapper::toDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an Event",
        operationId = "deleteEvent",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EventDto.class))),
        }
    )
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
