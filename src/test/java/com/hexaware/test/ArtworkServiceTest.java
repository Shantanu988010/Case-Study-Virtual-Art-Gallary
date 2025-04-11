package com.hexaware.test;

import dao.VirtualArtGallery;
import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import exception.ArtWorkNotFoundException;
import exception.DbConnectionException;

import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArtworkServiceTest {

    private static VirtualArtGallery galleryService;
    private static int generatedArtworkId; // to store actual DB ID

    @BeforeAll
    public static void init() throws DbConnectionException {
        galleryService = new VirtualArtGalleryImpl();
    }

    @Test
    @Order(1)
    public void testAddArtwork_success() {
        Artwork artwork = new Artwork(0, "JUnit Test Title", "JUnit Test Description", new Date(),
                "Oil", "junit_test.jpg", 1);
        boolean result = galleryService.addArtwork(artwork);
        assertTrue(result, "Artwork should be added successfully");


        List<Artwork> found = galleryService.searchArtworks("JUnit Test Title");
        assertFalse(found.isEmpty(), "Inserted artwork should be found in search");
        generatedArtworkId = found.get(0).getArtworkID();
    }

    @Test
    @Order(2)
    public void testUpdateArtwork_success() throws ArtWorkNotFoundException {
        Artwork updated = new Artwork(generatedArtworkId, "Updated Title", "Updated Description", new Date(),
                "Acrylic", "updated_url.jpg", 1);
        boolean result = galleryService.updateArtwork(updated);
        assertTrue(result, "Artwork should be updated successfully");

        Artwork fetched = galleryService.getArtworkById(generatedArtworkId);
        assertEquals("Updated Title", fetched.getTitle());
    }

    @Test
    @Order(3)
    public void testGetArtworkById_success() throws ArtWorkNotFoundException {
        Artwork artwork = galleryService.getArtworkById(generatedArtworkId);
        assertNotNull(artwork);
        assertEquals(generatedArtworkId, artwork.getArtworkID());
    }

    @Test
    @Order(4)
    public void testSearchArtworks_found() {
        List<Artwork> results = galleryService.searchArtworks("Updated");
        assertFalse(results.isEmpty(), "Search should return results for keyword 'Updated'");
    }

    @Test
    @Order(5)
    public void testRemoveArtwork_success() throws ArtWorkNotFoundException {
        boolean result = galleryService.removeArtwork(generatedArtworkId);
        assertTrue(result, "Artwork should be removed successfully");
    }

    @Test
    @Order(6)
    public void testGetArtworkById_notFound() {
        assertThrows(ArtWorkNotFoundException.class, () -> {
            galleryService.getArtworkById(generatedArtworkId);
        });
    }
}
