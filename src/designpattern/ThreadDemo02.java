package designpattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-11
 * Time:19:43
 * 一万年太久，只争朝夕，加油
 */
public class ThreadDemo02 {
    //懒汉模式：当类被加载的时候，不立刻实例化，第一次使用这个实例的时候在进行实例化
    static class Singleton{
        private Singleton(){ }
        volatile private static Singleton instance=null;
        public static Singleton getInstance(){
            //第一次调用getInstance的时候，才去实例化
            //如果要是代码一整场都没有调用这个方法，那么这个实例化也不会被触发
            //延时加载
            if(instance==null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }

    }

    public static void main(String[] args) {
        Singleton s1=Singleton.getInstance();
        Singleton s2=Singleton.getInstance();
        System.out.println(s1==s2);
    }
}
