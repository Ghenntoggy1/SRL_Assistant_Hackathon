package Utils;


public class ResponseHelper {

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Success");
    }

    public static <T> ApiResponse<T> error(String message, int errorCode) {
        return new ApiResponse<>(message, null, errorCode);
    }
}
