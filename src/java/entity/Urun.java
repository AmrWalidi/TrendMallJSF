package entity;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class Urun {
    private int id;
    private String ad;
    private Satici satici;
    private List<Kategori> kategoriler;
    private int miktar;
    private double fiyat;
    private Part image;
    
    public Urun() {
    }

    public Urun(int id, String ad, Satici satici, List<Kategori> kategoriler, int miktar, double fiyat) {
        this.id = id;
        this.ad = ad;
        this.satici = satici;
        this.kategoriler = kategoriler;
        this.miktar = miktar;
        this.fiyat = fiyat;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Satici getSatici() {
        return satici;
    }

    public void setSatici(Satici satici) {
        this.satici = satici;
    }

    public List<Kategori> getKategoriler() {
        return kategoriler;
    }

    public void setKategoriler(List<Kategori> kategoriler) {
        this.kategoriler = kategoriler;
    }
    
    
    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }
    
    public byte[] bytesConverter(Part file){
        byte[] imageBytes = null;
        try{ 
        InputStream input = file.getInputStream();
        imageBytes = input.readAllBytes();
        }catch(IOException e){
            e.getMessage();
        }
        return imageBytes;
    }
    
}
