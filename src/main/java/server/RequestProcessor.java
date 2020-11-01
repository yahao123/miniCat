package server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author ：WangYaHao
 * @date ：Created in 2020-10-15 06:29
 */
public class RequestProcessor extends Thread {

    private Socket socket;
    private Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            HttpServlet servlet = servletMap.get(request.getUrl());
            if (servlet != null){
                servlet.service(request, response);
            }else {
                response.outputHtml(request.getUrl());
            }
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
