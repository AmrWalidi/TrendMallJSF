package entity;

import java.util.List;


public class Sepet {
    private int id;
    private Musteri musteri;
    private List urunler;
    private double toplamUcret;

    public Sepet(int id, Musteri musteri, List<Urun> urunler, double toplamUcret) {
        this.id = id;
        this.musteri = musteri;
        this.urunler = urunler;
        this.toplamUcret = toplamUcret;
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

    public List<Urun> getUrunler() {
        return urunler;
    }

    public void setUrunler(List<Urun> urunler) {
        this.urunler = urunler;
    }

    public double getToplamUcret() {
        return toplamUcret;
    }

    public void setToplamUcret(double toplamUcret) {
        this.toplamUcret = toplamUcret;
    }

    
}
