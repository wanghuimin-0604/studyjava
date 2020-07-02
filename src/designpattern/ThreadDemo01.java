package designpattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:wanghuimin
 * Date:2020-06-11
 * Time:19:33
 * 一万年太久，只争朝夕，加油
 */


    public class ThreadDemo01 {
        //饿汉模式：只有类被加载，实例就会被立刻创建
        //实现单例模式的类
    static class Singleton {
        //把构造方法变成私有，此时再该类外部就不能new这个类的实例了
        private Singleton() {}
            //再来创建static成员
            static Singleton instance=new Singleton();
        private static Singleton getInstance(){
            return instance;

        }

        public static void main(String[] args) {
           // Singleton s3 = new Singleton();
            //获取该类实例的唯一方式，不应该使用其他方式来创建实例
            Singleton s = Singleton.getInstance();
            Singleton s2 = Singleton.getInstance();
            System.out.println(s == s2);


        }
    }
}
