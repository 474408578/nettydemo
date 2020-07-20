package com.xschen.nettydemo.springbootlearn.qualifier;

import org.springframework.stereotype.Component;

/**
 * @author xschen
 */


@Component("barFormatter")
public class BarFormatter implements Formatter {
    @Override
    public String format() {
        return "bar";
    }
}
