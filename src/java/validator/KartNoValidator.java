package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("kartNoValidator")
public class KartNoValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String kartNo = value.toString();

        if (kartNo.length() != 16) {
            throw new ValidatorException(new FacesMessage("Kart numarası 16 haneli olmalıdır"));
        }

        if (!kartNo.matches("[0-9]+")) {
            throw new ValidatorException(new FacesMessage("Kart numarası sadece rakamlardan oluşmalıdır"));
        }
    }
}
