package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("surnameValidator")
public class SurnameValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String name = (String) value;
        if (name.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Soyad boş olmaz"));
        } else if (name.length() > 20) {
            throw new ValidatorException(new FacesMessage("Soyad 20 karekterden az olmalı"));
        } else if (name.matches(".*\\d+.*")) {
            throw new ValidatorException(new FacesMessage("Soyad rakamlarden oluşmaz"));
        }
    }
}
