package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupService;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.LocationService;
import cloudflight.integra.backend.location.model.Location;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

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
    public Page<EventDto> getAll(
        @PageableDefault(sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable){
        return service.getAll(pageable).map(mapper::toDto);
    }

    @GetMapping("/after/{after}")
    public Page<EventDto> getAllAfter(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH::mm:ss.SSSSSS") LocalDateTime after, Pageable pageable
    ) {
        return service.getAllByTime(after, pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public EventDto getById(@PathVariable UUID id) {
        return mapper.toDto(service.getById(id));
    }

    @GetMapping("/location/{locationId}")
    public Page<EventDto> getAllEventsByLocationId(@PathVariable UUID locationId, Pageable pageable) {
        return service.getByLocationId(locationId, pageable).map(mapper::toDto);
    }

    @PostMapping
    public EventDto create(@RequestBody EventDto dto) {
        Location location = locationService.getById(dto.locationId());
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId());
        return mapper.toDto(service.create(mapper.toEntity(dto, location, hobbyGroupById)));
    }

    @PutMapping("/{id}")
    public EventDto update(@PathVariable UUID id, @RequestBody EventDto dto) {
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId());
        Location location = locationService.getById(dto.locationId());
        return mapper.toDto(service.update(id, mapper.toEntity(dto, location, hobbyGroupById)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
