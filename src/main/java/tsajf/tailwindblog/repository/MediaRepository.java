package tsajf.tailwindblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsajf.tailwindblog.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
}
