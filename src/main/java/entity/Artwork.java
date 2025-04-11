package entity;

import java.util.Date;

public class Artwork {
	private int artworkID;
    private String title;
    private String description;
    private Date creationDate;
    private String medium;
    private String imageURL;
    private int artistID;
	public Artwork() {
		super();

	}
	public Artwork(int artworkID, String title, String description, Date creationDate, String medium, String imageURL,
			int artistID) {
		super();
		this.artworkID = artworkID;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.medium = medium;
		this.imageURL = imageURL;
		this.artistID = artistID;
	}
	public int getArtworkID() {
		return artworkID;
	}
	public void setArtworkID(int artworkID) {
		this.artworkID = artworkID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public int getArtistID() {
		return artistID;
	}
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
    
    
    
    


}
