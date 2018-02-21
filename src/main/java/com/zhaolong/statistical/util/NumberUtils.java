package com.zhaolong.statistical.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {

    public static String percentage(int a,int b){
        if(b==0){
            return "0";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) a / (float) b * 100);

        return result+"%";
    }

    public static double double2(double d){
        BigDecimal b   =   new   BigDecimal(d);
        return  b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
