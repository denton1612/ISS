package ubb.scs.domain.validator;

public class ValidatorFactory {
    private static ValidatorClient validatorClient;

    public static ValidatorClient getValidatorClient() {
        if (validatorClient == null) {
            validatorClient = new ValidatorClient();
        }
        return validatorClient;
    }

}
