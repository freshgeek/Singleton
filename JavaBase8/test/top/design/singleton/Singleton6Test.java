package top.design.singleton;

import org.junit.Test;


 
public class Singleton6Test {

	@Test
	public void testInit(){
		/**
		 * testInit....
		 * 初始化...
		 * 123123
		 */
		System.out.println("testInit....");
		Singleton6 singleton6 =Singleton6.instance;
		singleton6.getSessionFactory().put("url", "123123");
		System.out.println(singleton6.getSessionFactory().get("url"));
	}
	@Test
	public void testInit2(){
		/**
testInit....
初始化...
123123
123123
		 */
		System.out.println("testInit....");
		Singleton6 singleton6 =Singleton6.instance;
		singleton6.getSessionFactory().put("url", "123123");
		System.out.println(singleton6.getSessionFactory().get("url"));
		Singleton6 singleton66 =Singleton6.instance;
		singleton66.getSessionFactory().put("jdbc", "123123");
		System.out.println(singleton66.getSessionFactory().get("jdbc"));
	}
	
}
