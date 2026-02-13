package cloudflight.integra.backend.coffeemug;

import cloudflight.integra.backend.coffeemug.model.CoffeeMug;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeMugService {
    private final CoffeeMugRepository repository;

    public CoffeeMugService(CoffeeMugRepository repository) {
        this.repository = repository;
    }

    public List<CoffeeMug> getAll() {
        return repository.findAll();
    }

    public Optional<CoffeeMug> getById(Long id) {
        return repository.findById(id);
    }

    public CoffeeMug create(CoffeeMug mug) {
        return repository.save(mug);
    }

    public Optional<CoffeeMug> update(Long id, CoffeeMug mug) {
        if (repository.findById(id).isPresent()) {
            mug.setId(id);
            return Optional.of(repository.save(mug));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
