package dao;

import java.util.List;

import entity.Gallery;

public interface GalleryDAO {
    boolean addGallery(Gallery gallery);
    boolean updateGallery(Gallery gallery);
    boolean removeGallery(int galleryId);
    List<Gallery> searchGalleries(String keyword);
    Gallery getGalleryById(int galleryId); // returns null if not found
}
