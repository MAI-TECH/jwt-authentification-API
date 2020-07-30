package com.maitech;

import com.maitech.models.RoleModel;
import com.maitech.models.UserModel;
import com.maitech.repositories.RoleRepository;
import com.maitech.repositories.UserRepository;
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
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

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

		List<UserModel> userModels = userRepository.findAll();
		List<RoleModel> roleModels = roleRepository.findAll();

		if (roleModels.isEmpty()) {
			role1 = new RoleModel();
			role1.setName("UTILISATEUR");
			role1.setCode("USER");
			role1.setDescription("Utilisateur ayant des droits limites");

			role2 = new RoleModel();
			role2.setName("ADMINISTRATEUR");
			role2.setCode("ADMIN");
			role2.setDescription("Utilisateur ayant tout les droits");

			role1 = roleRepository.save(role1);
			role2 = roleRepository.save(role2);
		}
		else {
			role1 = roleRepository.findByCode("USER");
			role2 = roleRepository.findByCode("ADMIN");
		}

		if (userModels.isEmpty()) {
			user1 = new UserModel();
			user1.setLastName("NGUENGUIA");
			user1.setFirstName("MORELLE");
			user1.setGender("Femme");
			user1.setPhone(671154549);
			user1.setEmail("user@email.com");
			user1.setUsername("morelle");
			user1.setPassword("#AnanI|1597");
			user1.setRoleModel(role1);

			user2 = new UserModel();
			user2.setLastName("MBOBDA");
			user2.setFirstName("AYMAR IVAN");
			user2.setGender("Homme");
			user2.setPhone(694142288);
			user2.setEmail("aymarmbobda@gmail.com");
			user2.setUsername("aymar");
			user2.setPassword("#AnanI|1597");
			user2.setRoleModel(role2);

			user1 = userRepository.save(user1);
			user2 = userRepository.save(user2);
		}
	}
}
