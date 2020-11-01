package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


/**
 * @author wangyahao
 */
public class Bootstrap {



    public static final Integer PORT = 8080;

    private Map<String, HttpServlet> servletMap = new HashMap<String, HttpServlet>();

    public void start() throws Exception {

        loadServlet();

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("开始监听端口:" + PORT);

        // 1.0
//        while (true) {
//            Socket accept = serverSocket.accept();
//            System.out.println("accept:" + accept);
//            OutputStream outputStream = accept.getOutputStream();
//            String data = "Hello Minicat";
//            outputStream.write((HttpProtocolUtil.getHttpHead200(data.getBytes().length) + data).getBytes());
//            outputStream.close();
//            accept.close();
//        }

        // 2.0
//        while (true) {
//            Socket accept = serverSocket.accept();
//            InputStream inputStream = accept.getInputStream();
//            Request request = new Request(inputStream);
//            String url = request.getUrl();
//            OutputStream outputStream = accept.getOutputStream();
//            Response response = new Response(outputStream);
//            response.outputHtml(url);
////            accept.close();
//        }


        // 3.0
//        while (true){
//            Socket accept = serverSocket.accept();
//            InputStream inputStream = accept.getInputStream();
//            Request request = new Request(inputStream);
//            Response response = new Response(accept.getOutputStream());
//            HttpServlet servlet = servletMap.get(request.getUrl());
//            if (servlet != null){
//                servlet.service(request, response);
//            }else {
//                response.outputHtml(request.getUrl());
//            }
//            accept.close();
//        }

        // 多线层改造
//        while (true){
//            Socket accept = serverSocket.accept();
//            RequestProcessor requestProcessor = new RequestProcessor(accept, servletMap);
//            requestProcessor.start();
//        }


        //线程池
        while (true){
            Socket accept = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(accept, servletMap);
            new MyThreadPoolExecutor().getExecutor().execute(requestProcessor);
        }

    }

    public void loadServlet() throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //读取输入流
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        //转换dom
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(resourceAsStream);
        // web-app
        Element rootElement = document.getRootElement();
        // servlet
        List<Element> elementList = rootElement.selectNodes("//servlet");
        for (Element element : elementList) {
            String servletName = element.selectSingleNode("servlet-name").getStringValue();
            String servletClass = element.selectSingleNode("servlet-class").getStringValue();
            // 根据servlet-name查找url-patten
            Element servletMappingElement = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
            String urlPatten = servletMappingElement.selectSingleNode("url-pattern").getStringValue();
            // 实例化servlet
            HttpServlet servlet = (HttpServlet) Class.forName(servletClass).newInstance();
            servletMap.put(urlPatten, servlet);
        }
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
