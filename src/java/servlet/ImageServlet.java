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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        byte[] imageData = null;
        try {
            String imageID = request.getParameter("id");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JSFexampleDB", "postgres", "AmrF.C.B");
            Statement st = conn.createStatement();
            String sql = "SELECT content FROM images WHERE id = " + Integer.valueOf(imageID);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                imageData = rs.getBytes("content");
            }
            response.setContentType("image/png");
            try (OutputStream out = response.getOutputStream()) {
                out.write(imageData);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }
}
