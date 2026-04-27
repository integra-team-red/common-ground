package cloudflight.integra.backend.tag.model;

import jakarta.validation.constraints.NotBlank;

public record TagDto(
    Long id,
    @NotBlank(message = "The tag label is required. It cannot be blank.")
    String label,
    String normalizedLabel
) {
}
