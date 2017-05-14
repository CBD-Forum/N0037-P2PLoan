package cn.edu.fudan.bclab.hackathon.util;

import java.text.DecimalFormat;

/**
 * Created by lomo on 2017/5/11.
 */
public class DoubleFormat {
    public Double format(Double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(num));
    }

}
