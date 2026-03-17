package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import cloudflight.integra.backend.user.UserService;
import cloudflight.integra.backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/hobbygroups")
public class HobbyGroupController {
    private final HobbyGroupService service;
    private final HobbyGroupMapper mapper;
    private final UserService userService;

    public HobbyGroupController(HobbyGroupService service, HobbyGroupMapper mapper, UserService userService) {
        this.service = service;
        this.mapper = mapper;
        this.userService = userService;
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
    public HobbyGroupDto create(@RequestBody HobbyGroupDto groupDto, @AuthenticationPrincipal UserDetails currentUser) {
        User owner = userService.getByUsername(currentUser.getUsername());
        return mapper.toDto(service.create(mapper.toEntity(groupDto, owner)));
    }

    @PutMapping("/{id}")
    public HobbyGroupDto update(@PathVariable UUID id,
                                @RequestBody HobbyGroupDto groupDto,
                                @AuthenticationPrincipal UserDetails currentUser) {
        User owner = userService.getByUsername(currentUser.getUsername());
        HobbyGroup updatedHobbyGroup = service.update(id, mapper.toEntity(groupDto, owner));
        return mapper.toDto(updatedHobbyGroup);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
