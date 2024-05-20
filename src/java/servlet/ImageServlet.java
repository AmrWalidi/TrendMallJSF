package servlet;

import dao.UrunDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {  
    
    private UrunDAO dao;
    
    public UrunDAO getDAO(){
        if (this.dao == null) 
            dao = new UrunDAO();
        return dao;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        String imageID = request.getParameter("id");
        try {
            response.setContentType("image/png");
            try (OutputStream out = response.getOutputStream()) {
                out.write(getDAO().urunImage(imageID));
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
