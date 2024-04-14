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
    private int indirim_miktari;
    private Date geceri_tarihi;
    private int kullanim_limiti;

    public Kupon() {
    }
  
    public Kupon(String kod, int indirim_miktari, Date geceri_tarihi, int kullanim_limiti) {
        this.kod = kod;
        this.indirim_miktari = indirim_miktari;
        this.geceri_tarihi = geceri_tarihi;
        this.kullanim_limiti = kullanim_limiti;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public int getIndirim_miktari() {
        return indirim_miktari;
    }

    public void setIndirim_miktari(int indirim_miktari) {
        this.indirim_miktari = indirim_miktari;
    }

    public Date getGeceri_tarihi() {
        return geceri_tarihi;
    }

    public void setGeceri_tarihi(Date geceri_tarihi) {
        this.geceri_tarihi = geceri_tarihi;
    }

    public int getKullanim_limiti() {
        return kullanim_limiti;
    }

    public void setKullanim_limiti(int kullanim_limiti) {
        this.kullanim_limiti = kullanim_limiti;
    }
    
    
    
    
    
}
