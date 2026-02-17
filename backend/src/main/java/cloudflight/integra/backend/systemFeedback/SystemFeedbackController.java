package cloudflight.integra.backend.systemFeedback;

import cloudflight.integra.backend.systemFeedback.model.SystemFeedbackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/systemfeedbacks")
public class SystemFeedbackController {

    private final SystemFeedbackMapper mapper;
    private final SystemFeedbackService service;

    public SystemFeedbackController(SystemFeedbackMapper mapper, SystemFeedbackService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public Page<SystemFeedbackDTO> getAll(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.getAllPaginated(pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public SystemFeedbackDTO getById(@PathVariable UUID id) {
        return service.getById(id).map(mapper::toDto).orElse(null);
    }

    @PostMapping
    public SystemFeedbackDTO create(@RequestBody SystemFeedbackDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public SystemFeedbackDTO update(@PathVariable UUID id, @RequestBody SystemFeedbackDTO dto) {
        return service.update(id, mapper.toEntity(dto)).map(mapper::toDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
