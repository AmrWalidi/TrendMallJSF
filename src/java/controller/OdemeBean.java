package controller;

import dao.OdemeDAO;
import entity.Sepet;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named(value = "odemeBean")
@SessionScoped
public class OdemeBean implements Serializable {

    private String kartSahibiAdi;
    private long kartNo;
    private int bitisAyi;
    private int bitisYili;
    private int cvv;
    private int indirimMiktari = 0;
    private String kupon;
    private int odemeTuru;
    private double odemeTutari;
    private OdemeDAO odemeDAO;
    @Inject
    private SepetBean sepetBean;

    @Inject
    private KullaniciBean kullaniciBean;

    public OdemeBean() {
    }

    public SepetBean getSepetBean() {
        
        return sepetBean;
    }
    
    
    
    public String getKupon() {
        return kupon;
    }

    public void setKupon(String kupon) {
        this.kupon = kupon;
    }

    public OdemeDAO getOdemeDAO() {
        if (odemeDAO == null) {
            odemeDAO = new OdemeDAO();
        }
        return odemeDAO;
    }

    public void setOdemeDAO(OdemeDAO odemeDAO) {
        this.odemeDAO = odemeDAO;
    }

    public void setKartSahibiAdi(String kartSahibiAdi) {
        this.kartSahibiAdi = kartSahibiAdi;
    }

    public long getKartNo() {
        return kartNo;
    }

    public void setKartNo(long kartNo) {
        this.kartNo = kartNo;
    }

    public int getBitisAyi() {
        return bitisAyi;
    }

    public void setBitisAyi(int bitisAyi) {
        this.bitisAyi = bitisAyi;
    }

    public int getBitisYili() {
        return bitisYili;
    }

    public void setBitisYili(int bitisYili) {
        this.bitisYili = bitisYili;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getOdemeTuru() {
        return odemeTuru;
    }

    public void setOdemeTuru(int odemeTuru) {
        this.odemeTuru = odemeTuru;
    }

    public String getKartSahibiAdi() {
        return kartSahibiAdi;
    }

    public double getOdemeTutari() {
        return odemeTutari;
    }

    public void setOdemeTutari(double odemeTutari) {
        this.odemeTutari = odemeTutari;
    }
    
    

    public int kupon() {
        if (kupon.isEmpty()) {
            indirimMiktari = 0;
        } else {
            indirimMiktari = getOdemeDAO().kuponArama(kupon);
        }
        return indirimMiktari;
    }

    public void kuponUygula() {
        odemeTutari = odemeTutari -(double) kupon();
    }

//    public String odeme() {
//        LocalDate currentDate = LocalDate.now();
//        getOdemeDAO().odeme(kullaniciBean.getMusteri().getId(), odemeTutari, currentDate);
//        if (odemeTuru == 1) {
//            getOdemeDAO().kartOdeme(kartSahibiAdi, kartNo, bitisAyi, bitisYili, cvv);
//        }
//        sepetBean.sepetKaldir();
//        return "SiparisAlindi.xhtml";
//    }

    public String simdiAl(Urun u){
        this.odemeTutari = u.getFiyat();
        return "odeme.xhtml";
    }

    public String sepetOnayla(double toplamUcret){
        this.odemeTutari = toplamUcret;
        return "odeme.xhtml";
    }
}
