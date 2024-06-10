package entity;

import jakarta.persistence.Entity;

@Entity
public class Satici extends Kullanici{
    private String sifre;

    public Satici(int id, String ad, String soyad, String eposta, String sifre, String telNo, String adres) {
        super(id, ad, soyad, eposta, sifre, telNo, adres);
    }

    public Satici() {
    }
      
    @Override
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
    
}
