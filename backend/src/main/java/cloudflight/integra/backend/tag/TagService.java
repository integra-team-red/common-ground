package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TagService {
    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<Tag> getAll(Pageable pageable) { return this.repository.findAll(pageable); }

    @Transactional(readOnly = true)
    public Tag getById(Long id) { return this.repository.findById(id).orElseThrow(); }

    @Transactional(readOnly = true)
    public Page<Tag> getByNormalizedLabel(String normalizedLabel, Pageable pageable) {
        return this.repository.findByNormalizedLabel(normalizedLabel, pageable);
    }

    private String generateNormalizedLabel(String label) {
        return label.trim().toLowerCase();
    }

    @Transactional
    public Tag create(Tag tag) {
        tag.setNormalizedLabel(this.generateNormalizedLabel(tag.getLabel()));
        return this.repository.save(tag);
    }

    @Transactional
    public Tag update(Long id, Tag newTag) {
        Tag oldTag = repository.findById(id).orElseThrow();

        oldTag.setLabel(newTag.getLabel());
        oldTag.setNormalizedLabel(newTag.getNormalizedLabel());
        return oldTag;
    }

    @Transactional
    public boolean delete(Long id) {
        Tag tag = repository.findById(id).orElseThrow();
        this.repository.deleteById(tag.getId());
        return true;
    }
}
