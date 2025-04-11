package dao;

import exception.ArtWorkNotFoundException;
import exception.UserNotFoundException;

import java.util.List;

import entity.Artwork;

public interface VirtualArtGallery {
    boolean addArtwork(Artwork artwork);
    boolean updateArtwork(Artwork artwork) throws ArtWorkNotFoundException;
    boolean removeArtwork(int artworkID) throws ArtWorkNotFoundException;
    Artwork getArtworkById(int artworkID) throws ArtWorkNotFoundException;
    List<Artwork> searchArtworks(String keyword);
    
    boolean addArtworkToFavorite(int userId, int artworkId) 
        throws UserNotFoundException, ArtWorkNotFoundException;
    boolean removeArtworkFromFavorite(int userId, int artworkId) 
        throws UserNotFoundException, ArtWorkNotFoundException;
    List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException;
}