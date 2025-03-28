package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * The persistent class for the comment database table.
 * 
 */
@Entity
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idComment;

	private String text;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name="idUser")
//	@JsonManagedReference NE TREBA KOD MANY TO MANY
	private AppUser appUser;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="idMovie")
//	@JsonManagedReference NE TREBA KOD MANY TO MANY
	private Movie movie;

	public Comment() {
	}

	public int getIdComment() {
		return this.idComment;
	}

	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(timestamp);
    }

}