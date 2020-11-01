package server;

public abstract class HttpServlet implements Servlet {

    abstract void doGet(Request request, Response response);

    abstract void doPost(Request request, Response response);

    public void service(Request request, Response response) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
