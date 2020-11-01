package server;

import java.util.concurrent.*;

/**
 * @author ：WangYaHao
 * @date ：Created in 2020-10-15 06:41
 */
public class MyThreadPoolExecutor {

    private static ThreadPoolExecutor executor;


    public MyThreadPoolExecutor() {
        if (executor == null){
            this.executor = getExecutor();
        }

    }

    public ThreadPoolExecutor getExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10, 60,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));
        return threadPoolExecutor;
    }

}
