package se.lth.base.server.data;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author Isabella Gagner
 * Test of class User
 */

public class UserTest {

	private User user = new User(1, "email", Role.USER, "name");
	private User admin = new User(1, "email", Role.ADMIN, "name");
	private User none = new User(1, "email", Role.NONE, "name");
	
	
	@Test
	public void getUserRoles() {
		assertEquals(user.getRole(), Role.USER);
		assertEquals(admin.getRole(), Role.ADMIN);
		assertEquals(none.getRole(), Role.NONE);
	}
	
	@Test
	public void getNameTest() {
		assertEquals(user.getName(), "name");
	}
	
	@Test
	public void getIdTest() {
		assertEquals(user.getId(), 1);
	}
	
	@Test
	public void getEmail() {
		assertEquals(user.getEmail(), "email");
	}
}
