package common; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.util.Map;

/** 
* ServletContext Tester. 
* 
* @author weiwei 
* @since 08/22/2018
* @version 1.0 
*/ 
public class ServletContextTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 


/** 
* 
* Method: init() 
* 
*/ 
@Test
public void testInit() throws Exception {
    System.out.println(ServletContext.port);
    Map m = ServletContext.typeMap;
    System.out.println(m.get("html"));

} 

} 
