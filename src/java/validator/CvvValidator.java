package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("cvvValidator")
public class CvvValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String cvv = value.toString();

        if (cvv.length() != 3) {
            throw new ValidatorException(new FacesMessage("CVV 3 haneli olmalıdır"));
        }

        if (!cvv.matches("[0-9]+")) {
            throw new ValidatorException(new FacesMessage("CVV sadece rakamlardan oluşmalıdır"));
        }
    }
}
