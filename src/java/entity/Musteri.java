package entity;

import jakarta.persistence.Entity;

@Entity
public class Musteri extends Kullanici{
    private String sifre;

    public Musteri(int id, String ad, String soyad, String eposta, String sifre, String telNo, String adres) {
        super(id, ad, soyad, eposta, sifre, telNo, adres);
    }

    public Musteri() {
    }

    @Override
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
    
    
}
