package com.xschen.nettydemo.springbootlearn.qualifiertest;


import org.springframework.stereotype.Component;

/**
 * @author xschen
 */


@Component
public class FooFormatter implements Formatter {
    @Override
    public String format() {
        return "foo";
    }
}
