package cloudflight.integra.backend.matrix.api.model;

public record MatrixRegisterRequest(String nonce, String username, String password, boolean admin, String mac) {
}
