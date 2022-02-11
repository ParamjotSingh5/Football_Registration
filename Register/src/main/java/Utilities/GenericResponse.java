package Utilities;

public class GenericResponse {

    public boolean status;
    public boolean success;

    public boolean isStatus() {
        return status;
    }

    public GenericResponse setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public GenericResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GenericResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public GenericResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public String message;
    public Object data;

    public GenericResponse() {
        this.status = true;
        this.success = true;
        this.message = "";
    }

    public GenericResponse(boolean status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public GenericResponse(boolean status, boolean success, String message, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
