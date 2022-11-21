package com.lyj.ehcache;

import java.io.InputStream;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 *  测试ehcache缓存
 */
public class TestEH {
    public static void main(String[] args) {
        //
        InputStream input = TestEH.class.getClassLoader().getResourceAsStream("ehcache.xml");
        // 获取缓存管理器对象
        CacheManager cacheManager = new CacheManager(input);
        Cache cache = cacheManager.getCache("HelloWorldCache");
        Element element = new Element("name","zhangsan");
        cache.put(element);
        Element element1 = cache.get("name");
        System.out.println("缓存中的数据element1 = " + element1.getObjectValue());
    }
}
