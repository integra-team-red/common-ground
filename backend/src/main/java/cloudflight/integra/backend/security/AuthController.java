package cloudflight.integra.backend.security;

import cloudflight.integra.backend.user.CustomUserDetails;
import cloudflight.integra.backend.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;


@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CookieProperties cookieProperties;

    public AuthController(
        UserService userService,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        CookieProperties cookieProperties
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.cookieProperties = cookieProperties;
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login user",
        operationId = "loginUser",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)
                )
            )
        }
    )
    public ResponseEntity<HttpStatus> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

            CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();

            String jwtToken = jwtService.generateToken(authenticatedUser.getUsername(),
                authenticatedUser.getAuthorities());
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(cookieProperties.getPath())
                .maxAge(Duration.ofHours(cookieProperties.getMaxAgeHours()))
                .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register a New User",
        operationId = "registerNewUser",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content =
                @Content(
                    mediaType = "application/json")),
        }
    )
    public ResponseEntity<HttpStatus> register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
