package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StaticResourcesUtil {

    public static String getAbsoluteResourcePath(String path) {
        String absoluteResource = StaticResourcesUtil.class.getResource("/").getPath();
        return absoluteResource.replace("\\\\", "/") + path;
    }

    public static void outputHtml(FileInputStream inputStream, OutputStream outputStream) throws IOException {
        int contentSize = 0;
        while (contentSize == 0) {
            contentSize = inputStream.available();
        }
        //先把http响应头返回
        outputStream.write(HttpProtocolUtil.getHttpHead200(contentSize).getBytes());
        //TODO 分批读取数据并返回
        int written = 0;
        int readSize = 1024;
        byte[] bytes = new byte[readSize];
        while (written < contentSize) {
            // 当剩下的内容长度没有达到readSize长度时
            if ((written + readSize) > contentSize) {
                readSize = (int) (contentSize - written);
                bytes = new byte[readSize];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            written += readSize;
        }
    }
}
