package controller;

import dao.KategoriDAO;
import dao.UrunDAO;
import entity.Kategori;
import entity.Urun;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "urunBean")
@SessionScoped
public class UrunBean implements Serializable {

    @EJB
    private UrunDAO urunDAO;
    @EJB
    private KategoriDAO kategoriDAO;
    private List<Urun> urunler;
    private List<Kategori> selectedKategoriler;
    private int counter = 1;
    private int pageSayisi;
    private String arananUrun;

    public UrunBean() {

    }

    public UrunDAO getUrunDAO() {
        return urunDAO;
    }

    public KategoriDAO getKategoriDAO() {
        return kategoriDAO;
    }
    
    
    public List<Urun> getUrunler() {
        if (urunler == null) {
            urunler = urunDAO.getUrunler(counter);
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
        return kategoriDAO.getEntities();
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
            urunSayisi = this.urunDAO.getUrunSayisi(arananUrun);
        } else if (!selectedKategoriler.isEmpty()) {
            urunSayisi = this.urunDAO.getUrunSayisi(selectedKategoriler);
        } else {
            urunSayisi = this.urunDAO.getUrunSayisi();
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
            setUrunler(urunDAO.getUrunler(counter));
        } else {

            setUrunler(urunDAO.getUrunler(counter, selectedKategoriler));
        }
    }

    public void previousPage() {
        counter--;
        if (selectedKategoriler.isEmpty()) {
            setUrunler(urunDAO.getUrunler(counter));
        } else {
            setUrunler(urunDAO.getUrunler(counter, selectedKategoriler));
        }
    }

    public void filter() {
        counter = 1;
        setUrunler(urunDAO.getUrunler(counter, selectedKategoriler));
    }

    public void temizle() {
        counter = 1;
        arananUrun = "";
        selectedKategoriler.clear();
        setUrunler(urunDAO.getUrunler(counter));
    }

    public void search() {
        counter = 1;
        selectedKategoriler.clear();
        setUrunler(urunDAO.getUrunler(counter, arananUrun));
    }
}
