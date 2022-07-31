package springboot.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.webapp.entity.Category;
import springboot.webapp.entity.Post;
import springboot.webapp.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String keyword);
	
}
