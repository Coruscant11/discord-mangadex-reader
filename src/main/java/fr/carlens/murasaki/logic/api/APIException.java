package fr.carlens.murasaki.logic.api;

public class APIException extends Exception {

    public APIException(int statusCode, String body) {
        super(String.format("Encountered an error with the API.\nStatus code : %d\nBody :\n%s", statusCode, body));
    }
}
