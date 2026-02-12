package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.tag.model.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDto toDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getLabel(), tag.getNormalizedLabel());
    }

    public Tag toEntity(TagDto dto) {
        return new Tag(dto.id(), dto.label(), dto.normalizedLabel());
    }
}
