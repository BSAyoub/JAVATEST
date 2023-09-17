package com.supralog.test.user;

import com.supralog.test.user.entity.Address;
import com.supralog.test.user.entity.User;
import com.supralog.test.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy // Enable Spring AOP
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository) {
		return args -> {
			userRepository.deleteAll();
			Address address = new Address();
			address.setCountry("France");
			User user = new User();
			user.setFirstName("Ayoub");
			user.setLastName("BOUSAID");
			user.setUsername("a.bousaid");
			user.setPassword("123456");
			user.setEmail("a.bousaid@supralog.fr");
			user.setAge(26);
			user.setAddress(address);

			//2nd user
			Address address_2 = new Address();
			address_2.setCountry("France");
			User user_2 = new User();
			user_2.setFirstName("Julien");
			user_2.setLastName("ATTA");
			user_2.setUsername("j.atta");
			user_2.setPassword("123456");
			user_2.setEmail("j.atta@supralog.fr");
			user_2.setAge(19);
			user_2.setAddress(address_2);

			User existingUser_1 = userRepository.findByUsername(user.getUsername());
			if (existingUser_1 == null) {
				userRepository.insert(user);
			}

			User existingUser_2 = userRepository.findByUsername(user_2.getUsername());
			if (existingUser_2 == null) {
				userRepository.insert(user_2);
			}


		};

}


}
