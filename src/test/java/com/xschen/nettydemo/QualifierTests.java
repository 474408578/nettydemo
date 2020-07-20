package com.xschen.nettydemo;

import com.xschen.nettydemo.springbootlearn.qualifier.Formatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xschen
 */


@RunWith(SpringRunner.class)
@SpringBootTest// 创建Spring的上下文ApplicationContext，保证测试在上下文环境里运行
@ContextConfiguration(classes = NettydemoApplication.class)
public class QualifierTests {


    /**
     * 不加@Qualifier指定是哪个注解时，会报org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type
     * 具体文章可参考https://juejin.im/post/5e0adfaa6fb9a0482806d826
     */
    @Autowired
    @Qualifier("fooFormatter")
    private Formatter formatter;

    @Test
    public void formatterTests() {
        System.out.println(formatter);
    }
}
