package com.example.guessmaster;

public class Singer extends Person {
	private String debutAlbum;
	private Date debutAlbumReleaseDate;

	public Singer(String name, Date born, String gender, String debutAlbum,
			Date debutAlbumReleaseDate, Double difficulty) {
		super(name, born, gender, difficulty);
		this.debutAlbum = debutAlbum;
		this.debutAlbumReleaseDate = debutAlbumReleaseDate;
	}

	// copy constructor
	public Singer(Singer otherSinger) {
		super(otherSinger);
		this.debutAlbum = otherSinger.getDebutAlbum();
		this.debutAlbumReleaseDate = otherSinger.getDebutAlbumReleaseDate();
	}

	public String getDebutAlbum() {
		return debutAlbum;
	}

	public void setDebutAlbum(String debutAlbum) {
		this.debutAlbum = debutAlbum;
	}

	public Date getDebutAlbumReleaseDate() {
		return debutAlbumReleaseDate;
	}

	public void setDebutAlbumReleaseDate(Date debutAlbumReleaseDate) {
		this.debutAlbumReleaseDate = debutAlbumReleaseDate;
	}
	
	
	@Override
	public String entityType() {
		return "singer";
	}

	@Override
	public Singer clone() {
		return new Singer(this);
	}

	@Override
	public String toString() {
		return String.format("%sDebut Album: %s\nRelease Date: %s\n", super.toString(), debutAlbum, debutAlbumReleaseDate);
	}
}
