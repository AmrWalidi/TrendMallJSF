package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator
public class PhoneNumberValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String telNo = (String) value;
        if (telNo.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Bu alanı boş olmaz"));
        }
        else if (telNo.charAt(0) != '5') {
            throw new ValidatorException(new FacesMessage("telefon numarasi 5 ile başlamalı"));
        }
        else if (telNo.length() != 10 || !telNo.matches("\\d+")) {
            throw new ValidatorException(new FacesMessage(" 10 rakamlardan oluşur"));
        }
    }
    
}
