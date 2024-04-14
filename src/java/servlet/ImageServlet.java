package servlet;

import dao.ImageDAO;
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
        ImageDAO dao = new ImageDAO();
        String imageID = request.getParameter("id");
        try {
            response.setContentType("image/png");
            try (OutputStream out = response.getOutputStream()) {
                out.write(dao.getImageData(imageID));
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    
}
