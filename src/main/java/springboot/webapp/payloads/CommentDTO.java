package springboot.webapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.webapp.entity.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private int id;
	
	private String content;
	
//	private PostDTO post;
}
