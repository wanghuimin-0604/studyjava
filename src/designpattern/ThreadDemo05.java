package designpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-14
 * Time:9:25
 * 一万年太久，只争朝夕，加油
 */
public class ThreadDemo05 {
    //Worker指的是工作类
    static class Worker extends Thread{
        //通过一个阻塞队列把多个任务组织起来
        private BlockingQueue<Runnable> queue=null;

        public Worker(BlockingQueue<Runnable> queue) {
            this.queue = queue;
        }
        //run()方法用来描述线程任务

        @Override
        public void run() {
            try {
            while(!Thread.currentThread().isInterrupted()) {
                Runnable command = queue.take();
                command.run();
            }
                } catch (InterruptedException e) {
                    System.out.println("线程被终止");
                }
        }
    }
    static class MyThreadPool {
        private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        private List<Worker> workers = new ArrayList<>();
        private static final int maxWorkerCount = 10;

        //实现两个方法
        public void excute(Runnable command) throws InterruptedException {
            if (workers.size() < maxWorkerCount) {
                Worker worker = new Worker(queue);
                worker.start();
                workers.add(worker);
            }
            //把任务加到队列中
            queue.put(command);
        }

        public void shutdown() throws InterruptedException {
            //结束所有线程
            for (Worker worker : workers) {
                worker.interrupt();
            }
            //等待每个线程结束
            for (Worker worker : workers) {
                worker.join();
            }
        }
    }
    static class Command implements Runnable {
        private int i;

        @Override
        public void run() {
            System.out.println("我正在执行任务："+i);
        }

        public Command(int i) {

            this.i = i;
        }
    }

        public static void main(String[] args) throws InterruptedException {
            MyThreadPool p=new MyThreadPool();
            for(int i=0;i<1000;i++){
            p.excute(new Command(i));
        }
        Thread.sleep(2000);
            p.shutdown();
            System.out.println("线程池被销毁");
    }
}
