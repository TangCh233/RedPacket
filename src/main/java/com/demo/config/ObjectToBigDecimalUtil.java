package com.demo.config;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Description: Object转化为BigDecimal
 * @Author: chenfeixiang
 * @Date: Created in 13:50 2018/7/8
 */
public class ObjectToBigDecimalUtil {

    public BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }
}
