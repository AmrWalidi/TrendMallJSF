package entity;

import jakarta.persistence.Entity;

@Entity
public class Satici extends Kullanici {

    private String sifre;

    @Override
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

}
