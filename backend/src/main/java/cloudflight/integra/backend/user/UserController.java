package cloudflight.integra.backend.user;

import cloudflight.integra.backend.tag.TagService;
import cloudflight.integra.backend.user.model.User;
import cloudflight.integra.backend.user.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final TagService tagService;
    private final UserMapper userMapper;

    public UserController(UserService userService, TagService tagService, UserMapper userMapper) {
        this.userService = userService;
        this.tagService = tagService;
        this.userMapper = userMapper;
    }

    @PutMapping("/users/{username}/tags")
    public ResponseEntity<HttpStatus> setTags(@PathVariable String username, @RequestBody List<Long> tagIds) {
        try{
            userService.setTagsToUser(username, tagService.getTagsFromIds(tagIds));

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/users/me")
    @Operation(
        summary = "Get the information from the user",
        operationId = "getUserInformation",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))),
        })
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getByUsername(userDetails.getUsername());
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
