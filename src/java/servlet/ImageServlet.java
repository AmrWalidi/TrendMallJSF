package servlet;

import controller.UrunBean;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {  
    
    @Inject
    private UrunBean urunBean;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        String imageID = request.getParameter("id");
        try {
            response.setContentType("image/png");
            try (OutputStream out = response.getOutputStream()) {
                out.write(urunBean.getUrunDAO().urunImage(imageID));
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
