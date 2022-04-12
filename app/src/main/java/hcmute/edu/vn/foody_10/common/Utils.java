package hcmute.edu.vn.foody_10.common;

import java.text.DecimalFormat;

public class Utils {
    public static String formatCurrenCy(float price) {
        String pattern = "###,###.##";
        return new DecimalFormat(pattern).format(price);
    }
}
