package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TagRepository {
    private final Map<Long, Tag> tags = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Tag save(Tag tag) {
        if (tag.getId() == null) {
            tag.setId(this.idGen.getAndIncrement());
        }

        this.tags.put(tag.getId(), tag);
        return tag;
    }

    public boolean deleteById(Long id) {
        if (!this.tags.containsKey(id)) { return false; }

        this.tags.remove(id);
        return true;
    }

    public List<Tag> findAll() {
        return new ArrayList<>(this.tags.values());
    }

    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(this.tags.get(id));
    }
}
