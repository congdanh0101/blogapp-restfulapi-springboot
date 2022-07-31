package springboot.webapp.service.implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.webapp.entity.Comment;
import springboot.webapp.entity.Post;
import springboot.webapp.exception.ResourceNotFoundException;
import springboot.webapp.payloads.CommentDTO;
import springboot.webapp.repository.CommentRepository;
import springboot.webapp.repository.PostRepository;
import springboot.webapp.service.CommentService;
import springboot.webapp.utils.ModelMapping;

@Service
public class CommentServiceImp implements CommentService, ModelMapping<Comment, CommentDTO> {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, int postID) {

		Post post = postRepository.findById(postID)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));

		Comment comment = this.mapper.map(commentDTO, Comment.class);
		comment.setPost(post);

		Comment savedComment = this.commentRepository.save(comment);

		return this.mapper.map(savedComment, CommentDTO.class);

	}

	@Override
	public CommentDTO updateComment(CommentDTO commentDTO, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComment(int id) {
		Comment comment = this.commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		this.commentRepository.delete(comment);
	}

	@Override
	public Comment dtoToEntity(CommentDTO dto) {
		return mapper.map(dto, Comment.class);
	}

	@Override
	public CommentDTO entityToDTO(Comment entity) {
		return mapper.map(entity, CommentDTO.class);
	}

}
