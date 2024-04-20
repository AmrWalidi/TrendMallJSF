package entity;

import java.time.LocalDate;

public class Odeme {

    private int id;
    private Siparis siparis;
    private LocalDate tarih;

    public Odeme() {
    }

    public Odeme(int id, Siparis siparis, LocalDate tarih) {
        this.id = id;
        this.siparis = siparis;
        this.tarih = tarih;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis) {
        this.siparis = siparis;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

}
