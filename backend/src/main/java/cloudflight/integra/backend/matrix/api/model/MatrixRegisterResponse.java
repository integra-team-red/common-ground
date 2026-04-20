package cloudflight.integra.backend.matrix.api.model;

public record MatrixRegisterResponse(String access_token, String device_id, String user_id, String home_server) {
}
