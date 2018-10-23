package top.design.singleton;


/****
 * 懒汉式 单例模式
 * 线程不安全
 *  原理: 延迟加载策略 
 *  在 为空的时候才new 对象 返回  多线程环境下可能出现a线程 b 线程同时调用时判断为空 相互覆盖
 *  在开始加载类的时候对象为空
 *  
* @Description:TODO
* @author:   chen.chao
* @time:2018年10月22日 下午2:41:45
 */
public class Singleton1 {
	private static Singleton1 instance;

	private Singleton1() {
		super();
	}
	public static Singleton1 getInstance(){
		if (instance==null) {
			instance = new Singleton1();
		}
		return instance;
	}
	
}
