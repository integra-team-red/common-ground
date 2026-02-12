package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getAll() { return this.repository.findAll(); }

    public Optional<Tag> getById(Long id) { return this.repository.findById(id); }

    public List<Tag> getByNormalizedLabel(String normalizedLabel) {
        return this.repository.findAll().stream()
            .filter(tag -> Objects.equals(tag.getNormalizedLabel(), normalizedLabel)).toList();
    }

    private String generateNormalizedLabel(String label) {
        return label.trim().toLowerCase();
    }

    public Tag create(Tag tag) {
        tag.setNormalizedLabel(this.generateNormalizedLabel(tag.getLabel()));
        return this.repository.save(tag);
    }

    public Optional<Tag> update(Long id, Tag newTag) {
        if (this.repository.findById(id).isPresent()) {
            newTag.setId(id);
            newTag.setNormalizedLabel(this.generateNormalizedLabel(newTag.getLabel()));

            return Optional.of(this.repository.save(newTag));
        }

        return Optional.empty();
    }

    public boolean delete(Long id) {
        return this.repository.deleteById(id);
    }
}
