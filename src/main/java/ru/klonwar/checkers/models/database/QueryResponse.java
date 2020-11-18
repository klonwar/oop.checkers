package ru.klonwar.checkers.models.database;

public class QueryResponse {
    private boolean successful;
    private String message = "";

    public QueryResponse(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public QueryResponse(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "QueryResponse{" +
                "successful=" + successful +
                ", message='" + message + '\'' +
                '}';
    }
}
