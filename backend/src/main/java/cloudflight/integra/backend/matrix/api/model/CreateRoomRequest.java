package cloudflight.integra.backend.matrix.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateRoomRequest(
    @JsonProperty("creation_content") RoomCreationContent roomCreationContent,
    String name,
    String preset
) {
}
