package controller;

import dao.UrunDAO;
import entity.Kategori;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "urunBean")
@SessionScoped
public class UrunBean implements Serializable {

    private List<Urun> urunler;
    private List<Kategori> kategoriler;
    private List<Kategori> selectedKategoriler;
    private UrunDAO dao;
    private int counter = 1;
    private int pageSayisi;
    private String urun;

    public UrunBean() {

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

    public String getUrun() {
        if (urun == null) {
            urun = "";
        }
        return urun;
    }

    public void setUrun(String urun) {
        this.urun = urun;
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

    public UrunDAO getDao() {
        if (dao == null) {
            dao = new UrunDAO();
        }
        return dao;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getPageSayisi() {
        int urunSayisi;
        if (!urun.isEmpty()) {
            urunSayisi = this.getDao().getUrunSayisi(urun);
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
        urun = "";
        selectedKategoriler.clear();
        setUrunler(getDao().getUrunler(counter));
    }

    public void search() {
        counter = 1;
        selectedKategoriler.clear();
        setUrunler(getDao().getUrunler(counter, urun));
    }

}
