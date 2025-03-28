package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRole;

	private String role;

	//bi-directional many-to-one association to AppUser
	@OneToMany(mappedBy="role")
	@JsonBackReference
	private List<AppUser> appUsers;

	public Role() {
	}

	public int getIdRole() {
		return this.idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public AppUser addAppUser(AppUser appUser) {
		getAppUsers().add(appUser);
		appUser.setRole(this);

		return appUser;
	}

	public AppUser removeAppUser(AppUser appUser) {
		getAppUsers().remove(appUser);
		appUser.setRole(null);

		return appUser;
	}

}