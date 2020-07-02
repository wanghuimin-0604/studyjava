package designpattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-24
 * Time:14:11
 * 一万年太久，只争朝夕，加油
 */
//创建线程池
public class ThreadDemo06 {
    public static void main(String[] args) {
        ExecutorService es=Executors.newFixedThreadPool(2);
        //提交一个任务用于执行
        es.submit(new RunnableImpl());
        es.submit(new RunnableImpl());
        es.submit(new RunnableImpl());
        //es.shutdown();

    }

}
