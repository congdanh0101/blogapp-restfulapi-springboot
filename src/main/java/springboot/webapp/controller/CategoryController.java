package springboot.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.webapp.payloads.ApiRespone;
import springboot.webapp.payloads.CategoryDTO;
import springboot.webapp.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	
	//POST - create
	@PostMapping("")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		return new ResponseEntity<CategoryDTO>(categoryService.createCategory(categoryDTO),HttpStatus.CREATED);
	}
	
	//PUT - update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable int id){
		return new ResponseEntity<CategoryDTO>(categoryService.updaCategory(categoryDTO, id),HttpStatus.OK);
	}
	
	//DELETE - delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiRespone>(new ApiRespone("Category was deleted", true),HttpStatus.OK);
	}
	
	//GET - get single
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id){
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(id),HttpStatus.OK);
	}
	
	//GET - get all
	@GetMapping("")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
}
