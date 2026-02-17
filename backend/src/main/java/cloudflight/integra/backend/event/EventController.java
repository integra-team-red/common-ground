package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupService;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;
    private final EventMapper mapper;

    private final HobbyGroupService hobbyGroupService;

    public EventController(EventService service, EventMapper mapper, HobbyGroupService hobbyGroupService) {
        this.service = service;
        this.mapper = mapper;
        this.hobbyGroupService = hobbyGroupService;
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

    @PostMapping
    public EventDto create(@RequestBody EventDto dto) {
        Optional<HobbyGroup> hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupID());
        return mapper.toDto(service.create(mapper.toEntity(dto, hobbyGroupById)));
    }

    @PutMapping("/{id}")
    public EventDto update(@PathVariable UUID id, @RequestBody EventDto dto) {
        Optional<HobbyGroup> hobbyGroupById = hobbyGroupService.getById(dto.hobbyGroupID());
        return service.update(id, mapper.toEntity(dto, hobbyGroupById))
            .map(mapper::toDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
