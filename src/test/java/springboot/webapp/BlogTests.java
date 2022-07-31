package springboot.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import springboot.webapp.repository.UserRepository;

@SpringBootTest
class BlogTests {

	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	void contextLoads() {
	}

	
	@Test
	public void repoTest() {
		String className = this.userRepository.getClass().getName();
		String packageName = this.userRepository.getClass().getPackage().getName();
		System.out.println(className);
		System.out.println(packageName);
	}
}
