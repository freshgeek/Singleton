package top.design.singleton;


/***
 * 
 * 双重校验锁
 * 
* @Description:TODO
* @author:   chen.chao
* @time:2018年10月23日 下午3:30:24
 */
public class Singleton7 {
    private volatile static Singleton7 singleton;  
    /***
     * 没有volatile修饰符，可能出现Java中的另一个线程看到个初始化了一半的_instance的情况
     * 但使用了volatile变量后，就能保证先行发生关系（happens-before relationship）。
     * 对于volatile变量_instance，所有的写（write）都将先行发生于读（read），
     * 在Java 5之前不是这样，所以在这之前使用双重检查锁有问题。
     * 现在，有了先行发生的保障（happens-before guarantee），你可以安全地假设其会工作良好。
     * 另外，这不是创建线程安全的单例模式的最好方法，你可以使用枚举实现单例模式，
     * 这种方法在实例创建时提供了内置的线程安全。
     * 我们都知道一个经典的懒加载方式的双重判断单例模式：
 
               instance = new Singleton();  //非原子操作
  
看似简单的一段赋值语句：instance= new Singleton()，但是它并不是一个原子操作，其实际上可以抽象为下面几条JVM指令：

memory =allocate();    //1：分配对象的内存空间 

ctorInstance(memory);  //2：初始化对象 

instance =memory;     //3：设置instance指向刚分配的内存地址 

 

上面操作2依赖于操作1，但是操作3并不依赖于操作2，所以JVM是可以针对它们进行指令的优化重排序的，经过重排序后如下：

memory =allocate();    //1：分配对象的内存空间 

instance =memory;     //3：instance指向刚分配的内存地址，此时对象还未初始化

ctorInstance(memory);  //2：初始化对象

 

可以看到指令重排之后，instance指向分配好的内存放在了前面，而这段内存的初始化被排在了后面。

在线程A执行这段赋值语句，在初始化分配对象之前就已经将其赋值给instance引用，恰好另一个线程进入方法判断instance引用不为null，然后就将其返回使用，导致出错。
     */
    private Singleton7 (){}  
    public static Singleton7 getInstance() {  
    if (singleton == null) {  
        synchronized (Singleton7.class) {  
        if (singleton == null) {  
            singleton = new Singleton7();  
        }  
        }  
    }  
    return singleton;  
    }  
}
