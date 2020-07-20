package com.xschen.nettydemo.springbootlearn.qualifiertest;

import org.springframework.stereotype.Component;

/**
 * @author xschen
 */


@Component
public class BarFormatter implements Formatter {
    @Override
    public String format() {
        return "bar";
    }
}
