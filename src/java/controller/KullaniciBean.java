package controller;

import dao.MusteriDAO;
import dao.SaticiDAO;
import entity.Kullanici;
import entity.Musteri;
import entity.Satici;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "kullaniciBean")
@SessionScoped
public class KullaniciBean implements Serializable {

    private Kullanici kullanici;
    private Musteri musteri;
    private Satici satici;
    private MusteriDAO musteriDAO;
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

    public MusteriDAO getMusteriDAO() {
        if (this.musteriDAO == null) {
            musteriDAO = new MusteriDAO();
        }
        return musteriDAO;
    }

    public SaticiDAO getSaticiDAO() {
        if (this.saticiDAO == null) {
            saticiDAO = new SaticiDAO();
        }
        return saticiDAO;
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
        this.sayfa = sayfa;
    }

    public boolean isSifreSayfasi() {
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
    
    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public void isLoggedIn() {
        loggedIn = this.getMusteri() != null || this.getSatici() != null;
    }
    
    public String login() {
        this.setMusteri(null);
        this.setSatici(null);
        if (this.getMusteriDAO().getMusteri(kullanici) != null) {
            this.setMusteri(this.getMusteriDAO().getMusteri(kullanici));
            setErrorMessage("");
            return "index.xhtml";
        } else if (this.getSaticiDAO().getSatici(kullanici) != null) {
            this.setSatici(this.getSaticiDAO().getSatici(kullanici));
            setErrorMessage("");
            return "index.xhtml";
        }
        setErrorMessage("E-posta veya şifre hatalı");
        return "giris-form.xhtml";
    }

    public String logout() {
        this.setMusteri(null);
        this.setSatici(null);
        return "giris-form.xhtml";
    }

    public void update() {
        if (this.getMusteriDAO().getMusteri(kullanici) != null) {
            this.getMusteriDAO().update(musteri);
            setSuccessMessage("Şifreniz Başarılı Bir Şekilde Değişti");
        } else if (this.getSaticiDAO().getSatici(kullanici) != null) {
            this.getSaticiDAO().update(satici);
            setSuccessMessage("Şifreniz Başarılı Bir Şekilde Değişti");
        }
        setErrorMessage("E-posta veya şifre hatalı");
    }

    public String create() {
        if (kullanici.getAd().length() > 20 || kullanici.getAd().matches(".*\\d+.*")) {
            setErrorMessage("Kullanici adı 20 karekterden az oluşur ve rakamlarden oluşmaz");
        } else if (kullanici.getSoyad().length() > 20 || kullanici.getSoyad().matches(".*\\d+.*")) {
            setErrorMessage("Kullanıcı soyadı 20 karekterden az oluşur ve rakamlarden oluşmaz");
        } else if (kullanici.getEposta().length() > 50 || !(kullanici.getEposta().indexOf('@') >= 0)) {
            setErrorMessage("E-posta 50 karekterden az oluşur ve @ sembol içerir");
        } else if (kullanici.getSifre().length() < 6 || kullanici.getSifre().length() > 36) {
            setErrorMessage("Şifre 6 ve 16 arasında karekterden oluşur");
        } else if (kullanici.getTelNo().charAt(0) != '5' || kullanici.getTelNo().length() != 10 || !kullanici.getTelNo().matches("\\d+")) {
            setErrorMessage("telefon numarasi 5 ile başlar ve 10 rakamlardan oluşur");
        } else if (this.getMusteriDAO().getMusteri(kullanici.getEposta()) || this.getSaticiDAO().getSatici(kullanici.getEposta())) {
            setErrorMessage("E-posta zaten alındı");
        } else {
            setErrorMessage("");
            if (type == 1) {
                this.getMusteriDAO().create(kullanici);
                return this.login();
            } else {
                this.getSaticiDAO().create(kullanici);
                return this.login();
            }
        }
        return "giris-form.xhtml";
    }

    public void sifreDegistir() {
        if (eskiSifre.equals(kullanici.getSifre())) {
            if (yeniSifre.equals(tekrarSifre)) {
                if (yeniSifre.length() >= 6 || yeniSifre.length() <= 36) {
                    if (musteri != null) {
                        getMusteri().setSifre(yeniSifre);
                        getKullanici().setSifre(yeniSifre);
                        getMusteriDAO().update(getMusteri());
                    } else {
                        getSatici().setSifre(yeniSifre);
                        getKullanici().setSifre(yeniSifre);
                        getSaticiDAO().update(getSatici());
                    }
                    setErrorMessage("");
                    setSuccessMessage("Şifreniz Başarılı Bir Şekilde Değişti");
                } else {
                    setErrorMessage("Şifre 6 ve 16 arasında karekterden oluşur");
                }
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
