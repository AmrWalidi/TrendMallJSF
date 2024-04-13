package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String imageId = request.getParameter("id"); // Assuming the image ID is passed as a parameter
        byte[] imageData = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JSFexampleDB", "postgres", "AmrF.C.B");
            Statement statement = conn.createStatement();
            String sql = "SELECT content FROM images WHERE id = " + Integer.valueOf(imageId);
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                imageData = rs.getBytes("content"); // Specify the correct MIME type for your image
            }
            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            out.write(imageData);
        } catch (IOException | ClassNotFoundException | NumberFormatException | SQLException e) {
            e.getMessage();
        }
    }
}
