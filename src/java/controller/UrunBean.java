package controller;

import dao.UrunDAO;
import entity.Urun;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;


@Named(value = "urunBean")
@SessionScoped
public class UrunBean implements Serializable{
    
    private List<Urun> list;
    private UrunDAO dao;
    private int counter = 1;
    private int pageSayisi;
    
    public UrunBean() {
        
    }

    public List<Urun> getList() {
        list = getDao().getUrunler(counter);
        return list;
    }


    public UrunDAO getDao() {
        if(dao == null){
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
        this.pageSayisi = this.getDao().getUrunSayisi() / 5 + 1;
        if(pageSayisi % 5 != 0)
            this.pageSayisi++;
        return pageSayisi;
    }
    
    public void nextPage(){
        counter++;
    }
    
        public void previousPage(){
        counter--;
    }
    
}
