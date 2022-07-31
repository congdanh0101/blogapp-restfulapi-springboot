package springboot.webapp.service;

import java.util.List;

import springboot.webapp.payloads.CategoryDTO;

public interface CategoryService {
	
	//create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	//update
	CategoryDTO updaCategory(CategoryDTO categoryDTO,int id);
	
	//delete
	void deleteCategory(int id);
	
	//get
	CategoryDTO getCategoryById(int id);
	
	//get all
	List<CategoryDTO> getAllCategories();
}
