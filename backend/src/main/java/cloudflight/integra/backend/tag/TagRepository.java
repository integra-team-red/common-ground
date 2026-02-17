package cloudflight.integra.backend.tag;

import cloudflight.integra.backend.tag.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByNormalizedLabel(String normalizedLabel, Pageable pageable);
}
