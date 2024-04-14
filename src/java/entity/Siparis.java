package entity;

import java.util.Date;
import java.util.List;

public class Siparis {

    private int id;
    private Musteri musteri;
    private Satici satici;
    private List<Urun> urunler;
    private Date talepTarihi;

    public Siparis() {
    }

    public Siparis(int id, Musteri musteri, Satici satici, List<Urun> urunler, Date talepTarihi) {
        this.id = id;
        this.musteri = musteri;
        this.satici = satici;
        this.urunler = urunler;
        this.talepTarihi = talepTarihi;
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

    public Satici getSatici() {
        return satici;
    }

    public void setSatici(Satici satici) {
        this.satici = satici;
    }

    public List<Urun> getUrunler() {
        return urunler;
    }

    public void setUrunler(List<Urun> urunler) {
        this.urunler = urunler;
    }

    public Date getTalepTarihi() {
        return talepTarihi;
    }

    public void setTalepTarihi(Date talepTarihi) {
        this.talepTarihi = talepTarihi;
    }

}
