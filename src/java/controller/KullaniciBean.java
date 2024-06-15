package controller;

import dao.MusteriDAO;
import dao.SaticiDAO;
import entity.Kullanici;
import entity.Musteri;
import entity.Satici;
import entity.Urun;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;

@Named(value = "kullaniciBean")
@SessionScoped
public class KullaniciBean implements Serializable {

    private Kullanici kullanici;
    private Musteri musteri;
    private Satici satici;
    @EJB
    private MusteriDAO musteriDAO;
    @EJB
    private SaticiDAO saticiDAO;
    private int type;
    private String errorMessage;
    private String sayfa;
    private boolean sifreSayfasi;
    private String eskiSifre;
    private String yeniSifre;
    private String tekrarSifre;
    private String successMessage;
    private boolean loggedIn;
    @Inject
    private SepetBean sb;

    public KullaniciBean() {
        this.loggedIn = true;
        this.sayfa = "giriş";
        this.sifreSayfasi = false;
    }

    public Kullanici getKullanici() {
        if (kullanici == null) {
            kullanici = new Kullanici();
        }
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public Satici getSatici() {
        return satici;
    }

    public void setSatici(Satici satici) {
        this.satici = satici;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSayfa() {
        return sayfa;
    }

    public void setSayfa(String sayfa) {
        setErrorMessage("");
        this.sayfa = sayfa;
    }

    public boolean getSifreSayfasi() {
        return sifreSayfasi;
    }

    public void setSifreSayfasi(boolean sifreSayfasi) {

        this.sifreSayfasi = sifreSayfasi;
    }

    public String getEskiSifre() {
        return eskiSifre;
    }

    public void setEskiSifre(String eskiSifre) {
        this.eskiSifre = kullanici.encryptString(eskiSifre);
    }

    public String getYeniSifre() {
        return yeniSifre;
    }

    public void setYeniSifre(String yeniSifre) {
        this.yeniSifre = yeniSifre;
    }

    public String getTekrarSifre() {
        return tekrarSifre;
    }

    public void setTekrarSifre(String tekrarSifre) {
        this.tekrarSifre = tekrarSifre;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public MusteriDAO getMusteriDAO() {
        return musteriDAO;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void sepeteEkle(Urun u) {
        if (this.getMusteri() != null) {
            sb.sepeteEkle(u);
        } else {
            loggedIn = false;
        }
    }

    public String logout() {
        this.setMusteri(null);
        this.setSatici(null);
        sb.setSepet(null);
        loggedIn = false;
        return "giris-form.xhtml";
    }

    public void update() {
        if (this.musteriDAO.getMusteri(kullanici) != null) {
            this.musteriDAO.update(musteri);
        } else if (this.saticiDAO.getSatici(kullanici) != null) {
            this.saticiDAO.update(satici);
        }
        setSuccessMessage("Bilgileriniz Başarılı Bir Şekilde Değişti");
        setErrorMessage("");
    }

    public String create() {
        if (this.musteriDAO.getMusteri(kullanici.getEposta()) || this.saticiDAO.getSatici(kullanici.getEposta())) {
            setErrorMessage("E-posta zaten alındı");
            return "giris-form.xhtml";
        } else {
            if (type == 1) {
                Musteri m = new Musteri();
                m.setAd(kullanici.getAd());
                m.setSoyad(kullanici.getSoyad());
                m.setEposta(kullanici.getEposta());
                m.setSifre(kullanici.getSifre());
                m.setTelNo(kullanici.getTelNo());
                m.setAdres(kullanici.getAdres());
                this.musteriDAO.create(m);
            } else {
                Satici s = new Satici();
                s.setAd(kullanici.getAd());
                s.setSoyad(kullanici.getSoyad());
                s.setEposta(kullanici.getEposta());
                s.setSifre(kullanici.getSifre());
                s.setTelNo(kullanici.getTelNo());
                s.setAdres(kullanici.getAdres());
                this.saticiDAO.create(s);
            }
            return this.login();
        }
    }

    public String login() {
        this.setMusteri(null);
        this.setSatici(null);
        if (this.musteriDAO.getMusteri(kullanici) != null) {
            this.setMusteri(this.musteriDAO.getMusteri(kullanici));
            setErrorMessage("");
            loggedIn = true;
            return "index.xhtml";
        } else if (this.saticiDAO.getSatici(kullanici) != null) {
            this.setSatici(this.saticiDAO.getSatici(kullanici));
            setErrorMessage("");
            loggedIn = true;
            return "satici-urunler.xhtml";
        }
        setErrorMessage("E-posta veya şifre hatalı");
        return "giris-form.xhtml";
    }

    public void sifreDegistir() {
        if (eskiSifre.equals(kullanici.getSifre())) {
            if (yeniSifre.equals(tekrarSifre)) {
                if (musteri != null) {
                    getMusteri().setSifre(getMusteri().encryptString(yeniSifre));
                    musteriDAO.update(getMusteri());
                } else {
                    getSatici().setSifre(getSatici().encryptString(yeniSifre));
                    saticiDAO.update(getSatici());
                }
                setErrorMessage("");
                setSuccessMessage("Şifreniz Başarılı Bir Şekilde Değişti");
            } else {
                setErrorMessage("Tekrarlana şifre ve yeni sifre farklı");
            }
        } else {
            setErrorMessage("Şifreniz yanlış");
        }
        if (!getErrorMessage().equals("")) {
            setSuccessMessage("");
        }
    }
    

}
