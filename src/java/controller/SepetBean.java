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
                    urunSayisiArtirir(u);
                    inCart = true;
                    break;
                }
            }
            if (!inCart) {
                this.dao.sepeteEkle(sepet, u);
                sepet.getUrunler().add(new SepetUrun(sepet, u, 0));
                sepet.setToplamUcret(sepet.getToplamUcret() + u.getFiyat());
            }
        }
    }

    public int getUrunAdet(Urun u) {
        return dao.getUrunAdet(sepet.getId(), u.getId());
    }

    public void urunSayisiArtirir(Urun u) {
        dao.urunSayisiArtirir(sepet.getId(), u);
        sepet.setToplamUcret(sepet.getToplamUcret() + u.getFiyat());
    }

    public void urunSayisiAzaltir(Urun u) {
        if (this.getUrunAdet(u) == 1) {
            this.sepettenCikar(u);
        } else {
            dao.urunSayisiAzaltir(sepet.getId(), u);
            sepet.setToplamUcret(sepet.getToplamUcret() - u.getFiyat());
        }
    }

    public void sepettenCikar(Urun u) {
        SepetUrun su = new SepetUrun(sepet, u,0);
        for (SepetUrun urun : sepet.getUrunler()) {
                if (urun.getUrun().getId() == su.getUrun().getId()) {
                    su = urun;
                }
            }
        sepet.getUrunler().remove(su);
        sepet.setToplamUcret(sepet.getToplamUcret() - (this.getUrunAdet(u) * u.getFiyat()));
        this.dao.sepettenCikar(sepet.getId(), u);
    }

}
