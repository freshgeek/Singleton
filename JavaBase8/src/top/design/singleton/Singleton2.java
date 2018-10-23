package top.design.singleton;

/***
 * 单例2
 * 懒汉式 线程安全
 * 但 加锁粒度太大 基本上很多情况下不需要同步 所以不要用
 * 
* @Description:TODO
* @author:   chen.chao
* 
* @time:2018年10月22日 下午3:25:43
* 
 */
public class Singleton2 {
	private static Singleton2 instance;  
    private Singleton2 (){}
    public static synchronized Singleton2 getInstance() {  
	    if (instance == null) {  
	        instance = new Singleton2();  
	    }  
    return instance;  
    } 
    
}
