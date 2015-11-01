


import java.net.URL;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.testInterfaceImpl.TestInterfaceImpl;

public class TestSpring {
	@Test
	public void test() {
		try {
			URL xmlpath = this.getClass().getClassLoader().getResource("spring.xml"); 
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
			// 从Spring的IOC容器中获取bean对象
			TestInterfaceImpl userService = (TestInterfaceImpl) ac.getBean("testInterfaceImpl");
			// 执行测试方法
			userService.test();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
