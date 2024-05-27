package validator;

import dao.MusteriDAO;
import dao.SaticiDAO;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator{
    

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;
        if (email.isEmpty()) {
            throw new ValidatorException(new FacesMessage("E-posta alanı boş olmaz"));
        }
        else if (email.length() > 50) {
            throw new ValidatorException(new FacesMessage("E-posta 50 karekterden az olmalı"));
        }
        else if (email.indexOf('@') < 0){
            throw new ValidatorException(new FacesMessage("E-posta @ sembol içermeli"));
        }
    }
}
