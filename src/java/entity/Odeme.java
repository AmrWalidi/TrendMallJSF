package entity;

import java.time.LocalDate;

public class Odeme {

    private int id;
    private Musteri musteri;
    private double tutar;
    private LocalDate tarih;

    public Odeme(int id, Musteri musteri, double tutar, LocalDate tarih) {
        this.id = id;
        this.musteri = musteri;
        this.tutar = tutar;
        this.tarih = tarih;
    }

    public Odeme(Musteri musteri) {
        this.musteri = musteri;
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

    public double getTutar() {
        return tutar;
    }

    public void setTutar(double tutar) {
        this.tutar = tutar;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

}
