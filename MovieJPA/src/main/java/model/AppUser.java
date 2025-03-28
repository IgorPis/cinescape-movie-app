package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * The persistent class for the app_user database table.
 * 
 */
@Entity
@Table(name="app_user")
@NamedQuery(name="AppUser.findAll", query="SELECT a FROM AppUser a")
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUser;

	private String firstName;

	private String imagePath;

	private String lastName;

	private String password;

	private String username;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="idRole")
//	@JsonManagedReference
	private Role role;

	//bi-directional many-to-one association to Comment
	//cascade = CascadeType.REMOVE, you don't need to manually delete the comments. When you delete a user, all associated comments will be automatically deleted.
	@OneToMany(mappedBy="appUser", cascade = CascadeType.REMOVE)
//	@JsonBackReference
	@JsonIgnore
	private List<Comment> comments;

	//bi-directional many-to-one association to Favorite
	@OneToMany(mappedBy="appUser", cascade = CascadeType.REMOVE)
//	@JsonBackReference
	@JsonIgnore
	private List<Favorite> favorites;

	public AppUser() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setAppUser(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setAppUser(null);

		return comment;
	}

	public List<Favorite> getFavorites() {
		return this.favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public Favorite addFavorite(Favorite favorite) {
		getFavorites().add(favorite);
		favorite.setAppUser(this);

		return favorite;
	}

	public Favorite removeFavorite(Favorite favorite) {
		getFavorites().remove(favorite);
		favorite.setAppUser(null);

		return favorite;
	}

}