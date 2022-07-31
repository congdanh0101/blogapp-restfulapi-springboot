package springboot.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springboot.webapp.config.AppConstants;
import springboot.webapp.payloads.ApiRespone;
import springboot.webapp.payloads.PostDTO;
import springboot.webapp.payloads.PostRespone;
import springboot.webapp.service.FileService;
import springboot.webapp.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// POST - create
	@PostMapping("/user/{userID}/category/{categoryID}")
//	@RequestMapping(value = "/user/{userID}/category/{categoryID}/post",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
	public ResponseEntity<PostDTO> creatPost(@RequestBody PostDTO postDTO, @PathVariable("userID") int userID,
			@PathVariable("categoryID") int categoryID) {

		return new ResponseEntity<PostDTO>(postService.createPost(postDTO, userID, categoryID), HttpStatus.CREATED);
	}

	// PUT - update
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable int id) {
		return ResponseEntity.ok(postService.updatePost(postDTO, id));
	}

	// DELETE - delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable int id) {
		this.postService.deletePost(id);
		return new ResponseEntity<ApiRespone>(new ApiRespone("Delete", true), HttpStatus.OK);
	}

	// GET - get all posts by user
	@GetMapping("/user/{userID}")
	public ResponseEntity<List<PostDTO>> getAllPostsByUser(@PathVariable int userID) {
		return ResponseEntity.ok(postService.getAllPostsByUser(userID));
	}

	// GET - get all posts by category
	@GetMapping("/category/{categoryID}")
	public ResponseEntity<List<PostDTO>> getAllPostsByCategory(@PathVariable int categoryID) {
		return ResponseEntity.ok(postService.getAllPostsByCategory(categoryID));
	}

	// GET - get all posts
	@GetMapping("")
	public ResponseEntity<PostRespone> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostRespone postRespone = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostRespone>(postRespone, HttpStatus.OK);
	}

	// GET - get single post
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
		return new ResponseEntity<PostDTO>(postService.getPostById(id), HttpStatus.OK);
	}

	// search
	@GetMapping("/search")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword) {
		return new ResponseEntity<List<PostDTO>>(postService.searchPosts(keyword), HttpStatus.OK);
	}

	// POST - upload image
	@PostMapping("/image/upload/{id}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable int id)
			throws IOException {
		PostDTO postDTO = this.postService.getPostById(id);
		String fileName = this.fileService.uploadImage(path, image);
		postDTO.setImageName(fileName);
		PostDTO updatedPostDTO = this.postService.updatePost(postDTO, id);
		return new ResponseEntity<PostDTO>(updatedPostDTO, HttpStatus.OK);
	}

	// GET - download file
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
