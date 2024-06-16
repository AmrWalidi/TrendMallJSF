package entity;

import converter.ByteArrayPart;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Entity
public class Urun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ad;
    @ManyToOne(targetEntity = Satici.class)
    private Satici satici;
    @ManyToMany
    @JoinTable(name = "urun_kategori", joinColumns = @JoinColumn(name = "urun_id"), inverseJoinColumns
            = @JoinColumn(name = "kategori_id"))
    private List<Kategori> kategoriler;
    private int miktar;
    private double fiyat;
    private byte[] image;
    @OneToMany(mappedBy = "urun")
    private List<SepetUrun> sepetler;

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

        Part file = new ByteArrayPart(this.image, ad, "png");
        return file;
    }

    public void setImage(Part image) {
        this.image = bytesConverter(image);
    }

    public byte[] bytesConverter(Part file) {
        byte[] imageBytes = null;
        try {
            InputStream input = file.getInputStream();
            imageBytes = input.readAllBytes();
        } catch (IOException e) {
            e.getMessage();
        }
        return imageBytes;
    }

    public List<SepetUrun> getSepetler() {
        return sepetler;
    }

    public void setSepetler(List<SepetUrun> sepetler) {
        this.sepetler = sepetler;
    }

}
