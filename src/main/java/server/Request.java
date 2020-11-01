package server;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private String method;

    private String url;

    private InputStream inputStream;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Request() {
    }

    public Request(InputStream inputStream) throws IOException {
        int available = 0;
        while (available == 0) {
            available = inputStream.available();
        }
        byte[] bytes = new byte[available];
        inputStream.read(bytes);
        String readStr = new String(bytes, "UTF-8");
        String[] readStrLines = readStr.split("\\n");
        String firstLine = readStrLines[0]; // GET / HTTP/1.1
        String[] firstLineStrArray = firstLine.split(" ");
        this.method = firstLineStrArray[0];
        this.url = firstLineStrArray[1];
        this.inputStream = inputStream;
        System.out.println(Thread.currentThread().getName() +  " request method:" + method);
        System.out.println(Thread.currentThread().getName() +  " request url:" + url);
    }
}
