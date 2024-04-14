/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Image;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class ImageDAO {

    private List<Image> imageList;

    public List<Image> getImages() {
        imageList = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JSFexampleDB", "postgres", "AmrF.C.B");
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM images";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                imageList.add(new Image(rs.getInt("id"), rs.getString("name")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
        return imageList;
    }

    public void upload(Part file) {
        if (file != null) {
            try {
                InputStream input = file.getInputStream();
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JSFexampleDB", "postgres", "AmrF.C.B");
                PreparedStatement statement = conn.prepareStatement("INSERT INTO images (name, content) VALUES (?, ?)");
                statement.setString(1, file.getSubmittedFileName());
                statement.setBytes(2, input.readAllBytes());
                statement.executeUpdate();
            } catch (ClassNotFoundException | IOException | SQLException e) {
                e.getMessage();
            }
        }
    }
}
