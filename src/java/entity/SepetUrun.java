package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(SepetUrunId.class)
@Table(name = "sepet_urun")
public class SepetUrun {

    @Id
    @ManyToOne
    @JoinColumn(name = "sepet_id")
    private Sepet sepet;

    @Id
    @ManyToOne
    @JoinColumn(name = "urun_id")
    private Urun urun;

    private int adet;
    
    public SepetUrun() {
    }

    public SepetUrun(Sepet sepet, Urun urun, int adet) {
        this.sepet = sepet;
        this.urun = urun;
        this.adet = adet;
    }

    public Sepet getSepet() {
        return sepet;
    }

    public void setSepet(Sepet sepet) {
        this.sepet = sepet;
    }

    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }
    
    
}
