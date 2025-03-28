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
 * The persistent class for the language database table.
 * 
 */
@Entity
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLanguage;

	private String name;

	//bi-directional many-to-one association to Movie
	@OneToMany(mappedBy="language")
	@JsonBackReference
	private List<Movie> movies;

	public Language() {
	}

	public int getIdLanguage() {
		return this.idLanguage;
	}

	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public Movie addMovy(Movie movy) {
		getMovies().add(movy);
		movy.setLanguage(this);

		return movy;
	}

	public Movie removeMovy(Movie movy) {
		getMovies().remove(movy);
		movy.setLanguage(null);

		return movy;
	}

}