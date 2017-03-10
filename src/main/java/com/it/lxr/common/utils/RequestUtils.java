package com.it.lxr.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class RequestUtils {
			public static Object getParameterAsDefInInt(HttpServletRequest request,String text,int s){
					int m = 0;
					String str = request.getParameter("text");
					if(StringUtils.isNotBlank(str)){
						m = Integer.parseInt(str);
						return m;
					}
					else{
						m = s;
						return m;
						}
			}
}
