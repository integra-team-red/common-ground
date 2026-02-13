package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.location.model.LocationDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    public LocationController(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @GetMapping("/search/{name}")
    public List<LocationDto> getByName(@PathVariable("name") String name) {
        return locationService.getByName(name).stream().map(locationMapper::toDto).toList();
    }

    @GetMapping
    public List<LocationDto> getAll() {
        return locationService.getAll().stream().map(locationMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public LocationDto getById(@PathVariable UUID id) {
        return locationService.getById(id).map(locationMapper::toDto).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        locationService.delete(id);
    }

    @PostMapping
    public LocationDto create(@RequestBody LocationDto locationDto) {
        Location entity = locationMapper.toEntity(locationDto);
        Location location = locationService.create(entity);
        return locationMapper.toDto(location);
    }

}
