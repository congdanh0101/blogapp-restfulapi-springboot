package springboot.webapp.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import springboot.webapp.config.AppConstants;
import springboot.webapp.entity.Role;
import springboot.webapp.entity.User;
import springboot.webapp.exception.ResourceNotFoundException;
import springboot.webapp.payloads.UserDTO;
import springboot.webapp.repository.RoleRepository;
import springboot.webapp.repository.UserRepository;
import springboot.webapp.service.UserService;
import springboot.webapp.utils.ModelMapping;

@Service
public class UserServiceImp implements UserService, ModelMapping<User, UserDTO> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		User user = dtoToEntity(userDTO);
		User savedUser = this.userRepository.save(user);
		return this.entityToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		user.setName(userDTO.getName());
		user.setAbout(userDTO.getAbout());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());

		User updatedUser = this.userRepository.save(user);

		return entityToDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		return this.entityToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> users = this.userRepository.findAll();

		List<UserDTO> userDTOs = users.stream().map(user -> this.entityToDTO(user)).collect(Collectors.toList());

		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepository.delete(user);

	}

	@Override
	public User dtoToEntity(UserDTO dto) {
		return this.modelMapper.map(dto, User.class);
	}

	@Override
	public UserDTO entityToDTO(User entity) {
		return this.modelMapper.map(entity, UserDTO.class);
	}

	@Override
	public UserDTO registerUser(UserDTO user) {

		User u = dtoToEntity(user);
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role =  this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		u.getRoles().add(role);
		User savedUser = this.userRepository.save(u);
		
		return entityToDTO(savedUser);
		
		
	}
}
