package dao;

import exception.DbConnectionException;
import util.DbConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Gallery;

public class GalleryDAOImpl implements GalleryDAO {
    private Connection conn;

    public GalleryDAOImpl() throws DbConnectionException {
        conn = DbConnectionUtil.getDbConnection();
    }

    @Override
    public boolean addGallery(Gallery g) {
        String sql = "INSERT INTO Gallery (Name, Description, Location, CuratorID, OpeningHours) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, g.getName());
            pstmt.setString(2, g.getDescription());
            pstmt.setString(3, g.getLocation());
            pstmt.setInt(4, g.getCurator());
            pstmt.setString(5, g.getOpeningHours());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); return false;
        }
    }

    @Override
    public boolean updateGallery(Gallery g) {
        String sql = "UPDATE Gallery SET Name = ?, Description = ?, Location = ?, CuratorID = ?, OpeningHours = ? WHERE GalleryID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, g.getName());
            pstmt.setString(2, g.getDescription());
            pstmt.setString(3, g.getLocation());
            pstmt.setInt(4, g.getCurator());
            pstmt.setString(5, g.getOpeningHours());
            pstmt.setInt(6, g.getGalleryID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); return false;
        }
    }

    @Override
    public boolean removeGallery(int galleryId) {
        String sql = "DELETE FROM Gallery WHERE GalleryID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, galleryId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); return false;
        }
    }

    @Override
    public List<Gallery> searchGalleries(String keyword) {
        List<Gallery> list = new ArrayList<>();
        String sql = "SELECT * FROM Gallery WHERE Name LIKE ? OR Description LIKE ? OR Location LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) pstmt.setString(i, pattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Gallery(
                        rs.getInt("GalleryID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("CuratorID"),
                        rs.getString("OpeningHours")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Gallery getGalleryById(int galleryId) {
        String sql = "SELECT * FROM Gallery WHERE GalleryID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, galleryId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Gallery(
                        rs.getInt("GalleryID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("CuratorID"),
                        rs.getString("OpeningHours")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
