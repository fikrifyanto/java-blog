package tsajf.tailwindblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsajf.tailwindblog.entity.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


}
