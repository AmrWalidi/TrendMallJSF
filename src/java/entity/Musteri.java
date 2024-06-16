package entity;

import jakarta.persistence.Entity;

@Entity
public class Musteri extends Kullanici {

    private String sifre;

    @Override
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

}
