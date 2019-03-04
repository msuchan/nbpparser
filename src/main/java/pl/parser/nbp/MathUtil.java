/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author mariusz
 */
public class MathUtil {

    private static BigDecimal sqrt(BigDecimal in, int scale) {
        BigDecimal sqrt = new BigDecimal(1);
        sqrt.setScale(scale + 3, RoundingMode.FLOOR);
        BigDecimal store = new BigDecimal(in.toString());
        boolean first = true;
        do {
            if (!first) {
                store = new BigDecimal(sqrt.toString());
            } else {
                first = false;
            }
            store.setScale(scale + 3, RoundingMode.FLOOR);
            sqrt = in.divide(store, scale + 3, RoundingMode.FLOOR).add(store).divide(
                    BigDecimal.valueOf(2), scale + 3, RoundingMode.FLOOR);
        } while (!store.equals(sqrt));
        return sqrt.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateStandardDevation(List<BigDecimal> list) {

        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        BigDecimal avgList = getAvgAritmetic(list);
        BigDecimal l = new BigDecimal(list.size());

        if (list == null || list.size() < 2) {
            return result;
        }

        for (BigDecimal temp : list) {
            BigDecimal r = (temp.subtract(avgList));
            result = result.add(r.pow(2));
        }
        result = result.divide(l, RoundingMode.HALF_UP);
        result = sqrt(result, 4);
        return result;
    }

    public static BigDecimal getAvgAritmetic(List<BigDecimal> list) {
        BigDecimal avg = new BigDecimal("0");
        BigDecimal all = new BigDecimal(list.size());

        if (list.isEmpty()) {
            return avg;
        }

        for (BigDecimal q : list) {
            avg = avg.add(q);
        }
        avg = avg.divide(all, RoundingMode.HALF_UP);
        avg = avg.setScale(4, RoundingMode.HALF_UP);
        return avg;
    }
}
