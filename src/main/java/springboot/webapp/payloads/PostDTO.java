package springboot.webapp.payloads;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	private int id;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;

	private CategoryDTO category;

	private UserDTO user;

	private Collection<CommentDTO> comments = new HashSet<>();
}
