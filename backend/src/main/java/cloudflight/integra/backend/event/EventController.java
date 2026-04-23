package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.event.model.EventMapDto;
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
import java.util.List;
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
        operationId = "getAllEvents"
    )
    public Page<EventDto> getAll(
        @PageableDefault(sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.getAll(pageable).map(mapper::toDto);
    }

    @GetMapping("/map")
    @Operation(
        summary="Get all Events for showing on map",
        operationId = "getAllEventsForMap"
    )
    public List<EventMapDto> getAllEventsForMap(){
        return service.getAllEventsForMap().stream()
            .map(mapper::toMapDto)
            .toList();
    }

    @GetMapping("/after/{after}")
    @Operation(
        summary = "Get all Events After A Certain Time",
        operationId = "getAllEventsAfter"
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
                    schema = @Schema(implementation = EventDto.class))),
        }
    )
    public EventDto getById(@PathVariable UUID id) {
        return mapper.toDto(service.getById(id));
    }

    @GetMapping("/location/{locationId}")
    @Operation(
        summary = "Get all Events by Location Id",
        operationId = "getAllEventsByLocationId"
    )
    public Page<EventDto> getAllEventsByLocationId(@PathVariable UUID locationId, Pageable pageable) {
        return service.getByLocationId(locationId, pageable).map(mapper::toDto);
    }


    @GetMapping("/title/{title}")
    @Operation(
        summary = "Get all Events by Title",
        operationId = "getAllEventsByTitleInput"
    )
    public Page<EventDto> getAllEventsByTitle(@PathVariable String title, Pageable pageable) {
        return service.getByEventTitle(title, pageable).map(mapper::toDto);
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
        Location location = locationService.getById(dto.locationId());
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId());
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
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId());
        Location location = locationService.getById(dto.locationId());
        return mapper.toDto(service.update(id, mapper.toEntity(dto, location, hobbyGroupById)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an Event",
        operationId = "deleteEvent"
    )
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
