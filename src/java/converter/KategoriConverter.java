package converter;

import dao.UrunDAO;
import entity.Kategori;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("kategoriConverter")
public class KategoriConverter implements Converter {

    private UrunDAO dao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int id = Integer.valueOf(value);
        Kategori k = this.getDao().getKategori(id);
        return k;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Kategori k = (Kategori)value;
        return String.valueOf(k.getId());
    }

    public UrunDAO getDao() {
        if (dao == null) {
            dao = new UrunDAO();
        }
        return dao;
    }

}
