package ubb.scs.domain.validator;

import ubb.scs.domain.Client;
import ubb.scs.service.ServiceException;

public class ValidatorClient implements Validator<Long, Client>{
    // TODO: Mesaje de validare mai explicite

    ValidatorClient() {} // nu permitem constructorului sa fie accesat in afara package-ului (se va folosi ValidatorFactory)

    @Override
    public void validate(Client entity) {
        String errors = "";
        if (!validateUsername(entity.getUsername())) {
            errors += "Username is not valid!";
        }
        if (!validatePassword(entity.getPassword())) {
            errors += " ";
            errors += "Password is not valid!";
        }

        if (!errors.isEmpty()) {
            throw new ServiceException(errors);
        }
    }

    private boolean validateUsername(String username) {
        return !username.isEmpty();
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        return password.matches(".*[,.?!].*");
    }
}
