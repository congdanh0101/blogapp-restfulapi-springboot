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
import springboot.webapp.payloads.UserDTO;
import springboot.webapp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST - create user
	@PostMapping("")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(this.userService.createUser(userDTO), HttpStatus.CREATED);
	}

	// PUT - update user
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("id") int id) {
		// return new ResponseEntity<UserDTO>(userService.updateUser(userDTO,
		// id),HttpStatus.OK);
		return ResponseEntity.ok(this.userService.updateUser(userDTO, id));
	}

	// DELETE - delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
		this.userService.deleteUser(id);
		return new ResponseEntity<ApiRespone>(new ApiRespone("User was deleted", true), HttpStatus.OK);
	}

	// GET - get user
	@GetMapping()
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
		return ResponseEntity.ok(this.userService.getUserById(id));
	}
}
