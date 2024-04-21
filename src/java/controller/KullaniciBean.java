package controller;

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
    private MusteriDAO musteriDao;
    private SaticiDAO saticiDao;
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

    public MusteriDAO getMusteriDao() {
        if (this.musteriDao == null) {
            musteriDao = new MusteriDAO();
        }
        return musteriDao;
    }

    public SaticiDAO getSaticiDao() {
        if (this.saticiDao == null) {
            saticiDao = new SaticiDAO();
        }
        return saticiDao;
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
        if (this.getMusteriDao().getMusteri(entity)) {
            return "Login successful";
        } else if (this.getSaticiDao().getSatici(entity)) {
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
        } else if (this.getMusteriDao().getMusteri(entity.getEposta()) || this.getSaticiDao().getSatici(entity.getEposta())) {
            setErrorMessage("E-posta zaten alındı");
        } else {
            if (type == 1) {
                this.getMusteriDao().create(entity);

            } else {
                this.getSaticiDao().create(entity);
            }
        }
    }
}
