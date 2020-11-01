package server;

/**
 * @author wangyahao
 */
public class HttpProtocolUtil {



    public static String getHttpHead200(Integer contentLength){
        return "HTTP/1.1 200 OK\n"+
                "Content-Type: text/html\n"+
                "Content-Length: " + contentLength + "\n" +
                "\r\n";
    }


    public static String getHttpHead404(){
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found OK\n"+
                "Content-Type: text/html\n"+
                "Content-Length: " + str404.getBytes().length + "\n" +
                "\r\n" + str404;
    }
}
