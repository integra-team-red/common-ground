package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupService;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.LocationService;
import cloudflight.integra.backend.location.model.Location;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<EventDto> getAll() {
        return service.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/after/{after}")
    public List<EventDto> getAllAfter(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH::mm:ss.SSSSSS") LocalDateTime after
    ) {
        return service.getAllByTime(after).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventDto getById(@PathVariable UUID id) {
        return service.getById(id)
            .map(mapper::toDto).orElse(null);
    }

    @GetMapping("/location/{locationId}")
    public List<EventDto> getAllEventsByLocationId(@PathVariable UUID locationId) {
        return service.getByLocationId(locationId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public EventDto create(@RequestBody EventDto dto) {
        Location location = locationService.getById(dto.locationId()).orElseThrow();
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId()).orElseThrow();
        return mapper.toDto(service.create(mapper.toEntity(dto, location, hobbyGroupById)));
    }

    @PutMapping("/{id}")
    public EventDto update(@PathVariable UUID id, @RequestBody EventDto dto) {
        HobbyGroup hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupId()).orElseThrow();
        Location location = locationService.getById(dto.locationId()).orElseThrow();
        return service.update(id, mapper.toEntity(dto, location, hobbyGroupById)).map(mapper::toDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
