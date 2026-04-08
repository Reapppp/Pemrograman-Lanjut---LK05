package gateway;

import interfaces.Confidential;
import interfaces.MedicalRecord;

public class SecureResponse<T extends MedicalRecord & Confidential> {

    private boolean success;
    private T data;
    private String message;

    public SecureResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "SecureResponse" +
               "\n  Success : " + success +
               "\n  Message : " + message +
               "\n  Data    :\n" + data;
    }
}
