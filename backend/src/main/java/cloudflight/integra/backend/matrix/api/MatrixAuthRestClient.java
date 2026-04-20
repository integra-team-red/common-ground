package cloudflight.integra.backend.matrix.api;

import cloudflight.integra.backend.matrix.api.model.MatrixNonceResponse;
import cloudflight.integra.backend.matrix.api.model.MatrixRegisterRequest;
import cloudflight.integra.backend.matrix.api.model.MatrixRegisterResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

@Service
public class MatrixAuthRestClient {
    private final RestClient matrixRestClient;
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private final String sharedSecret;

    public MatrixAuthRestClient(
        RestClient matrixRestClient,
        @Value("${matrix.registration-shared-secret}") String sharedSecret
    ) {
        this.matrixRestClient = matrixRestClient;
        this.sharedSecret = sharedSecret;
    }

    public MatrixRegisterResponse register(String username, String password) {
        MatrixNonceResponse nonceResponse = this.matrixRestClient.get()
            .uri("/_synapse/admin/v1/register")
            .retrieve()
            .body(MatrixNonceResponse.class);
        String nonce = nonceResponse.nonce();

        String mac = this.getRegisterHmac(nonce, username, password, "notadmin");

        MatrixRegisterRequest request = new MatrixRegisterRequest(
            nonce,
            username,
            password,
            false,
            mac
        );

        return this.matrixRestClient.post()
            .uri("/_synapse/admin/v1/register")
            .body(request)
            .retrieve()
            .body(MatrixRegisterResponse.class);
    }

    private String getRegisterHmac(
        String nonce,
        String username,
        String password,
        String admin
    ) throws IllegalStateException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.sharedSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);

            mac.update(nonce.getBytes(StandardCharsets.UTF_8));
            mac.update(HexFormat.of().parseHex("00"));
            mac.update(username.getBytes(StandardCharsets.UTF_8));
            mac.update(HexFormat.of().parseHex("00"));
            mac.update(password.getBytes(StandardCharsets.UTF_8));
            mac.update(HexFormat.of().parseHex("00"));
            byte[] result = mac.doFinal(admin.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : result) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to compute HMAC for Matrix registration", e);
        }
    }
}
