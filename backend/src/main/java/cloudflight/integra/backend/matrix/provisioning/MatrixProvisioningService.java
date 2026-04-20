package cloudflight.integra.backend.matrix.provisioning;

import cloudflight.integra.backend.matrix.api.MatrixAuthRestClient;
import cloudflight.integra.backend.matrix.api.model.MatrixRegisterResponse;
import cloudflight.integra.backend.user.UserService;
import cloudflight.integra.backend.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatrixProvisioningService {
    private final MatrixAuthRestClient matrixRestClient;
    private final UserService userService;
    private final String serverName;
    private final SecureRandom passwordRandom = new SecureRandom();
    private final ConcurrentHashMap<String, String> temporaryPassword = new ConcurrentHashMap<>();

    public MatrixProvisioningService(
        MatrixAuthRestClient matrixRestClient,
        UserService userService,
        @Value("${matrix.server-name}") String serverName
    ) {
        this.matrixRestClient = matrixRestClient;
        this.userService = userService;
        this.serverName = serverName;
    }

    public String getMatrixUserId(User user) {
        if (user.getMatrixAccessToken() == null) return null;

        return "@" + user.getUsername().toLowerCase() + ":" + this.serverName;
    }

    public void setTemporaryPassword(User user, String password) {
        if (user.getMatrixAccessToken() == null) return;

        this.temporaryPassword.put(user.getUsername(), password);
    }

    public String getTemporaryPassword(User user) {
        return this.temporaryPassword.get(user.getUsername());
    }

    public MatrixProvisioningResponse provisionAccount(User user) {
        String temporaryPassword = this.getTemporaryMatrixPassword();

        try {
            MatrixRegisterResponse response = matrixRestClient.register(user.getUsername(), temporaryPassword);

            userService.setMatrixAccount(user.getUsername(), response.access_token());
            this.setTemporaryPassword(user, temporaryPassword);

            return MatrixProvisioningResponse.ok();
        } catch (IllegalStateException e) {
            return MatrixProvisioningResponse.warn("Matrix provisioning is not configured correctly");

        } catch (ResourceAccessException e) {
            return MatrixProvisioningResponse.warn("Matrix homeserver is unreachable");

        } catch (RestClientResponseException e) {
            HttpStatusCode errorCode = e.getStatusCode();
            if (errorCode.value() == 409) {
                return MatrixProvisioningResponse.warn("Matrix account already exists for this username");
            }

            return MatrixProvisioningResponse.warn("Matrix homeserver rejected request");
        }
    }

    public String getTemporaryMatrixPassword() {
        int passwordLength = 10;
        String characters = "abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ23456789!@#$%^&*_+-=";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int index = this.passwordRandom.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
