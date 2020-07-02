package designpattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-13
 * Time:9:17
 * 一万年太久，只争朝夕，加油
 */
public class ThreadDemo03 {
    static class BlockingQueue{
        //使用数组方式
        private int[] array=new int[1000];
        private volatile int head=0;
        private volatile int tail=0;
        //[head,tail)
        public volatile  int size=0;

        //入队列
        //1、加锁
        //2、阻塞效果
        public  void put(int val) throws InterruptedException {
            synchronized (this) {
                if(size==array.length){
                    //队列满了，就阻塞,等另外一个线程出队列成功
                    wait();
                }
                array[tail]=val;
                tail++;
                if(tail==array.length){
                    tail=0;
                }
                size++;
                notify();//通知另一个
            }
        }
        //出队列
        public  Integer  take() throws InterruptedException {
            int ret=-1;
            synchronized (this) {
                if(size==0){
                    this.wait();
                }
                ret = array[head];
                head++;
                if(head==array.length){
                    head=0;
                }
                size--;
                notify();
            }
            return ret;
        }
        //取队首元素
        //阻塞队列只支持前两种操作
    }

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue b=new BlockingQueue();
        Thread producer=new Thread(){
            @Override
            public void run() {
                for(int i=0;i<10000;i++){
                    try {
                        b.put(i);
                        System.out.println("生产元素:"+i);
                       Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        producer.start();


        Thread consumer=new Thread(){
            @Override
            public void run() {
                while(true){
                    try {

                        int ret =b.take();
                        System.out.println("消费元素: "+ret);
                       // Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        consumer.start();

    }
}
