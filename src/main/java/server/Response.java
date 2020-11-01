package server;

import java.io.*;

public class Response {

    private OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void outputHtml(String path) throws IOException {
        // 获取文件的绝对路径
        String absoluteResourcePath = StaticResourcesUtil.getAbsoluteResourcePath(path);
        File file = new File(absoluteResourcePath);
        if (file.exists() && file.isFile()) {
            // 读取文件
            StaticResourcesUtil.outputHtml(new FileInputStream(file), outputStream);
        } else {
            System.out.println("response outputHtml file is not found :" + path);
            output(HttpProtocolUtil.getHttpHead404());
        }
    }
}
