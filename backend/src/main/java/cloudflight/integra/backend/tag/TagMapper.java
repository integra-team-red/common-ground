package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.tag.model.TagDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto toDto(Tag tag);

    Tag toEntity(TagDto dto);
}
