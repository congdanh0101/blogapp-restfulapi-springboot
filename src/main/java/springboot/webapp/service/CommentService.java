package springboot.webapp.service;

import springboot.webapp.payloads.CommentDTO;

public interface CommentService {
	
	//create
	CommentDTO createComment(CommentDTO commentDTO, int postID);
	
	//update
	CommentDTO updateComment(CommentDTO commentDTO,int id);
	
	//delete
	void deleteComment(int id);
	
	//get
	
	
	
}
