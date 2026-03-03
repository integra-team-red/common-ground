package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<Page<HobbyGroupDto>> getAll(Pageable pageable) {
        return List.of(service.getAll(pageable).map(mapper::toDto));
    }

    @GetMapping("/filter")
    public List<Page<HobbyGroupDto>> filterByName(@RequestParam String name, Pageable pageable) {
        return List.of(service.filterByName(name, pageable).map(mapper::toDto));
    }

    @GetMapping("/{id}")
    public HobbyGroupDto getById(@PathVariable UUID id) {
        return mapper.toDto(service.getById(id));
    }


    @PostMapping
    public HobbyGroupDto create(@RequestBody HobbyGroupDto groupDto) {
        return mapper.toDto(service.create(mapper.toEntity(groupDto)));
    }

    @PutMapping("/{id}")
    public HobbyGroupDto update(@PathVariable UUID id, @RequestBody HobbyGroupDto groupDto) {
        return mapper.toDto(service.update(id, mapper.toEntity(groupDto)));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
