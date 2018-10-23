package top.design.singleton;


/***
 * 单例模式3
 * 
 *  饿汉式 直接在类加载时赋值给单例
 *  
 *  简单 避免同步问题 但是不能确定导致类加载原因
 *  
* @Description:TODO
* @author:   chen.chao
* @time:2018年10月22日 下午4:11:24
 */
public class Singleton3 {

	private static Singleton3 instance = new Singleton3();

	private Singleton3() {
	}

	public static Singleton3 getInstance() {
		return instance;
	}

}
