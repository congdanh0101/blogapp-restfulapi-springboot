package springboot.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.webapp.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
}
