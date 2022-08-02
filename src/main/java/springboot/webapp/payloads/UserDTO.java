package springboot.webapp.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private int id;

	@NotNull
	@NotBlank
	@Size(min = 4, message = "Username must be min of 4 characters")
	private String name;

	@Email(message = "Email address is not valid")
	private String email;

	@NotNull
	@NotBlank
	@Size(min = 3, message = "Password must be min of 3 characters")
	private String password;

	private String about;

	private Set<RoleDTO> roles = new HashSet<>();
}