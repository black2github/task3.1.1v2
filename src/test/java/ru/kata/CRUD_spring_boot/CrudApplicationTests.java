package ru.kata.CRUD_spring_boot;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kata.CRUD_spring_boot.model.Role;
import ru.kata.CRUD_spring_boot.model.RoleRepository;
import ru.kata.CRUD_spring_boot.model.User;
import ru.kata.CRUD_spring_boot.model.UserRepository;
import ru.kata.CRUD_spring_boot.service.UserService;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
class CrudApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(CrudApplicationTests.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	// @Test
	// void contextLoads() {
	// }

	@Test
	void createUser() {
		log.debug("createUser: <-");

		User user = new User("user", "user", 20);
		user.getRoles().add(new Role("ROLE_USER"));
		user = userService.create(user);
		assertNotNull("User must not be null", user);

		User user2 = userService.find(user.getId());
		assertEquals("Users not equals", user, user2);

		userService.delete(user);
	}

	@Test
	void deleteUser() {
		User user = new User("user", "user", 20);
		user.getRoles().add(new Role("ROLE_USER"));
		user = userService.create(user);
		user = userService.find(user.getId());
		assertNotNull("deleteUser: Just created user not found", user);

		userService.delete(user);
		user = userService.find(user.getId());
		assertNull("delete User: Found user after delete", user);
	}

	@Test
	void updateUser() {
		User user = new User("name1", "second1", 10);
		user.getRoles().add(new Role("ROLE_USER"));
		user = userService.create(user);

		User user2 = new User("name2", "second2", 20);
		user2.getRoles().add(new Role("ROLE_ADMIN"));
		log.debug("user2="+user2);

		user = userService.update(user.getId(), user2);
		log.debug("user after update = "+user);

		User user3 = userService.find(user.getId());
		log.debug("user3 = "+user3);
		log.debug("user3==user ? " + user3.equals(user));
		assertEquals("User not updated properly.", user3, user);
	}
}
