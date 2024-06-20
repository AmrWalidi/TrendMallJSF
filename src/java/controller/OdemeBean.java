package controller;

import dao.KartDAO;
import dao.KuponDAO;
import dao.OdemeDAO;
import dao.SiparisDAO;
import entity.Kart;
import entity.Odeme;
import entity.Sepet;
import entity.SepetUrun;
import entity.Siparis;
import entity.SiparisUrun;
import entity.Urun;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.time.Instant;
import java.sql.Date;
import java.util.List;

@Named(value = "odemeBean")
@SessionScoped
public class OdemeBean implements Serializable {

    private Odeme odeme;
    private Kart kart;
    private Siparis siparis;
    private Sepet sepet;
    private Urun urun;
    private int ay;
    private int yil;
    private double indirimMiktari;
    private String kupon;
    private int odemeTuru;
    private double odemeTutari;
    private boolean saveKart;
    private boolean kuponUygulama;
    private boolean kayitliKart;
    private List<Kart> list;
    private String fromPage;
    @EJB
    private OdemeDAO odemeDAO;
    @EJB
    private KartDAO kartDAO;
    @EJB
    private KuponDAO kuponDAO;
    @EJB
    private SiparisDAO siparisDAO;
    @Inject
    private KullaniciBean kullaniciBean;
    @Inject
    private SepetBean sepetBean;

    public OdemeBean() {
        kuponUygulama = false;
    }

    public String getKupon() {
        return kupon;
    }

    public Odeme getOdeme() {
        if (odeme == null) {
            odeme = new Odeme();
        }
        return odeme;
    }

    public Kart getKart() {
        if (kart == null) {
            kart = new Kart();
        }
        return kart;
    }

    public int getAy() {
        return ay;
    }

    public void setAy(int ay) {
        this.ay = ay;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public void setKupon(String kupon) {
        this.kupon = kupon;
    }

    public int getOdemeTuru() {
        return odemeTuru;
    }

    public void setOdemeTuru(int odemeTuru) {
        this.odemeTuru = odemeTuru;
    }

    public double getOdemeTutari() {
        return odemeTutari;
    }

    public void setOdemeTutari(double odemeTutari) {
        this.odemeTutari = odemeTutari;
    }

    public boolean isSaveKart() {
        return saveKart;
    }

    public void setSaveKart(boolean saveKart) {
        this.saveKart = saveKart;
    }

    public Sepet getSepet() {
        return sepet;
    }

    public Urun getUrun() {
        return urun;
    }

    public boolean isKayitliKart() {
        return kayitliKart;
    }

    public void setKayitliKart(boolean kayitliKart) {
        this.kayitliKart = kayitliKart;
    }
    

    public List<Kart> getList() {
        list = this.kartDAO.getEntities(kullaniciBean.getMusteri());
        return list;
    }

    public void handleodemeTuru() {
        if (odemeTuru == 1) {
            getOdeme().setTur("kart");
        } else {
            getOdeme().setTur("nakit");
        }
    }

    public String replaceSubstring(String originalString, int startIndex, int endIndex, String replacement) {
        if (originalString == null || originalString.isEmpty()) {
            return originalString;
        }

        StringBuilder sb = new StringBuilder(originalString);
        sb.replace(startIndex, endIndex, replacement);

        return sb.toString();
    }

    public void kuponUygula() {
        if (!kuponUygulama) {
            indirimMiktari = kuponDAO.kuponArama(kupon);
            if (odemeTutari > indirimMiktari) {
                odemeTutari = odemeTutari - indirimMiktari;
                kuponUygulama = true;
            }
            indirimMiktari = 0;
        }
    }

    public String odeme() {
        getOdeme().setTarih(Date.from(Instant.now()));
        getOdeme().setUcret(odemeTutari + 50);

        if (getOdeme().getTur().equals("kart")) {
            if (saveKart) {
                Date expiryDate = Date.valueOf(yil + "-" + ay + "-01");
                this.getKart().setSonKullanmaTarihi(expiryDate);
                this.getKart().setMusteri(kullaniciBean.getMusteri());
                this.kartDAO.create(kart);
            }
        }
        this.odemeDAO.create(odeme);
        siparis = new Siparis();
        siparis.setMusteri(kullaniciBean.getMusteri());
        siparis.setOdeme(odeme);
        if (this.fromPage.equals("sepet onayla")) {
            for (SepetUrun su : sepet.getUrunler()) {
                siparis.getUrunler().add(new SiparisUrun(siparis, su.getUrun(), su.getAdet()));
            }
            this.sepetBean.delete();
        } else {
            siparis.getUrunler().add(new SiparisUrun(siparis, urun, 1));
        }
        this.siparisDAO.create(siparis);
        kart = new Kart();
        saveKart = false;
        return "siparis-alindi.xhtml";
    }

    public String simdiAl(Urun u) {
        this.urun = u;
        this.odemeTutari = urun.getFiyat();
        this.fromPage = "simdi Al";
        this.kuponUygulama = false;
        return "odeme.xhtml";
    }

    public String sepetOnayla(Sepet s) {
        this.sepet = s;
        this.odemeTutari = sepet.getToplamUcret();
        this.fromPage = "sepet onayla";
        this.kuponUygulama = false;
        return "odeme.xhtml";
    }
}
