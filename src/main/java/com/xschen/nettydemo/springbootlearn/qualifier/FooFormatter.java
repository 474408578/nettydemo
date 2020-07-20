package com.xschen.nettydemo.springbootlearn.qualifier;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author xschen
 */


@Component(/*"fooFormatter"*/)
public class FooFormatter implements Formatter {
    @Override
    @Qualifier
    public String format() {
        return "foo";
    }
}
