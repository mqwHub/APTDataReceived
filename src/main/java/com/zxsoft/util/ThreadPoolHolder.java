package com.zxsoft.util;

import com.zxsoft.server.BusinessThreadPool;

public class ThreadPoolHolder {

	private static BusinessThreadPool mythreadPool;
	
	public static BusinessThreadPool getBusinessThreadPool() {
		if (mythreadPool == null) {
			return new BusinessThreadPool(50, 10000);
		}
		return mythreadPool;
	}
}
