package com.cisco.wt.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.britesnow.snow.testsupport.SnowTestSupport;

public class BaseWebTest extends SnowTestSupport {
    @BeforeClass
    public static void initTestClass() throws Exception {
        SnowTestSupport.initWebApplication("src/main/webapp");
    }
    
    @Test
    public void nopTest(){
      System.out.println("nop test");
    }
    
    
}
