package cloudflight.integra.backend.coffeemug;

import cloudflight.integra.backend.coffeemug.model.CoffeeMug;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CoffeeMugRepository {
    private final Map<Long, CoffeeMug> mugs = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<CoffeeMug> findAll() {
        return new ArrayList<>(mugs.values());
    }

    public Optional<CoffeeMug> findById(Long id) {
        return Optional.ofNullable(mugs.get(id));
    }

    public CoffeeMug save(CoffeeMug mug) {
        if (mug.getId() == null) {
            mug.setId(idGen.getAndIncrement());
        }
        mugs.put(mug.getId(), mug);
        return mug;
    }

    public void deleteById(Long id) {
        mugs.remove(id);
    }
}
