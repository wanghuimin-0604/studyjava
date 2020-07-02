package designpattern;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-13
 * Time:11:10
 * 一万年太久，只争朝夕，加油
 */
public class ThreadDemo04 {
    //描述一个任务
    static class Task implements Comparable<Task> {
        //Runnable中有一个run方法，借着这个方法来描述要执行的任务
        public Runnable command;
        //time表示啥时候要执行任务(绝对时间）
        public long time;

        //多少ms之后
        public Task(Runnable command, long after) {
            this.command  = command;
            //相对时间
            this.time = System.currentTimeMillis() + after;
        }

        public void run() {
            command.run();
        }

        @Override
        public int compareTo(Task o) {
            //谁时间小谁先执行
            return (int) (this.time - o.time);
        }
    }
    static  class Worker extends Thread {
        private PriorityBlockingQueue<Task> queue=null;
        private  Object mailBox=null;

        public Worker(PriorityBlockingQueue<Task> queue,Object mailBox) {
            this.queue = queue;
            this.mailBox=mailBox;
        }

        @Override
        //线程具体实现的内容
        public void run() {
            while (true) {
                try {
                    //取出队首元素
                    Task task = queue.take();
                    long curTime = System.currentTimeMillis();
                    //检查任务执行时间是否答
                    if (task.time > curTime) {
                        //时间还没到，把这个任务再塞回队列中
                        queue.put(task);
                        synchronized (mailBox){
                            //等待时间
                            mailBox.wait(task.time-curTime);
                        }
                    } else {
                        //到时间了的话，直接执行
                        task.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }


    static class Timer {
        //避免忙等  使用wait方法
        private  Object mailBox=new Object();

        //组织(优先阻塞队列）
        private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();



        public Timer() {
            Worker woker = new Worker(queue,mailBox);
            woker.start();
        }
        //提供一个方法，让调用者把任务安排进来
        public void schedule(Runnable command, long after) {
            //创建一个任务
            Task task = new Task(command, after);
            //把这个任务加入队列中
            queue.put(task);
            synchronized (mailBox){
                mailBox.notify();
            }
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hehe");
                timer.schedule(this, 2000);
            }
        }, 2000);

    }
}
