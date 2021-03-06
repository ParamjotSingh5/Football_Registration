package Utilities;

public enum ValidationMessages {

    DROPPING_WEBAPP_START_REQUEST("Dropping Run web-app request due to of some misconfiguration."),
    PARAMETER_REQUIRED ("Field required."),
    VALUE_REQUIRED ("Value required for field."),
    CORRUPTED_VALUE ("Please provide a valid value."),
    ONLY_ALPHABET ("Only alphabetic characters are allowed."),
    ONLY_NUMERIC ("Only numeric values allowed."),
    INVALID_LENGTH ("Please provide a valid value"),
    INVALID_Email ("Please provide a valid value"),
    VALID ("Looks good"),
    INVALID_REQUEST("Invalid Request"),
    INTERNAL_SERVER_ERROR("An unhandled error occurred while processing your request."),
    DB_CONNECTION_ERROR("An error occurred while connecting to database."),
    CANNOT_CREATE_NEW_USER("An error occurred while registering a new user."),
    USERNAME_EXISTS("username already exists."),
    INVALID_USERNAME("Invalid Username."),
    USER_DATA_UPDATED("User data updated successfully");


    private final String message;

    private ValidationMessages(String Message) {
        message = Message;
    }

    public String toString() {
        return this.message;
    }
}
