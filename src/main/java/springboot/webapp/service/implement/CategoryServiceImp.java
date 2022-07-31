package springboot.webapp.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.webapp.entity.Category;
import springboot.webapp.exception.ResourceNotFoundException;
import springboot.webapp.payloads.CategoryDTO;
import springboot.webapp.repository.CategoryRepository;
import springboot.webapp.service.CategoryService;
import springboot.webapp.utils.ModelMapping;


@Service
public class CategoryServiceImp implements CategoryService,ModelMapping<Category, CategoryDTO> {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = dtoToEntity(categoryDTO);
		Category savedCategory = this.categoryRepository.save(category);
		return entityToDTO(savedCategory);
	}

	@Override
	public CategoryDTO updaCategory(CategoryDTO categoryDTO, int id) {
		Category category = this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		category.setTitle(categoryDTO.getTitle());
		category.setDescription(categoryDTO.getDescription());
		Category updatedCategory = this.categoryRepository.save(category);
		return entityToDTO(updatedCategory);
	}

	@Override
	public void deleteCategory(int id) {
		Category category = this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		this.categoryRepository.delete(category);
	}

	@Override
	public CategoryDTO getCategoryById(int id) {
		Category category = this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		
		return entityToDTO(category);

	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		
		List<Category> categories = this.categoryRepository.findAll();
		
		List<CategoryDTO> categoryDTOs = categories.stream().map(cate-> entityToDTO(cate)).collect(Collectors.toList());
		
		return categoryDTOs;
	}


	@Override
	public Category dtoToEntity(CategoryDTO dto) {
		return this.modelMapper.map(dto, Category.class);
	}

	@Override
	public CategoryDTO entityToDTO(Category entity) {
		return this.modelMapper.map(entity, CategoryDTO.class);
	}
}
