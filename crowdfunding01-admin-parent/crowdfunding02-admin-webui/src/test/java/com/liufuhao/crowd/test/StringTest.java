package com.liufuhao.crowd.test;

import org.junit.Test;

import com.liufuhao.crowd.util.CrowdUtil;

public class StringTest {
	
	@Test
	public void testMD5() {
		String source = "123123";
		String encoded = CrowdUtil.md5(source);
		System.out.println(encoded);
	}

}
