package cloudflight.integra.backend.security;

import cloudflight.integra.backend.matrix.provisioning.MatrixProvisioningResponse;
import cloudflight.integra.backend.user.CustomUserDetails;
import cloudflight.integra.backend.matrix.provisioning.MatrixProvisioningService;
import cloudflight.integra.backend.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.context.annotation.Profile;
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
@Profile("!test")
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CookieProperties cookieProperties;
    private final MatrixProvisioningService matrixProvisioningService;

    public AuthController(
        UserService userService,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        CookieProperties cookieProperties,
        MatrixProvisioningService matrixProvisioningService
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.cookieProperties = cookieProperties;
        this.matrixProvisioningService = matrixProvisioningService;
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
                    schema = @Schema(implementation = LoginResponse.class)
                )
            )
        }
    )
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
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

            String matrixMessage = null;

            if (!authenticatedUser.hasMatrixAccount()) {
                MatrixProvisioningResponse matrixResponse = this.matrixProvisioningService.provisionAccount(
                    this.userService.getByUsername(authenticatedUser.getUsername()));

                matrixMessage = matrixResponse.message();
            }

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(matrixMessage));
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
