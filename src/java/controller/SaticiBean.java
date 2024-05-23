package controller;

import dao.UrunDAO;
import entity.Satici;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named(value = "saticiBean")
@SessionScoped
public class SaticiBean implements Serializable{

    private Urun urun;
    private UrunDAO dao;
    private List<Urun> urunler;
    
    public SaticiBean() {
    }
    
    public UrunDAO getDao() {
        if (dao == null) {
            dao = new UrunDAO();
        }
        return dao;
    }

    public Urun getUrun() {
        if (urun == null)
            urun = new Urun();
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }
    
    public List<Urun> getUrunler(Satici s) {
        if (urunler == null)
            urunler = getDao().getSaticiUrunler(s);
        return urunler;
    }

    public void setUrunler(List<Urun> urunler) {
        this.urunler = urunler;
    }
    
    public String urunEklePage(){
        urun = new Urun();
        return "urun-form.xhtml";
    }
    
    public String urunEkle(Satici s){
        this.getDao().urunEkle(urun, s);
        urun = new Urun();
        return "satici-urunler.xhtml";
    }
    
    public String updatePage(Urun urun){
        setUrun(urun);
        return "urun-form.xhtml";
    }
    
    public String update() {
        this.getDao().urunDuzenle(urun);
        urun = new Urun();
        return "satici-urunler.xhtml";
    }

    public void delete(Urun urun) {
        getDao().delete(urun);
    }
    
}
