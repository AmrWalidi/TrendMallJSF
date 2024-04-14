/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class Siparis {
    
    private int id;
    private Musteri musteri_id;
    private Satici satici_id;
    private List<Urun> urun_id;
    private Date talep_tarihi;

    public Siparis() {
    }
    
    public Siparis(int id, Urun urun_id, Musteri musteri_id, Satici satici_id, Date talep_tarihi) {
        this.id = id;
        this.urun_id = (List<Urun>) urun_id;
        this.musteri_id = musteri_id;
        this.satici_id = satici_id;
        this.talep_tarihi = talep_tarihi;
    }

    public List<Urun> getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(List<Urun> urun_id) {
        this.urun_id = urun_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Musteri getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(Musteri musteri_id) {
        this.musteri_id = musteri_id;
    }

    public Satici getSatici_id() {
        return satici_id;
    }

    public void setSatici_id(Satici satici_id) {
        this.satici_id = satici_id;
    }

    public Date getTalep_tarihi() {
        return talep_tarihi;
    }

    public void setTalep_tarihi(Date talep_tarihi) {
        this.talep_tarihi = talep_tarihi;
    }
    
    
    
    
}
