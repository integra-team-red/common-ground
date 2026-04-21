package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.location.model.LocationDto;
import cloudflight.integra.backend.user.UserService;
import cloudflight.integra.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final UserService userService;

    public LocationController(LocationService locationService, LocationMapper locationMapper, UserService userService) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.userService = userService;
    }

    @GetMapping("/search/{name}")
    @Operation(
        operationId = "getByName",
        summary = "Search locations by name"
    )
    public Page<LocationDto> getByName(@PathVariable("name") String name, Pageable pageable) {
        return locationService.getByName(name, pageable).map(locationMapper::toDto);
    }

    @GetMapping
    @Operation(
        operationId = "getLocations",
        summary = "Get all locations, optionally filtered by user"
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

    @DeleteMapping("/admin/{id}")
    @Operation(
        summary = "Delete a Location",
        operationId = "deleteLocation",
        responses = {
            @ApiResponse(responseCode = "200")
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
    public LocationDto create(@RequestBody LocationDto locationDto,  @AuthenticationPrincipal UserDetails currentUser) {
        User creator = userService.getByUsername(currentUser.getUsername());
        Location entity = locationMapper.toEntity(locationDto);
        entity.setCreator(creator);
        Location location = locationService.create(entity);
        return locationMapper.toDto(location);
    }

}
