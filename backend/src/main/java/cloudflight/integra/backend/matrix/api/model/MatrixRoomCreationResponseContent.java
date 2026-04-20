package cloudflight.integra.backend.matrix.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MatrixRoomCreationResponseContent(
    @JsonProperty("room_id") String roomId) {
}
