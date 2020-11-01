package server;

public class MyServlet extends HttpServlet {
    void doGet(Request request, Response response) {
        try{
            Thread.sleep(10000);
            String content = "<h1>doGet Hello MyServlet</h1>";
            response.output(HttpProtocolUtil.getHttpHead200(content.getBytes().length) + content);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void doPost(Request request, Response response) {
        try{
            String content = "<h1>doPost Hello MyServlet</h1>";
            response.output(HttpProtocolUtil.getHttpHead200(content.getBytes().length) + content);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void init() {
        System.out.println("MyServlet init");
    }

    public void destroy() {
        System.out.println("MyServlet destroy");
    }
}
