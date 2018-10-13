package se.lth.base.server.data;

public class RidePerson {
	private int rideId;
	private int userId;
	private String role;
	
	public RidePerson(int rideId, int userId, String role) {
		this.userId = userId;
		this.rideId = rideId;
		this.role = role;
	}
	
	public int getRideId() {
		return rideId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String newRole) {
		role = newRole;
	}
	
}
