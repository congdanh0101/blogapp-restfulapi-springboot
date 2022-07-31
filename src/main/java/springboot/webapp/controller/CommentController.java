package springboot.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.webapp.payloads.ApiRespone;
import springboot.webapp.payloads.CommentDTO;
import springboot.webapp.service.CommentService;

@RestController
@RequestMapping("/api/comments/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postID}")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable int postID){
		
		return new ResponseEntity<CommentDTO>(commentService.createComment(commentDTO, postID),HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiRespone> deleteComment(@PathVariable int id){
		this.commentService.deleteComment(id);
		return new ResponseEntity<ApiRespone>(new ApiRespone("Comment was deleted",true),HttpStatus.OK);
	}
}
