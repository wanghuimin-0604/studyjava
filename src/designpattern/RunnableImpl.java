package designpattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-24
 * Time:14:22
 * 一万年太久，只争朝夕，加油
 */
public class RunnableImpl implements Runnable {
    //设置线程任务
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"创建了一个新的线程");
    }
}
