package com.service.testInterfaceImpl;

import org.springframework.stereotype.Service;

import com.service.testInterface.TestInterface;
@Service("testInterfaceImpl")
public class TestInterfaceImpl implements TestInterface{

	@Override
	public void test() {
		 System.out.println("Hello World!");
	}

}
