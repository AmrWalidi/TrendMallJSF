package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;
        if (password.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Şifre alanı boş olmaz"));
        }
        else if (password.length() < 6 || password.length() > 36) {
            throw new ValidatorException(new FacesMessage("Şifre 6 ve 16 arasında karekterden oluşur"));
        }
    }
    
}
