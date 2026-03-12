package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cloudflight.integra.backend.tag.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/hobbygroups")
public class HobbyGroupController {
    private final HobbyGroupService hobbyGroupService;
    private final TagService tagService;
    private final HobbyGroupMapper mapper;

    public HobbyGroupController(HobbyGroupService service, TagService tagService, HobbyGroupMapper mapper) {
        this.hobbyGroupService = service;
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<HobbyGroupDto> getAll(Pageable pageable) {
        return hobbyGroupService.getAll(pageable)
            .map(group -> mapper.toDto(group, tagService.getIdsFromTags(group.getTags())));
    }

    @GetMapping("/filter")
    public Page<HobbyGroupDto> filterByName(@RequestParam String name, Pageable pageable) {
        return hobbyGroupService.filterByName(name, pageable)
            .map(group -> mapper.toDto(group, tagService.getIdsFromTags(group.getTags())));
    }

    @GetMapping("/{id}")
    public HobbyGroupDto getById(@PathVariable UUID id) throws NoSuchElementException {
        return hobbyGroupService.getById(id)
            .map(group -> mapper.toDto(group, tagService.getIdsFromTags(group.getTags()))).orElseThrow();
    }


    @PostMapping
    public HobbyGroupDto create(@RequestBody HobbyGroupDto groupDto) {
        HobbyGroup createHobbyGroup = hobbyGroupService.create(mapper.toEntity(
            groupDto,
            tagService.getTagsFromIds(groupDto.tagIds())));

        return mapper.toDto(createHobbyGroup, groupDto.tagIds());
    }

    @PutMapping("/{id}")
    public HobbyGroupDto update(@PathVariable UUID id, @RequestBody HobbyGroupDto groupDto) {
        Optional<HobbyGroup> updateHobbyGroup = hobbyGroupService.update(id, mapper.toEntity(
            groupDto,
            tagService.getTagsFromIds(groupDto.tagIds())));

        return updateHobbyGroup.map(group -> mapper.toDto(group, groupDto.tagIds()))
            .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        hobbyGroupService.delete(id);
    }

}
