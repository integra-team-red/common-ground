package cloudflight.integra.backend.security;

import cloudflight.integra.backend.tag.TagService;
import cloudflight.integra.backend.user.CustomUserDetails;
import cloudflight.integra.backend.user.UserService;
import cloudflight.integra.backend.user.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final TagService tagService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
        UserService userService,
        TagService tagService,
        JwtService jwtService,
        AuthenticationManager authenticationManager
    )
    {
        this.userService = userService;
        this.tagService = tagService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody User user) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                jwtService.generateToken(authenticatedUser.getUsername(), authenticatedUser.getAuthorities())).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/set-tags-to-user/{username}")
    public ResponseEntity<HttpStatus> setTags(@PathVariable String username, @RequestBody List<Long> tagIds) {
        try{
            userService.setTagsToUser(username, tagService.getTagsFromIds(tagIds));

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/add-tags-to-user/{username}")
    public ResponseEntity<HttpStatus> addTags(@PathVariable String username, @RequestBody List<Long> tagIds) {
        try{
            userService.addTagsToUser(username, tagService.getTagsFromIds(tagIds));

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
