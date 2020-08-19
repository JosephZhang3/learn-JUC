package chap6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Demonstrate the lifecycle of Executors
 *
 * @author jianghao.zhang
 */
public class LifecycleWebServer {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!executorService.isShutdown()) {
            try {
                final Socket conn = socket.accept();

                // do work
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(conn);
                    }
                });

            } catch (RejectedExecutionException e) {

                // if executor have not shut down, then print the warning info about rejecting task
                if (!executorService.isShutdown()) {
                    System.out.println("task submission rejected,and execute also stop");
                    System.out.println(e.toString());
                }
            }
        }
    }

    void handleRequest(Socket connection) {
        Request req = readRequest(connection);
        if (isShutdownRequest(req)) {

            // if received "SHUTDOWN" command request,do destroy operation
            stop();
        } else {
            dispatchRequest(req);
        }
    }

    private void dispatchRequest(Request req) {
    }

    private boolean isShutdownRequest(Request req) {
        return false;
    }

    private void stop() {
        executorService.shutdown();
    }

    private Request readRequest(Socket s) {
        return null;
    }

    interface Request {
    }
}
