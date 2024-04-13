/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author HP
 */
@Named(value = "managedBean")
@SessionScoped
public class ManagedBean implements Serializable {

    private Part file;
    private List<String> imageIds;

    public ManagedBean() {
        
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            try {
                InputStream input = file.getInputStream();
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JSFexampleDB", "postgres", "AmrF.C.B");
                PreparedStatement statement = conn.prepareStatement("INSERT INTO images (name, content) VALUES (?, ?)");
                statement.setString(1, file.getSubmittedFileName());
                statement.setBytes(2, input.readAllBytes());;
                statement.executeUpdate();
            } catch (ClassNotFoundException | IOException | SQLException e) {
                // Handle exceptions
                e.printStackTrace();
            }
        }
    }

    public List<String> getImageIds() {
        imageIds = new ArrayList<>();
        imageIds.add("1");
        imageIds.add("2");
        imageIds.add("3");
        imageIds.add("4");
        imageIds.add("5");
        return imageIds;
    }
}
