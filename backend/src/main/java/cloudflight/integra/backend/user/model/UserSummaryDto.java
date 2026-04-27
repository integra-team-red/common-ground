package cloudflight.integra.backend.user.model;

import java.util.UUID;

public record UserSummaryDto(
    UUID id,
    String username
) {}
