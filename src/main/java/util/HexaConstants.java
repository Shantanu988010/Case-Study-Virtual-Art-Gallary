package util;

public class HexaConstants {

    // === Messages ===
    public static final String ARTWORK_NOT_FOUND = "Artwork not found";
    public static final String USER_NOT_FOUND = "User not found for given Id";
    public static final String CANNOT_OPEN_CONNECTION = "Connection cannot be opened";

    // === File and Property Keys ===
    public static final String DB_FILE_NAME = "hexadb.properties";
    public static final String DB_DRIVER = "driver";
    public static final String DB_URL = "dburl";

    // === SQL Queries ===

    public static final String ADD_ARTWORK_QRY =
            "INSERT INTO Artwork (Title, Description, CreationDate, Medium, ImageURL, ArtistID) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_ARTWORK_QRY =
            "UPDATE Artwork SET Title = ?, Description = ?, CreationDate = ?, Medium = ?, ImageURL = ?, ArtistID = ? WHERE ArtworkID = ?";

    public static final String DELETE_ARTWORK_QRY =
            "DELETE FROM Artwork WHERE ArtworkID = ?";

    public static final String GET_ARTWORK_BY_ID_QRY =
            "SELECT * FROM Artwork WHERE ArtworkID = ?";

    public static final String SEARCH_ARTWORK_QRY =
            "SELECT * FROM Artwork WHERE Title LIKE ? OR Description LIKE ? OR Medium LIKE ?";

    public static final String ADD_FAVORITE_QRY =
            "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";

    public static final String REMOVE_FAVORITE_QRY =
            "DELETE FROM User_Favorite_Artwork WHERE UserID = ? AND ArtworkID = ?";

    public static final String GET_USER_FAVORITES_QRY =
            "SELECT a.* FROM Artwork a " +
                    "JOIN User_Favorite_Artwork ufa ON a.ArtworkID = ufa.ArtworkID " +
                    "WHERE ufa.UserID = ?";

    public static final String CHECK_ENTITY_EXISTS_TEMPLATE =
            "SELECT %s FROM %s WHERE %s = ?"; // For dynamic entity existence checks

    // === Column Constants (optional use in ResultSet parsing) ===
    public static final String COL_ARTWORK_ID = "ArtworkID";
    public static final String COL_TITLE = "Title";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_CREATION_DATE = "CreationDate";
    public static final String COL_MEDIUM = "Medium";
    public static final String COL_IMAGE_URL = "ImageURL";
    public static final String COL_ARTIST_ID = "ArtistID";
    public static final String COL_USER_ID = "UserID";

    // === UI Prompts (optional for client-side CLI apps) ===
    public static final String PROMPT_ARTWORK_ID = "Artwork ID: ";
    public static final String PROMPT_ARTIST_ID = "Artist ID: ";
    public static final String PROMPT_USER_ID = "User ID: ";
    public static final String PROMPT_TITLE = "Title: ";
    public static final String PROMPT_DESCRIPTION = "Description: ";
    public static final String PROMPT_MEDIUM = "Medium: ";
    public static final String PROMPT_IMAGE_URL = "Image URL: ";
    public static final String PROMPT_CREATION_DATE = "Creation Date (yyyy-mm-dd): ";



    //-------------------------------------------------

    // === Prompt Constants for CLI ===

    public static final String PROMPT_NEW_TITLE = "New Title (leave blank to keep current): ";
    public static final String PROMPT_NEW_DESCRIPTION = "New Description (leave blank to keep current): ";
    public static final String PROMPT_NEW_CREATION_DATE = "New Creation Date";
    public static final String PROMPT_NEW_MEDIUM = "New Medium (leave blank to keep current): ";
    public static final String PROMPT_NEW_IMAGE_URL = "New Image URL (leave blank to keep current): ";
    public static final String PROMPT_NEW_ARTIST_ID = "New Artist ID";

    // === Menu/Header Texts ===
    public static final String HEADER_MAIN_MENU = "\n=== Virtual Art Gallery Management System ===";
    public static final String HEADER_ADD_ARTWORK = "\n=== Add New Artwork ===";
    public static final String HEADER_UPDATE_ARTWORK = "\n=== Update Artwork ===";
    public static final String HEADER_REMOVE_ARTWORK = "\n=== Remove Artwork ===";
    public static final String HEADER_SEARCH_BY_ID = "\n=== Search Artwork by ID ===";
    public static final String HEADER_SEARCH_BY_KEYWORD = "\n=== Search Artworks ===";
    public static final String HEADER_ADD_TO_FAV = "\n=== Add to Favorites ===";
    public static final String HEADER_REMOVE_FROM_FAV = "\n=== Remove from Favorites ===";
    public static final String HEADER_VIEW_FAV = "\n=== View Favorites ===";


    // Prompts & Inputs
    public static final String PROMPT_CHOICE = "Enter your choice: ";
    public static final String PROMPT_SEARCH_KEYWORD = "Enter search keyword: ";
    public static final String UPDATE_ARTWORK_ID = "Enter Artwork ID to update: ";
    public static final String CURRENT_DETAILS = "Current details:";

    public static final String INVALID_NUMBER_INPUT = "Invalid input! Please enter a valid number.";
    public static final String INVALID_DATE_INPUT = "Invalid date format! Please try again.";
    public static final String INVALID_DATE_KEEP_CURRENT = "Invalid date format. Keeping current value.";
    public static final String INVALID_NUMBER_KEEP_CURRENT = "Invalid number. Keeping current value.";
    public static final String INVALID_CHOICE_MSG = "Invalid choice! Please enter 1-9";
    public static final String EXIT_MSG = "Exiting system...";
    public static final String USER_ERROR_PREFIX = "Error: ";
    public static final String UNEXPECTED_ERROR_PREFIX = "Unexpected error: ";



    // Feedback Messages
    public static final String ADD_SUCCESS = "Artwork added successfully!";
    public static final String ADD_FAILURE = "Failed to add artwork.";
    public static final String UPDATE_SUCCESS = "Artwork updated successfully!";
    public static final String UPDATE_FAILURE = "Failed to update artwork.";
    public static final String REMOVE_SUCCESS = "Artwork removed successfully!";
    public static final String REMOVE_FAILURE = "Failed to remove artwork.";
    public static final String NO_RESULTS_FOUND = "No artworks found matching your search.";
    public static final String FAV_ADD_SUCCESS = "Artwork added to favorites!";
    public static final String FAV_ADD_FAILURE = "Failed to add to favorites.";
    public static final String FAV_REMOVE_SUCCESS = "Artwork removed from favorites!";
    public static final String FAV_REMOVE_FAILURE = "Failed to remove from favorites.";
    public static final String NO_FAVORITES = "No favorite artworks found.";
    public static final String FAVORITE_LIST_HEADER = "Favorite artworks:";



    // Menu Items
    public static final String MENU_OPTION_1 = "1. Add Artwork";
    public static final String MENU_OPTION_2 = "2. Update Artwork";
    public static final String MENU_OPTION_3 = "3. Remove Artwork";
    public static final String MENU_OPTION_4 = "4. Search Artwork by ID";
    public static final String MENU_OPTION_5 = "5. Search Artworks by Keyword";
    public static final String MENU_OPTION_6 = "6. Add Artwork to Favorites";
    public static final String MENU_OPTION_7 = "7. Remove Artwork from Favorites";
    public static final String MENU_OPTION_8 = "8. View User Favorites";
    public static final String MENU_OPTION_9 = "9. Exit";


}
