package uzkor.aziz.adminIntra.AdminPort;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uzkor.aziz.adminIntra.AdminPort.domain.User;
import uzkor.aziz.adminIntra.AdminPort.domain.security.Role;
import uzkor.aziz.adminIntra.AdminPort.domain.security.UserRole;
import uzkor.aziz.adminIntra.AdminPort.service.UserService;
import uzkor.aziz.adminIntra.AdminPort.utility.SecurityUtility;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AdminPortApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(AdminPortApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		User user1 = new User();
		user1.setUsername("admin");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		user1.setEmail("admin@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(0);
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1,role1));

		userService.createUser(user1,userRoles);

	}
}
