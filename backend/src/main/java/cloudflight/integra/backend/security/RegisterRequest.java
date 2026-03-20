package cloudflight.integra.backend.security;

public record RegisterRequest(String username, String email, String password) {
}
