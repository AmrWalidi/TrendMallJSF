package controller;

import dao.UrunDAO;
import entity.Kategori;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
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

    public UrunBean() {

    }

    public List<Urun> getUrunler() {
        if (selectedKategoriler == null) {
            urunler = getDao().getUrunler(counter);
        } else {
            urunler = getDao().getKategoriliUrunler(counter, selectedKategoriler);
        }
        return urunler;
    }

    private void setUrunler(List<Urun> urunler) {
        this.urunler = urunler;
    }

    public List<Kategori> getKategoriler() {
        this.kategoriler = getDao().getKategoriler();
        return kategoriler;
    }

    public List<Kategori> getSelectedKategoriler() {
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
        if (selectedKategoriler == null) {
            this.pageSayisi = this.getDao().getUrunSayisi() / 5 + 1;
            if (pageSayisi % 5 != 0) {
                this.pageSayisi++;
            }
        } else {
            this.pageSayisi = this.getDao().getUrunSayisi(selectedKategoriler) / 5 + 1;
        }

        return pageSayisi;
    }

    public void nextPage() {
        counter++;
    }

    public void previousPage() {
        counter--;
    }

    public void filter() {
        counter = 1;
        setUrunler(getDao().getUrunler(selectedKategoriler));
    }
}
