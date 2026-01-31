package cloudflight.integra.backend.coffeemug;

import cloudflight.integra.backend.coffeemug.model.CoffeeMug;
import cloudflight.integra.backend.coffeemug.model.CoffeeMugDto;
import org.springframework.stereotype.Component;

@Component
public class CoffeeMugMapper {
    public CoffeeMugDto toDto(CoffeeMug mug) {
        return new CoffeeMugDto(mug.getId(), mug.getColor(), mug.getCapacityMl(), mug.isClean());
    }

    public CoffeeMug toEntity(CoffeeMugDto dto) {
        return new CoffeeMug(dto.id(), dto.color(), dto.capacityMl(), dto.clean());
    }
}
