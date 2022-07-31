package springboot.webapp.service;

import java.util.List;

import springboot.webapp.entity.Post;
import springboot.webapp.payloads.PostDTO;
import springboot.webapp.payloads.PostRespone;

public interface PostService {

	//create
	PostDTO createPost(PostDTO postDTO,int userID,int categoryID);
	
	//update
	PostDTO updatePost(PostDTO postDTO,int id);
	
	//delete
	void deletePost(int id);
	
	//get all posts
	PostRespone getAllPosts(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//get single post
	PostDTO getPostById(int id);
	
	//get all posts by category
	List<PostDTO> getAllPostsByCategory(int categroyID);
	
	//get all posts by user
	List<PostDTO> getAllPostsByUser(int userID);
	
	//search post
	List<PostDTO> searchPosts(String keyword);
	
	
}
