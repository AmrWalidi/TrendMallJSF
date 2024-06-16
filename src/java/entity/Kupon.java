package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Kupon {
    @Id 
    private String kod;
    @Column(name = "indirim_miktari")
    private double indirimMiktari;

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public double getIndirimMiktari() {
        return indirimMiktari;
    }

    public void setIndirimMiktari(double indirimMiktari) {
        this.indirimMiktari = indirimMiktari;
    }
}
