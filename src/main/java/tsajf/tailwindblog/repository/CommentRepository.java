package tsajf.tailwindblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsajf.tailwindblog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


}
