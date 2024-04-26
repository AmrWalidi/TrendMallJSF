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

    public KullaniciBean() {
        this.sayfa = "giriş";
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

    public String login() {
        this.setMusteri(null);
        this.setSatici(null);
        if (this.getMusteriDAO().getMusteri(kullanici) != null) {
            this.setMusteri(this.getMusteriDAO().getMusteri(kullanici));
            return "index.xhtml";
        } else if (this.getSaticiDAO().getSatici(kullanici) != null) {
            this.setSatici(this.getSaticiDAO().getSatici(kullanici));
            return "index.xhtml";
        }
        setErrorMessage("E-posta veya şifre hatalı");
        return "giris-form.xhtml";
    }

    public String create() {
        if (kullanici.getAd().length() > 20 || kullanici.getAd().matches(".*\\d+.*")) {
            setErrorMessage("Kullanici adı 20 karekterden az oluşur ve rakamlarden oluşmaz");
        } else if (kullanici.getSoyad().length() > 20 || kullanici.getSoyad().matches(".*\\d+.*")) {
            setErrorMessage("Kullanıcı soyadı 20 karekterden az oluşur ve rakamlarden oluşmaz");
        } else if (kullanici.getEposta().length() > 50 || !(kullanici.getEposta().indexOf('@') >= 0)) {
            setErrorMessage("E-posta 50 karekterden az oluşur ve @ sembol içerir");
        } else if (kullanici.getSifre().length() < 6 || kullanici.getSifre().length() > 16) {
            setErrorMessage("Şifre 6 ve 16 arasında karekterden oluşur");
        } else if (kullanici.getTelNo().charAt(0) != '5' || kullanici.getTelNo().length() != 10 || !kullanici.getTelNo().matches("\\d+")) {
            setErrorMessage("telefon numarasi 5 ile başlar ve 10 rakamlardan oluşur");
        } else if (this.getMusteriDAO().getMusteri(kullanici.getEposta()) || this.getSaticiDAO().getSatici(kullanici.getEposta())) {
            setErrorMessage("E-posta zaten alındı");
        } else {
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
}
