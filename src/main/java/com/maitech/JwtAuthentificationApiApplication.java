package com.maitech;

import com.maitech.models.RoleModel;
import com.maitech.models.UserModel;
import com.maitech.services.RoleService;
import com.maitech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class JwtAuthentificationApiApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthentificationApiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		UserModel user1, user2;
		RoleModel role1, role2;

		List<UserModel> userModels = userService.findAllUsers();
		List<RoleModel> roleModels = roleService.findAllRoles();

		if (roleModels.isEmpty()) {
			role1 = new RoleModel();
			role1.setName("UTILISATEUR");
			role1.setCode("USER");
			role1.setDescription("Utilisateur ayant des droits limites");

			role2 = new RoleModel();
			role2.setName("ADMINISTRATEUR");
			role2.setCode("ADMIN");
			role2.setDescription("Utilisateur ayant tout les droits");

			role1 = roleService.saveRole(role1);
			role2 = roleService.saveRole(role2);
		}
		else {
			role1 = roleService.findRoleByCode("USER");
			role2 = roleService.findRoleByCode("ADMIN");
		}

		if (userModels.isEmpty()) {
			user1 = new UserModel();
			user1.setLastName("user");
			user1.setFirstName("user");
			user1.setGender("Femme");
			user1.setPhone(812457888);
			user1.setEmail("user@email.com");
			user1.setUsername("user");
			user1.setPassword("#YoomeeCM|2020");
			user1.setRoleModel(role1);

			user2 = new UserModel();
			user2.setLastName("admin");
			user2.setFirstName("admin");
			user2.setGender("Homme");
			user2.setPhone(812457887);
			user2.setEmail("admin@email.com");
			user2.setUsername("admin");
			user2.setPassword("#YoomeeCM|2020");
			user2.setRoleModel(role2);

			user1 = userService.saveUser(user1);
			user2 = userService.saveUser(user2);
		}
	}
}
