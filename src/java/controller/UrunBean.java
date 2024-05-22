package controller;

import dao.UrunDAO;
import entity.Kategori;
import entity.Satici;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "urunBean")
@SessionScoped
public class UrunBean implements Serializable {

    
    private UrunDAO dao;
    private List<Urun> urunler;
    private List<Kategori> kategoriler;
    private List<Kategori> selectedKategoriler;
    private int counter = 1;
    private int pageSayisi;
    private String arananUrun;

    public UrunBean() {

    }
    
    public UrunDAO getDao() {
        if (dao == null) {
            dao = new UrunDAO();
        }
        return dao;
    }
    
    public List<Urun> getUrunler() {
        if (urunler == null) {
            urunler = getDao().getUrunler(counter);
        }
        return urunler;
    }

    private void setUrunler(List<Urun> urunler) {
        this.urunler = urunler;
    }

    public String getArananUrun() {
        if (arananUrun == null) {
            arananUrun = "";
        }
        return arananUrun;
    }

    public void setArananUrun(String arananUrun) {
        this.arananUrun = arananUrun;
    }

    public List<Kategori> getKategoriler() {
        if (kategoriler == null) {
            this.kategoriler = getDao().getKategoriler();
        }
        return kategoriler;
    }

    public List<Kategori> getSelectedKategoriler() {
        if (selectedKategoriler == null) {
            selectedKategoriler = new ArrayList<>();
        }
        return selectedKategoriler;
    }

    public void setSelectedKategoriler(List<Kategori> selectedKategoriler) {
        this.selectedKategoriler = selectedKategoriler;
    }
    

    public int getCounter() {
        return counter;
    }
    

    public int getPageSayisi() {
        int urunSayisi;
        if (!arananUrun.isEmpty()) {
            urunSayisi = this.getDao().getUrunSayisi(arananUrun);
        } else if (!selectedKategoriler.isEmpty()) {
            urunSayisi = this.getDao().getUrunSayisi(selectedKategoriler);
        } else {
            urunSayisi = this.getDao().getUrunSayisi();
        }
        pageSayisi = urunSayisi / 5;
        if (urunSayisi % 5 != 0 || pageSayisi == 0) {
            pageSayisi++;
        }
        return pageSayisi;
    }

    public void nextPage() {
        counter++;
        if (selectedKategoriler.isEmpty()) {
            setUrunler(getDao().getUrunler(counter));
        } else {

            setUrunler(getDao().getUrunler(counter, selectedKategoriler));
        }
    }

    public void previousPage() {
        counter--;
        if (selectedKategoriler.isEmpty()) {
            setUrunler(getDao().getUrunler(counter));
        } else {
            setUrunler(getDao().getUrunler(counter, selectedKategoriler));
        }
    }

    public void filter() {
        counter = 1;
        setUrunler(getDao().getUrunler(counter, selectedKategoriler));
    }

    public void temizle() {
        counter = 1;
        arananUrun = "";
        selectedKategoriler.clear();
        setUrunler(getDao().getUrunler(counter));
    }

    public void search() {
        counter = 1;
        selectedKategoriler.clear();
        setUrunler(getDao().getUrunler(counter, arananUrun));
    }
}
