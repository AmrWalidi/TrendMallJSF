package entity;

import java.util.Date;

public class KartOdeme {
    
    private String kartNo;
    private String kartSahibi;
    private Date sonKullanimTarihi;
    private String cvv;
    private Odeme odeme;

    public KartOdeme(String kartNo, String kartSahibi, Date sonKullanimTarihi, String cvv, Odeme odeme) {
        this.kartNo = kartNo;
        this.kartSahibi = kartSahibi;
        this.sonKullanimTarihi = sonKullanimTarihi;
        this.cvv = cvv;
        this.odeme = odeme;
    }

    public KartOdeme() {
    }

    public String getKartNo() {
        return kartNo;
    }

    public void setKartNo(String kartNo) {
        this.kartNo = kartNo;
    }

    public String getKartSahibi() {
        return kartSahibi;
    }

    public void setKartSahibi(String kartSahibi) {
        this.kartSahibi = kartSahibi;
    }

    public Date getSonKullanimTarihi() {
        return sonKullanimTarihi;
    }

    public void setSonKullanimTarihi(Date sonKullanimTarihi) {
        this.sonKullanimTarihi = sonKullanimTarihi;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Odeme getOdeme() {
        return odeme;
    }

    public void setOdeme(Odeme odeme) {
        this.odeme = odeme;
    }
    
    
    
}
