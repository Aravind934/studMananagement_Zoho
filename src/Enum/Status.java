package Enum;

public enum Status {
    OK(200), SUCCESS(201), PERMANANT_REDIRECT(301), TEMPORORY_REDIRECT(302),
    BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404), SERVER_ERROR(500);
    int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
