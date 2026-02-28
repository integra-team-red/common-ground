package cloudflight.integra.backend.coffeemug;

import cloudflight.integra.backend.coffeemug.model.CoffeeMug;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoffeeMugService {
    private final CoffeeMugRepository repository;

    public CoffeeMugService(CoffeeMugRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CoffeeMug> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public CoffeeMug getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public CoffeeMug create(CoffeeMug mug) {
        mug.setId(null);
        return repository.save(mug);
    }

    @Transactional
    public CoffeeMug update(Long id, CoffeeMug mug) {
        return repository.findById(id)
            .map(existingMug -> {
                existingMug.setColor(mug.getColor());
                existingMug.setClean(mug.isClean());
                existingMug.setCapacityMl(mug.getCapacityMl());

                return repository.save(existingMug);
            })
            .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        CoffeeMug mug = repository.findById(id)
            .orElseThrow();
        repository.deleteById(mug.getId());
        return true;
    }
}
