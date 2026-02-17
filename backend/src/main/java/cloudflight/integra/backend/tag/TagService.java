package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public Page<Tag> getAll(Pageable pageable) { return this.repository.findAll(pageable); }

    public Optional<Tag> getById(Long id) { return this.repository.findById(id); }

    public Page<Tag> getByNormalizedLabel(String normalizedLabel, Pageable pageable) {
        return this.repository.findByNormalizedLabel(normalizedLabel, pageable);
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

    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
