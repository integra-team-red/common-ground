package cloudflight.integra.backend.systemFeedback;


import cloudflight.integra.backend.systemFeedback.model.SystemFeedbackDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<SystemFeedbackDTO> getAll() {
        return service.getAll().stream()
            .sorted((f1, f2) -> {
                if (f1.getCreatedAt() == null) return 1;
                if (f2.getCreatedAt() == null) return -1;
                return f2.getCreatedAt().compareTo(f1.getCreatedAt());
            })
            .map(mapper::toDto)
            .collect(Collectors.toList());
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
