package common;

import java.util.ArrayList;

public class Response {
    public int status;
    public String message;
    public ArrayList data;
    public Object singleData;

    public Response(int status, String msg, ArrayList data, Object singleData) {
        this.message = msg;
        this.status = status;
        this.data = data;
        this.singleData = singleData;
    }
}
