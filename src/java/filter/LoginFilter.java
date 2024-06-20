package filter;

import entity.Musteri;
import entity.Satici;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String url = req.getRequestURI();
        
        HttpSession session = req.getSession();
        
        Musteri m = null;
        Satici s = null;
        
        if (session != null) {
            m = (Musteri) session.getAttribute("validMusteri");
            s = (Satici) session.getAttribute("validSatici");
        }
        
        if (m == null && s == null) {
            if (url.contains("logout")) {
                res.sendRedirect(req.getContextPath() + "/giris-form.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (url.contains("giris-form")) {
                res.sendRedirect(req.getContextPath() + "/index.xhtml");
            } else if (url.contains("logout")) {
                session.invalidate();
                res.sendRedirect(req.getContextPath() + "/giris-form.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
