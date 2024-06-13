package controller;

import dao.SepetDAO;
import entity.Sepet;
import entity.SepetUrun;
import entity.Urun;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;

@Named(value = "sepetBean")
@SessionScoped
public class SepetBean implements Serializable {

    private Sepet sepet;
    @EJB
    private SepetDAO dao;
    @Inject
    private KullaniciBean kb;

    public SepetBean() {
    }

    public Sepet getSepet() {
        if (sepet == null) {
            sepet = this.dao.getSepetByMusteriId(kb.getMusteri().getId());
        }
        return sepet;
    }

    public void setSepet(Sepet sepet) {
        this.sepet = sepet;
    }

    public void sepeteEkle(Urun u) {
        if (this.getSepet().getId() == 0) {
            sepet.setMusteri(kb.getMusteri());
            this.dao.create(sepet);
            this.dao.sepeteEkle(sepet, u);
        } else {
            boolean inCart = false;
            for (SepetUrun su : sepet.getUrunler()) {
                if (su.getUrun().getId() == u.getId()) {
                    inCart = true;
                    break;
                }
            }
            if (inCart) {
                urunSayisiArtirir(u);
            } else {
                this.dao.sepeteEkle(sepet, u);
            }
        }
    }

    public int getUrunAdet(Urun u) {
        return dao.getUrunAdet(sepet.getId(), u.getId());
    }

    public void urunSayisiArtirir(Urun u) {
        dao.urunSayisiArtirir(sepet, u);
    }

    public void urunSayisiAzaltir(Urun u) {
        if (this.getUrunAdet(u) == 1) {
            this.sepettenCikar(u);
        } else {
            dao.urunSayisiAzaltir(sepet, u);
        }
    }

    public void sepettenCikar(Urun u) {
        this.dao.sepettenCikar(sepet, u);
        if (sepet.getUrunler().isEmpty()) {
            delete(this.sepet);
        }
    }

    public void delete(Sepet s) {
        this.dao.delete(sepet);
        sepet = new Sepet();
    }

}
