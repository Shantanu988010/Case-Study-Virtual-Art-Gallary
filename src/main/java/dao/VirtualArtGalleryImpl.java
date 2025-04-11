package dao;

import exception.ArtWorkNotFoundException;
import exception.DbConnectionException;
import exception.UserNotFoundException;
import util.DbConnectionUtil;
import util.HexaConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Artwork;

public class VirtualArtGalleryImpl implements VirtualArtGallery {
    private Connection connection;

    public VirtualArtGalleryImpl() throws DbConnectionException {
        connection = DbConnectionUtil.getDbConnection();
    }

    @Override
    public boolean addArtwork(Artwork artwork) {
        String sql = HexaConstants.ADD_ARTWORK_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setArtworkParameters(pstmt, artwork);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public boolean updateArtwork(Artwork artwork) throws ArtWorkNotFoundException {
        String sql = HexaConstants.UPDATE_ARTWORK_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setArtworkParameters(pstmt, artwork);
            pstmt.setInt(7, artwork.getArtworkID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ArtWorkNotFoundException("Artwork with ID " + artwork.getArtworkID() + " not found");
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public boolean removeArtwork(int artworkID) throws ArtWorkNotFoundException {
        String sql = HexaConstants.DELETE_ARTWORK_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, artworkID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkID + " not found");
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public Artwork getArtworkById(int artworkID) throws ArtWorkNotFoundException {
        String sql = HexaConstants.GET_ARTWORK_BY_ID_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, artworkID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return createArtworkFromResultSet(rs);
            } else {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkID + " not found");
            }
        } catch (SQLException e) {
            throw new ArtWorkNotFoundException("Error retrieving artwork: " + e.getMessage());
        }
    }

    @Override
    public List<Artwork> searchArtworks(String keyword) {
        List<Artwork> results = new ArrayList<>();
        String sql = HexaConstants.SEARCH_ARTWORK_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) {
                pstmt.setString(i, searchPattern);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(createArtworkFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return results;
    }

    @Override
    public boolean addArtworkToFavorite(int userId, int artworkId)
            throws UserNotFoundException, ArtWorkNotFoundException {
        validateUserAndArtwork(userId, artworkId);

        String sql = HexaConstants.ADD_FAVORITE_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, artworkId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public boolean removeArtworkFromFavorite(int userId, int artworkId)
            throws UserNotFoundException, ArtWorkNotFoundException {
        validateUserAndArtwork(userId, artworkId);

        String sql = HexaConstants.REMOVE_FAVORITE_QRY;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, artworkId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException {
        if (!isUserExists(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        List<Artwork> favorites = new ArrayList<>();
        String sql = HexaConstants.GET_USER_FAVORITES_QRY;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                favorites.add(createArtworkFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return favorites;
    }

    // ===========================
    // ===== Helper Methods ======
    // ===========================

    private Artwork createArtworkFromResultSet(ResultSet rs) throws SQLException {
        return new Artwork(
                rs.getInt(HexaConstants.COL_ARTWORK_ID),
                rs.getString(HexaConstants.COL_TITLE),
                rs.getString(HexaConstants.COL_DESCRIPTION),
                rs.getDate(HexaConstants.COL_CREATION_DATE),
                rs.getString(HexaConstants.COL_MEDIUM),
                rs.getString(HexaConstants.COL_IMAGE_URL),
                rs.getInt(HexaConstants.COL_ARTIST_ID)
        );
    }

    private void setArtworkParameters(PreparedStatement pstmt, Artwork artwork) throws SQLException {
        pstmt.setString(1, artwork.getTitle());
        pstmt.setString(2, artwork.getDescription());
        pstmt.setDate(3, new java.sql.Date(artwork.getCreationDate().getTime()));
        pstmt.setString(4, artwork.getMedium());
        pstmt.setString(5, artwork.getImageURL());
        pstmt.setInt(6, artwork.getArtistID());
    }

    private void validateUserAndArtwork(int userId, int artworkId)
            throws UserNotFoundException, ArtWorkNotFoundException {
        if (!isUserExists(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        if (!isArtworkExists(artworkId)) {
            throw new ArtWorkNotFoundException("Artwork with ID " + artworkId + " not found");
        }
    }

    private boolean isUserExists(int userId) {
        return checkEntityExists("User", HexaConstants.COL_USER_ID, userId);
    }

    private boolean isArtworkExists(int artworkId) {
        return checkEntityExists("Artwork", HexaConstants.COL_ARTWORK_ID, artworkId);
    }

    private boolean checkEntityExists(String table, String column, int id) {
        String sql = String.format(HexaConstants.CHECK_ENTITY_EXISTS_TEMPLATE, column, table, column);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    private void handleSQLException(SQLException e) {
        System.err.println("SQL Error: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
    }
}
