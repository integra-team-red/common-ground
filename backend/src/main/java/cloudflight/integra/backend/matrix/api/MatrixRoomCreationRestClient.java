package cloudflight.integra.backend.matrix.api;

import cloudflight.integra.backend.matrix.api.model.CreateRoomRequest;
import cloudflight.integra.backend.matrix.api.model.RoomCreationContent;
import cloudflight.integra.backend.matrix.api.model.MatrixRoomCreationResponseContent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class MatrixRoomCreationRestClient {

    private final RestClient restClient;

    public MatrixRoomCreationRestClient(@Qualifier("matrixAdminRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String createRoom(String roomName) {
        CreateRoomRequest newRoomRequest =
            new CreateRoomRequest(new RoomCreationContent(false), roomName, "private_chat");
        return Objects.requireNonNull(restClient.post()
            .uri("/_matrix/client/v3/createRoom")
            .body(newRoomRequest)
            .retrieve()
            .body(MatrixRoomCreationResponseContent.class)).roomId();
    }
}
