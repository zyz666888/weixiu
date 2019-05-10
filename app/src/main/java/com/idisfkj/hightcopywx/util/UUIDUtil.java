package com.idisfkj.hightcopywx.util;

import java.util.UUID;

public class UUIDUtil {

	public  static  String getUUID(){
		String uuId = UUID.randomUUID().toString();
		return uuId.replaceAll("-", "");
		
	}
}
