package az.orient.hotelmgsystem.exception;

public class HotelException extends RuntimeException {
    private Integer code;

    public HotelException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

