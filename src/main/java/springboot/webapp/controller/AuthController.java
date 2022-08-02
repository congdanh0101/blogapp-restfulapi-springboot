package springboot.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.webapp.exception.ApiException;
import springboot.webapp.payloads.JwtAuthRequest;
import springboot.webapp.payloads.JwtAuthRespone;
import springboot.webapp.payloads.UserDTO;
import springboot.webapp.security.JwtTokenHelper;
import springboot.webapp.service.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthRespone> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthRespone respone = new JwtAuthRespone();
		respone.setToken(token);
		return new ResponseEntity<JwtAuthRespone>(respone, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {

			System.out.println("Invalid details");
			throw new ApiException("invalid username or password");
		}

	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO){
		return new ResponseEntity<UserDTO>(userService.registerUser(userDTO),HttpStatus.CREATED);
		
	}

}
