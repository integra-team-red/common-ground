package cloudflight.integra.backend.coffeemug;

import cloudflight.integra.backend.coffeemug.model.CoffeeMugDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coffeemugs")
public class CoffeeMugController {
    private final CoffeeMugService service;
    private final CoffeeMugMapper mapper;

    public CoffeeMugController(CoffeeMugService service, CoffeeMugMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CoffeeMugDto> getAll() {
        return service.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CoffeeMugDto getById(@PathVariable Long id) {
        return mapper.toDto(service.getById(id));
    }

    @PostMapping
    public CoffeeMugDto create(@RequestBody CoffeeMugDto dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public CoffeeMugDto update(@PathVariable Long id, @RequestBody CoffeeMugDto dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
