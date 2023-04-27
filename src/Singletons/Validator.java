package Singletons;

public class Validator {

    private static Validator validator;

    private Validator() {
    }

    public static Validator GetValidator() {
        if (validator == null)
            validator = new Validator();
        return validator;
    }

    // validates a given double as a string with optional inclusive limits
    public double validateDoubleInput(String string_input,
                                      double min_limit,
                                      double max_limit,
                                      boolean no_min_limit,
                                      boolean no_max_limit,
                                      double invalid_input) {

        // variables
        double double_user_input;

        // trying to convert string input to double
        try {
            double_user_input = Double.parseDouble(string_input);
        }
        // if conversion fails return as invalid input
        catch (NumberFormatException e) {
            return invalid_input;
        }

        // return the double input if:

        // there is no minimum and no maximum limit,
        // there is no minimum and the value is less than the maximum limit,
        // the value is more than the minimum and there is no maximum limit,
        // the value is more than the minimum and less than the maximum limit,

        if ((no_min_limit && no_max_limit) ||
                (no_min_limit && double_user_input <= max_limit) ||
                (double_user_input >= min_limit && no_max_limit) ||
                (double_user_input >= min_limit && double_user_input <= max_limit)) {

            return double_user_input;

        } else {
            return invalid_input;
        }
    }

    // validates a given integer as a string
    public int validateIntInput(String string_input,
                                int min_limit,
                                int max_limit,
                                boolean no_min_limit,
                                boolean no_max_limit,
                                int invalid_input) {

        return (int) validateDoubleInput(string_input, min_limit, max_limit, no_min_limit, no_max_limit, invalid_input);
    }
}
