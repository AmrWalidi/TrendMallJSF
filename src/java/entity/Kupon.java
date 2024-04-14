/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Kupon {

    private String kod;
    private int indirimMiktari;
    private Date geceriTarihi;
    private int kullanimLimiti;

    public Kupon() {
    }

    public Kupon(String kod, int indirimMiktari, Date geceriTarihi, int kullanimLimiti) {
        this.kod = kod;
        this.indirimMiktari = indirimMiktari;
        this.geceriTarihi = geceriTarihi;
        this.kullanimLimiti = kullanimLimiti;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public int getIndirimMiktari() {
        return indirimMiktari;
    }

    public void setIndirimMiktari(int indirimMiktari) {
        this.indirimMiktari = indirimMiktari;
    }

    public Date getGeceriTarihi() {
        return geceriTarihi;
    }

    public void setGeceriTarihi(Date geceriTarihi) {
        this.geceriTarihi = geceriTarihi;
    }

    public int getKullanimLimiti() {
        return kullanimLimiti;
    }

    public void setKullanimLimiti(int kullanimLimiti) {
        this.kullanimLimiti = kullanimLimiti;
    }

}
