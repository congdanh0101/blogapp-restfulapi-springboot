package springboot.webapp.service.implement;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import springboot.webapp.entity.Category;
import springboot.webapp.entity.Post;
import springboot.webapp.entity.User;
import springboot.webapp.exception.ResourceNotFoundException;
import springboot.webapp.payloads.PostDTO;
import springboot.webapp.payloads.PostRespone;
import springboot.webapp.repository.CategoryRepository;
import springboot.webapp.repository.PostRepository;
import springboot.webapp.repository.UserRepository;
import springboot.webapp.service.PostService;
import springboot.webapp.utils.ModelMapping;

@Service
public class PostServiceImp implements PostService, ModelMapping<Post, PostDTO> {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public PostDTO createPost(PostDTO postDTO, int userID, int categoryID) {

		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
		Category category = categoryRepository.findById(categoryID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryID));

		Post post = dtoToEntity(postDTO);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post savedPost = this.postRepository.save(post);

		return entityToDTO(savedPost);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, int id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());

		this.postRepository.save(post);
		return entityToDTO(post);
	}

	@Override
	public void deletePost(int id) {

		Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		this.postRepository.delete(post);
	}

	@Override
	public PostRespone getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = postRepository.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		List<PostDTO> postDTOs = allPosts.stream().map(post -> entityToDTO(post)).collect(Collectors.toList());

		PostRespone postRespone = new PostRespone();
		postRespone.setContent(postDTOs);
		postRespone.setPageNumber(pagePost.getNumber());
		postRespone.setPageSize(pagePost.getSize());
		postRespone.setTotalElements(pagePost.getTotalElements());
		postRespone.setTotalPages(pagePost.getTotalPages());
		postRespone.setLastPage(pagePost.isLast());

		return postRespone;
	}

	@Override
	public PostDTO getPostById(int id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return entityToDTO(post);
	}

	@Override
	public List<PostDTO> getAllPostsByCategory(int categroyID) {

		Category category = categoryRepository.findById(categroyID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categroyID));

		List<Post> posts = postRepository.findByCategory(category);
		List<PostDTO> postDTOs = posts.stream().map(cate -> entityToDTO(cate)).collect(Collectors.toList());

		return postDTOs;

	}

	@Override
	public List<PostDTO> getAllPostsByUser(int userID) {

		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

		List<Post> posts = postRepository.findByUser(user);
		List<PostDTO> postDTOs = posts.stream().map(u -> entityToDTO(u)).collect(Collectors.toList());

		return postDTOs;

	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {

		List<Post> posts = postRepository.findByTitleContaining(keyword);

		return posts.stream().map(p -> entityToDTO(p)).collect(Collectors.toList());
	}

	@Override
	public Post dtoToEntity(PostDTO dto) {
		return mapper.map(dto, Post.class);
	}

	@Override
	public PostDTO entityToDTO(Post entity) {
		return mapper.map(entity, PostDTO.class);
	}

}
