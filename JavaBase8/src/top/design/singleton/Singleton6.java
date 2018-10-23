package top.design.singleton;

import java.util.HashMap;
import java.util.Map;

/***
 * 
这种方式是Effective Java作者Josh Bloch 提倡的方式
它不仅能避免多线程同步问题
而且还能防止反序列化重新创建新的对象

其主要使用了枚举的三个特性，自由序列化，线程安全，保证单例
首先，我们都知道enum是由class实现的，
换言之，enum可以实现很多class的内容，
包括可以有member和member function，
这也是我们可以用enum作为一个类来实现单例的基础。
由于enum是通过继承了Enum类实现的，
enum结构不能够作为子类继承其他类，
但是可以用来实现接口。此外，enum类也不能够被继承，
在反编译中，我们会发现该类是final的。
其次，enum有且仅有private的构造器，防止外部的额外构造，这恰好和单例模式吻合，
也为保证单例性做了一个铺垫。
这里展开说下这个private构造器，如果我们不去手写构造器，则会有一个默认的空参构造器，
我们也可以通过给枚举变量参量来实现类的初始化。

这里写一个sessionFactry

* @Description:TODO

* @author:   chen.chao

* @time:2018年10月23日 下午3:18:00
 */
public enum Singleton6 {
	instance;
	private Map<Object, Object> sessionFactory;//由于没有导包,假装他是SessionFactory 
	Singleton6(){
		setSessionFactory(new HashMap<>());
		System.out.println("初始化...");
	}
	public Map<Object, Object> getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(Map<Object, Object> sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
