package controller;

import dao.SepetDAO;
import entity.Musteri;
import entity.Sepet;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;

@Named(value = "sepetBean")
@SessionScoped
public class SepetBean implements Serializable {

    private Sepet sepet;
    private SepetDAO dao;
    @Inject
    private KullaniciBean kb;

    public SepetBean() {
    }

    public Sepet getSepet() {
        if (sepet == null) {
            sepet = this.getDao().getSepet(kb.getMusteri().getId());
        }
        return sepet;
    }

    public void setSepet(Sepet sepet) {
        this.sepet = sepet;
    }

    public SepetDAO getDao() {
        if (dao == null) {
            dao = new SepetDAO();
        }
        return dao;
    }

    public void sepeteEkle(Urun u, Musteri m) {
        if (this.getSepet().getId() == 0) {
            this.getDao().createSepet(m.getId());
            this.getDao().sepeteEkle(sepet.getId(), u);
        } else {
            boolean inCart = false;
            for (Urun urun : sepet.getUrunler()){
                if (urun.getId() == u.getId()){
                    urunSayisiArtirir(u);
                    inCart = true;
                    break;
                }
            }
            if (!inCart){
                this.getDao().sepeteEkle(sepet.getId(), u);
                sepet.getUrunler().add(u);
                sepet.setToplamUcret(sepet.getToplamUcret() + u.getFiyat());
            }
        }
    }

    public int getUrunAdet(Urun u) {
        return getDao().getUrunAdet(sepet.getId(), u.getId());
    }

    public void urunSayisiArtirir(Urun u) {
        getDao().urunSayisiArtirir(sepet.getId(), u);
        sepet.setToplamUcret(sepet.getToplamUcret() + u.getFiyat());
    }

    public void urunSayisiAzaltir(Urun u) {
        if (this.getUrunAdet(u) == 1) {
            this.sepettenCikar(u);
        } else {
            getDao().urunSayisiAzaltir(sepet.getId(), u);
            sepet.setToplamUcret(sepet.getToplamUcret() - u.getFiyat());
        }
    }

    public void sepettenCikar(Urun u) {
        sepet.getUrunler().remove(u);
        sepet.setToplamUcret(sepet.getToplamUcret() - (this.getUrunAdet(u) * u.getFiyat()));
        this.getDao().sepettenCikar(sepet.getId(), u);
    }

}
