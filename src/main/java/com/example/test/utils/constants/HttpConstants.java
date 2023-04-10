package com.example.test.utils.constants;

public enum HttpConstants {

    // HTTP methods
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),

    // HTTP status codes
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    RESET_CONTENT(205),
    PARTIAL_CONTENT(206),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    SERVICE_UNAVAILABLE(503),
    HTTP_VERSION_NOT_SUPPORTED(505);

    private final String method;
    private final int statusCode;

    HttpConstants(String method) {
        this.method = method;
        this.statusCode = 0;
    }

    HttpConstants(int statusCode) {
        this.method = null;
        this.statusCode = statusCode;
    }

    public int getValue() {
        return this.statusCode;
    }

    public String getMethod() {
        return method;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
