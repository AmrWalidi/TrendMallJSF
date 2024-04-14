package entity;

import java.util.List;


public class Sepet {
    private Musteri musteri;
    private List<Urun> urunList;
    private double toplamUcret;

    public Sepet(Musteri musteri, List<Urun> urunList, double toplamUcret) {
        this.musteri = musteri;
        this.urunList = urunList;
        this.toplamUcret = toplamUcret;
    }

    public Sepet(Musteri musteri) {
        this.musteri = musteri;
    }

    public Sepet() {
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public List<Urun> getUrunList() {
        return urunList;
    }

    public void setUrunList(List<Urun> urunList) {
        this.urunList = urunList;
    }

    public double getToplamUcret() {
        return toplamUcret;
    }

    public void setToplamUcret(double toplamUcret) {
        this.toplamUcret = toplamUcret;
    }
}
