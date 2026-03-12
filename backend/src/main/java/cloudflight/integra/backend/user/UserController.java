package cloudflight.integra.backend.user;

import cloudflight.integra.backend.tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final TagService tagService;

    public UserController(UserService userService, TagService tagService) {
        this.userService = userService;
        this.tagService = tagService;
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
}
