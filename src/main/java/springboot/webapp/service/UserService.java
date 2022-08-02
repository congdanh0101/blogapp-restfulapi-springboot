package springboot.webapp.service;

import java.util.List;

import springboot.webapp.payloads.UserDTO;

public interface UserService {

	
	UserDTO registerUser(UserDTO user);
	UserDTO createUser(UserDTO user);
	UserDTO updateUser(UserDTO user, Integer userId);
	UserDTO getUserById(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
	
}
