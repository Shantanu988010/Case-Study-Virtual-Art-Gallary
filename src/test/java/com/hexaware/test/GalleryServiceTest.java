package com.hexaware.test;

import dao.GalleryDAO;
import dao.GalleryDAOImpl;
import entity.Gallery;
import exception.DbConnectionException;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GalleryServiceTest {

    private static GalleryDAO galleryDAO;
    private static int generatedGalleryId;

    @BeforeAll
    public static void init() throws DbConnectionException {
        galleryDAO = new GalleryDAOImpl();
    }

    @Test
    @Order(1)
    public void testAddGallery_success() {
        Gallery gallery = new Gallery(0, "JUnit Test Gallery", "JUnit Description", "Mumbai",
                1, "9am-6pm");

        boolean result = galleryDAO.addGallery(gallery);
        assertTrue(result, "Gallery should be added successfully");

        // Fetch back to confirm and get generated ID
        List<Gallery> results = galleryDAO.searchGalleries("JUnit");
        assertFalse(results.isEmpty(), "Gallery should be found");
        generatedGalleryId = results.get(0).getGalleryID();
    }

    @Test
    @Order(2)
    public void testUpdateGallery_success() {
        Gallery updated = new Gallery(generatedGalleryId, "Updated Gallery Name", "Updated Desc",
                "Pune", 1, "10am-5pm");

        boolean result = galleryDAO.updateGallery(updated);
        assertTrue(result, "Gallery should be updated successfully");

        Gallery fetched = galleryDAO.getGalleryById(generatedGalleryId);
        assertEquals("Updated Gallery Name", fetched.getName());
    }

    @Test
    @Order(3)
    public void testSearchGallery_found() {
        List<Gallery> results = galleryDAO.searchGalleries("Updated");
        assertFalse(results.isEmpty(), "Search should return at least one result");
    }

    @Test
    @Order(4)
    public void testRemoveGallery_success() {
        boolean result = galleryDAO.removeGallery(generatedGalleryId);
        assertTrue(result, "Gallery should be removed successfully");
    }

    @Test
    @Order(5)
    public void testGetGalleryById_notFound() {
        Gallery gallery = galleryDAO.getGalleryById(generatedGalleryId);
        assertNull(gallery, "After removal, gallery should not be found");
    }
}
