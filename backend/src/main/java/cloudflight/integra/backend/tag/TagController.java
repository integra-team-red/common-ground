package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.TagDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<TagDto> getAll() {
        return this.service.getAll().stream().map(this.mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        return this.service.getById(id).map(this.mapper::toDto).orElse(null);
    }

    @GetMapping("/normalized/{label}")
    public List<TagDto> search(@PathVariable String label) {
        return this.service.getByNormalizedLabel(label).stream().map(this.mapper::toDto).toList();
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
