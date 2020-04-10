package cn.edu.seu.common;

public class CoinTransManager {
    private static String addZeros(String eth) {
        StringBuffer result = new StringBuffer(eth);
        if(result.toString().indexOf('.') == -1){
            result.append(".");
        }
        result.insert(0, "0000000000000000");
        result.insert(result.toString().length(), "0000000000000000");
        return result.toString();
    }

    private static String rmZeros(String eth){
        StringBuffer result = new StringBuffer(eth);
        Integer dotIdx = result.toString().indexOf('.');
        Integer len = result.toString().length();
        Integer start = 0;
        Integer end = len;
        for(int i = 0; i < dotIdx - 1; i++)
            if(eth.charAt(i) == '0'){
                start ++;
            } else {
                break;
            }
        for(int i = len - 1; i > dotIdx + 1; i--)
            if(eth.charAt(i) == '0'){
                end --;
            } else {
                break;
            }
        return result.substring(start, end).toString();
    }

    public static String transToCoin(String eth){
        StringBuffer result = new StringBuffer(addZeros(eth));
        Integer dotIdx = result.toString().indexOf('.');
        result.deleteCharAt(dotIdx);

        dotIdx -= 15;
        result.insert(dotIdx, ".");

        return rmZeros(result.toString());
    }

    public static String transToEth(String coin){
        StringBuffer result = new StringBuffer(addZeros(coin));
        Integer dotIdx = result.toString().indexOf('.');
        result.deleteCharAt(dotIdx);

        dotIdx += 15;
        result.insert(dotIdx, ".");

        return rmZeros(result.toString()).replace(".0", "");
    }
}
