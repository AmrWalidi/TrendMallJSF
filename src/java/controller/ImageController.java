package controller;

import dao.ImageDAO;
import entity.Image;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.http.Part;
import java.io.Serializable;
import java.util.List;

@Named(value = "imageController")
@SessionScoped
public class ImageController implements Serializable {

    private Image entity;
    private ImageDAO dao;
    private Part file;
    private List<Image> list;

    public ImageController() {
    }

    public Image getEntity() {
        if (this.entity == null){
            entity = new Image();
        }
        return entity;
    }

    public void setEntity(Image entity) {

        this.entity = entity;
    }

    public ImageDAO getDao() {
        if (this.dao == null){
            dao = new ImageDAO();
        }
        return dao;
    }

    public void setDao(ImageDAO dao) {
        this.dao = dao;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    

    public List<Image> getList() {
        this.list = this.getDao().getImages(); 
        return this.list;
    }

    public void setList(List<Image> list) {
        this.list = list;
    }
    
    public void upload(){
        this.getDao().upload(this.getFile());
    }

}
