package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(SiparisUrunId.class)
@Table(name = "siparis_urun")
public class SiparisUrun {

    @Id
    @ManyToOne
    @JoinColumn(name = "siparis_id")
    private Siparis siparis;

    @Id
    @ManyToOne
    @JoinColumn(name = "urun_id")
    private Urun urun;
    
    private int adet;
    
    public SiparisUrun() {
    }

    public SiparisUrun(Siparis siparis, Urun urun, int adet) {
        this.siparis = siparis;
        this.urun = urun;
        this.adet = adet;
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis) {
        this.siparis = siparis;
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
