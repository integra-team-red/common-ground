package cloudflight.integra.backend.matrix.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomCreationContent(
    @JsonProperty("m.federate") Boolean federate
) {
}
