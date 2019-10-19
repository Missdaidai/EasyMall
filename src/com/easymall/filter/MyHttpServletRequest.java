package com.easymall.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//    装饰类
class MyHttpServletRequest extends HttpServletRequestWrapper {
    public String encode = null;
    public MyHttpServletRequest(HttpServletRequest request, String encode) {
        super(request);
        this.encode=encode;
    }
    //        重写的方法
    @Override
    public Map<String, String[]> getParameterMap() {
//        获取原有map的值，将值依次做出乱码处理,处理后将值放入rmap
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> rmap = new HashMap<>();
//        将值依次做出乱码处理,
        try {
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
//           遍历数组将值依次取出作出乱码处理，放入一个新数组
                String[] rvalues = new String[values.length];
                for (int i = 0; i < values.length; i++) {
                    rvalues[i]=new String(values[i].getBytes("iso8859-1"), "utf-8");

                }
//               处理后将值放入rmap
                rmap.put(key,rvalues);
            }

            return rmap;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//        循环结束全部乱码处理完毕
//        return super.getParameterMap();
    }

    @Override
    public String[] getParameterValues(String name) {
        Map<String,String[]> map = getParameterMap();
        return map.get(name);
//        return super.getParameterValues(name);

    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
//        将数组中第一个元素作为方法返回值使用
        return values==null?null:values[0];
//        return super.getParameter(name);
    }
}