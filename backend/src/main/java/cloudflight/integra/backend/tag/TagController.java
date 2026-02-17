package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.TagDto;
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
    public Page<TagDto> getAll(@PageableDefault(sort = "label", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.service.getAll(pageable).map(this.mapper::toDto);
    }

    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        return this.service.getById(id).map(this.mapper::toDto).orElse(null);
    }

    @GetMapping("/normalized/{label}")
    public Page<TagDto> search(
        @PathVariable String label,
        @PageableDefault(sort = "label", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return this.service.getByNormalizedLabel(label, pageable).map(this.mapper::toDto);
    }

    @PostMapping()
    public TagDto create(@RequestBody TagDto dto) {
        return this.mapper.toDto(this.service.create(this.mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public TagDto update(@PathVariable Long id, @RequestBody TagDto dto) {
        return this.service.update(id, this.mapper.toEntity(dto)).map(this.mapper::toDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
