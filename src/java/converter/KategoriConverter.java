package converter;

import controller.UrunBean;
import entity.Kategori;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.inject.Inject;

@RequestScoped
public class KategoriConverter implements Converter {

    @Inject
    private UrunBean urunBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int id = Integer.valueOf(value);
        Kategori k = urunBean.getKategoriDAO().getById(id);
        return k;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Kategori k = (Kategori)value;
        return String.valueOf(k.getId());
    }

}
