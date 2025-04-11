package main;

import dao.VirtualArtGallery;
import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import exception.ArtWorkNotFoundException;
import exception.DbConnectionException;
import exception.UserNotFoundException;
import util.HexaConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static final VirtualArtGallery galleryService;

    static {
        try {
            galleryService = new VirtualArtGalleryImpl();
        } catch (DbConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                int choice = getIntInput(HexaConstants.PROMPT_CHOICE);
                switch (choice) {
                    case 1 -> addArtwork();
                    case 2 -> updateArtwork();
                    case 3 -> removeArtwork();
                    case 4 -> searchArtworkById();
                    case 5 -> searchArtworksByKeyword();
                    case 6 -> addToFavorites();
                    case 7 -> removeFromFavorites();
                    case 8 -> viewFavorites();
                    case 9 -> {
                        System.out.println(HexaConstants.EXIT_MSG);
                        System.exit(0);
                    }
                    default -> System.out.println(HexaConstants.INVALID_CHOICE_MSG);
                }
            } catch (ArtWorkNotFoundException | UserNotFoundException e) {
                System.out.println(HexaConstants.USER_ERROR_PREFIX + e.getMessage());
            } catch (Exception e) {
                System.out.println(HexaConstants.UNEXPECTED_ERROR_PREFIX + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println(HexaConstants.HEADER_MAIN_MENU);
        System.out.println(HexaConstants.MENU_OPTION_1);
        System.out.println(HexaConstants.MENU_OPTION_2);
        System.out.println(HexaConstants.MENU_OPTION_3);
        System.out.println(HexaConstants.MENU_OPTION_4);
        System.out.println(HexaConstants.MENU_OPTION_5);
        System.out.println(HexaConstants.MENU_OPTION_6);
        System.out.println(HexaConstants.MENU_OPTION_7);
        System.out.println(HexaConstants.MENU_OPTION_8);
        System.out.println(HexaConstants.MENU_OPTION_9);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(HexaConstants.INVALID_NUMBER_INPUT);
            }
        }
    }

    private static Date getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (yyyy-mm-dd): ");
                return dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println(HexaConstants.INVALID_DATE_INPUT);
            }
        }
    }

    private static void addArtwork() {
        System.out.println(HexaConstants.HEADER_ADD_ARTWORK);
        int id = getIntInput(HexaConstants.PROMPT_ARTWORK_ID);
        String title = getStringInput(HexaConstants.PROMPT_TITLE);
        String description = getStringInput(HexaConstants.PROMPT_DESCRIPTION);
        Date creationDate = getDateInput(HexaConstants.PROMPT_CREATION_DATE);
        String medium = getStringInput(HexaConstants.PROMPT_MEDIUM);
        String imageURL = getStringInput(HexaConstants.PROMPT_IMAGE_URL);
        int artistId = getIntInput(HexaConstants.PROMPT_ARTIST_ID);

        Artwork artwork = new Artwork(id, title, description, creationDate, medium, imageURL, artistId);
        if (galleryService.addArtwork(artwork)) {
            System.out.println(HexaConstants.ADD_SUCCESS);
        } else {
            System.out.println(HexaConstants.ADD_FAILURE);
        }
    }

    private static void updateArtwork() throws ArtWorkNotFoundException {
        System.out.println(HexaConstants.HEADER_UPDATE_ARTWORK);
        int id = getIntInput(HexaConstants.UPDATE_ARTWORK_ID);
        Artwork existing = galleryService.getArtworkById(id);
        System.out.println(HexaConstants.CURRENT_DETAILS);
        displayArtwork(existing);

        String title = getStringInput(HexaConstants.PROMPT_NEW_TITLE);
        String description = getStringInput(HexaConstants.PROMPT_NEW_DESCRIPTION);
        Date creationDate = getOptionalDate(HexaConstants.PROMPT_NEW_CREATION_DATE);
        String medium = getStringInput(HexaConstants.PROMPT_NEW_MEDIUM);
        String imageURL = getStringInput(HexaConstants.PROMPT_NEW_IMAGE_URL);
        Integer artistId = getOptionalInt(HexaConstants.PROMPT_NEW_ARTIST_ID);

        Artwork updated = new Artwork(
                id,
                title.isEmpty() ? existing.getTitle() : title,
                description.isEmpty() ? existing.getDescription() : description,
                creationDate != null ? creationDate : existing.getCreationDate(),
                medium.isEmpty() ? existing.getMedium() : medium,
                imageURL.isEmpty() ? existing.getImageURL() : imageURL,
                artistId != null ? artistId : existing.getArtistID()
        );

        if (galleryService.updateArtwork(updated)) {
            System.out.println(HexaConstants.UPDATE_SUCCESS);
        } else {
            System.out.println(HexaConstants.UPDATE_FAILURE);
        }
    }

    private static void removeArtwork() throws ArtWorkNotFoundException {
        System.out.println(HexaConstants.HEADER_REMOVE_ARTWORK);
        int artworkId = getIntInput(HexaConstants.PROMPT_ARTWORK_ID);
        if (galleryService.removeArtwork(artworkId)) {
            System.out.println(HexaConstants.REMOVE_SUCCESS);
        } else {
            System.out.println(HexaConstants.REMOVE_FAILURE);
        }
    }

    private static void searchArtworkById() throws ArtWorkNotFoundException {
        System.out.println(HexaConstants.HEADER_SEARCH_BY_ID);
        int id = getIntInput(HexaConstants.PROMPT_ARTWORK_ID);
        Artwork artwork = galleryService.getArtworkById(id);
        displayArtwork(artwork);
    }

    private static void searchArtworksByKeyword() {
        System.out.println(HexaConstants.HEADER_SEARCH_BY_KEYWORD);
        String keyword = getStringInput(HexaConstants.PROMPT_SEARCH_KEYWORD);
        List<Artwork> results = galleryService.searchArtworks(keyword);
        if (results.isEmpty()) {
            System.out.println(HexaConstants.NO_RESULTS_FOUND);
        } else {
            System.out.println("Found " + results.size() + " artworks:");
            results.forEach(MainModule::displayArtwork);
        }
    }

    private static void addToFavorites() throws UserNotFoundException, ArtWorkNotFoundException {
        System.out.println(HexaConstants.HEADER_ADD_TO_FAV);
        int userId = getIntInput(HexaConstants.PROMPT_USER_ID);
        int artworkId = getIntInput(HexaConstants.PROMPT_ARTWORK_ID);
        if (galleryService.addArtworkToFavorite(userId, artworkId)) {
            System.out.println(HexaConstants.FAV_ADD_SUCCESS);
        } else {
            System.out.println(HexaConstants.FAV_ADD_FAILURE);
        }
    }

    private static void removeFromFavorites() throws UserNotFoundException, ArtWorkNotFoundException {
        System.out.println(HexaConstants.HEADER_REMOVE_FROM_FAV);
        int userId = getIntInput(HexaConstants.PROMPT_USER_ID);
        int artworkId = getIntInput(HexaConstants.PROMPT_ARTWORK_ID);
        if (galleryService.removeArtworkFromFavorite(userId, artworkId)) {
            System.out.println(HexaConstants.FAV_REMOVE_SUCCESS);
        } else {
            System.out.println(HexaConstants.FAV_REMOVE_FAILURE);
        }
    }

    private static void viewFavorites() throws UserNotFoundException {
        System.out.println(HexaConstants.HEADER_VIEW_FAV);
        int userId = getIntInput(HexaConstants.PROMPT_USER_ID);
        List<Artwork> favorites = galleryService.getUserFavoriteArtworks(userId);
        if (favorites.isEmpty()) {
            System.out.println(HexaConstants.NO_FAVORITES);
        } else {
            System.out.println(HexaConstants.FAVORITE_LIST_HEADER);
            favorites.forEach(MainModule::displayArtwork);
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static Date getOptionalDate(String prompt) {
        System.out.print(prompt + " (press enter to skip): ");
        String input = scanner.nextLine();
        if (input.isEmpty()) return null;
        try {
            return dateFormat.parse(input);
        } catch (ParseException e) {
            System.out.println(HexaConstants.INVALID_DATE_KEEP_CURRENT);
            return null;
        }
    }

    private static Integer getOptionalInt(String prompt) {
        System.out.print(prompt + " (press enter to skip): ");
        String input = scanner.nextLine();
        if (input.isEmpty()) return null;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(HexaConstants.INVALID_NUMBER_KEEP_CURRENT);
            return null;
        }
    }

    private static void displayArtwork(Artwork artwork) {
        System.out.println("\n=== Artwork Details ===");
        System.out.println("ID: " + artwork.getArtworkID());
        System.out.println("Title: " + artwork.getTitle());
        System.out.println("Description: " + artwork.getDescription());
        System.out.println("Creation Date: " + dateFormat.format(artwork.getCreationDate()));
        System.out.println("Medium: " + artwork.getMedium());
        System.out.println("Image URL: " + artwork.getImageURL());
        System.out.println("Artist ID: " + artwork.getArtistID());
    }
}
