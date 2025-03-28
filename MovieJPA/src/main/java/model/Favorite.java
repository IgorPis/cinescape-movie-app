package model;

import java.io.Serializable;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;


/**
 * The persistent class for the favorite database table.
 * 
 */
@Entity
@NamedQuery(name="Favorite.findAll", query="SELECT f FROM Favorite f")
public class Favorite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idFavorite;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name="idUser")
	private AppUser appUser;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="idMovie")
	private Movie movie;

	public Favorite() {
	}

	public int getIdFavorite() {
		return this.idFavorite;
	}

	public void setIdFavorite(int idFavorite) {
		this.idFavorite = idFavorite;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}