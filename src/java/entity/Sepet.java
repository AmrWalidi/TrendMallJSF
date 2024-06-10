package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class Sepet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Musteri musteri;
    @Column(name = "toplam_ucret")
    private double toplamUcret;
    @OneToMany(mappedBy = "sepet")
    private List<SepetUrun> urunler;

    public Sepet(int id, Musteri musteri, double toplamUcret, List<SepetUrun> urunler) {
        this.id = id;
        this.musteri = musteri;
        this.toplamUcret = toplamUcret;
        this.urunler = urunler;
    }

    public Sepet(Musteri musteri) {
        this.musteri = musteri;
    }

    public Sepet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public double getToplamUcret() {
        return toplamUcret;
    }

    public void setToplamUcret(double toplamUcret) {
        this.toplamUcret = toplamUcret;
    }

    public List<SepetUrun> getUrunler() {
        return urunler;
    }

    public void setUrunler(List<SepetUrun> urunler) {
        this.urunler = urunler;
    }
    
    

}
