package controller;

import dao.KullaniciDAO;
import dao.MusteriDAO;
import dao.SaticiDAO;
import entity.Kullanici;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "kullaniciBean")
@SessionScoped
public class KullaniciBean implements Serializable {

    private Kullanici entity;
    private KullaniciDAO kDao;
    private MusteriDAO mDao;
    private SaticiDAO sDao;
    private int type;
    private String errorMessage;
    private String sayfa;

    public KullaniciBean() {
        this.sayfa = "giriş";
    }

    public Kullanici getEntity() {
        if (entity == null) {
            entity = new Kullanici();
        }
        return entity;
    }

    public void setEntity(Kullanici entity) {
        this.entity = entity;
    }

    public KullaniciDAO getkDao() {
        if (this.kDao == null) {
            kDao = new KullaniciDAO();
        }
        return kDao;
    }

    public MusteriDAO getmDao() {
        if (this.mDao == null) {
            mDao = new MusteriDAO();
        }
        return mDao;
    }

    public SaticiDAO getsDao() {
        if (this.sDao == null) {
            sDao = new SaticiDAO();
        }
        return sDao;
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
        if (this.getmDao().getMusteri(entity)) {
            return "Login successful";
        } else if (this.getsDao().getSatici(entity)) {
            return "Login successful";
        }
        return "Login Failed";
    }

    public void create() {
        if (entity.getAd().length() > 20 || entity.getAd().matches(".*\\d+.*")) {
            setErrorMessage("Kullanici adı 20 karekterden az oluşur ve rakamlarden oluşmaz");
        } else if (entity.getSoyad().length() > 20 || entity.getSoyad().matches(".*\\d+.*")) {
            setErrorMessage("Kullanıcı soyadı 20 karekterden az oluşur ve rakamlarden oluşmaz");
        } else if (entity.getEposta().length() > 50 || !(entity.getEposta().indexOf('@') >= 0)) {
            setErrorMessage("E-posta 50 karekterden az oluşur ve @ sembol içerir");
        } else if (entity.getSifre().length() < 6 || entity.getSifre().length() > 16) {
            setErrorMessage("Şifre 6 ve 16 arasında karekterden oluşur");
        } else if (entity.getTelNo().charAt(0) != '5' || entity.getTelNo().length() != 10 || !entity.getTelNo().matches("\\d+")) {
            setErrorMessage("telefon numarasi 5 ile başlar ve 10 rakamlardan oluşur");
        } else if (this.getmDao().getMusteri(entity.getEposta()) || this.getsDao().getSatici(entity.getEposta())) {
            setErrorMessage("E-posta zaten alındı");
        } else {
            if (type == 1) {
                this.getkDao().create(entity, "musteri");

            } else {
                this.getkDao().create(entity, "satici");
            }
        }
    }
}
