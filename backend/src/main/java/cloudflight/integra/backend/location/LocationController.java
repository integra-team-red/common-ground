package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.location.model.LocationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @Operation(
        operationId = "getByName",
        summary = "Search locations by name",
        responses={
            @ApiResponse(content = @Content(mediaType = "application/json"))
        }
    )
    public Page<LocationDto> getByName(@PathVariable("name") String name, Pageable pageable) {
        return locationService.getByName(name, pageable).map(locationMapper::toDto);
    }

    @GetMapping
    @Operation(
        operationId = "getLocations",
        summary = "Get all locations, optionally filtered by user",
        responses={
            @ApiResponse(content = @Content(mediaType = "application/json"))
        }
    )
    public Page<LocationDto> getAll(
        @RequestParam(required = false) UUID userId,
        Pageable pageable
    ) {
        if (userId != null) {
            return locationService.getByCreator(userId, pageable)
                .map(locationMapper::toDto);
        }
        return locationService.getAll(pageable).map(locationMapper::toDto);
    }

    @GetMapping("/{id}")
    public LocationDto getById(@PathVariable UUID id) {
        return locationMapper.toDto(locationService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a Location",
        operationId = "deleteLocation",
        responses = {
            @ApiResponse(
                responseCode = "200"
            ),
            @ApiResponse(content = @Content(mediaType = "application/json"))
        }
    )
    public void delete(@PathVariable UUID id) {
        locationService.delete(id);
    }


    @PostMapping
    @Operation(
        summary = "Create a Location",
        operationId = "createLocation",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)
                )
            )
        }
    )
    public LocationDto create(@RequestBody LocationDto locationDto) {
        Location entity = locationMapper.toEntity(locationDto);
        Location location = locationService.create(entity);
        return locationMapper.toDto(location);
    }

}
