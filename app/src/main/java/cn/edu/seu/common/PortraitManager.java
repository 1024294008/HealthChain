package cn.edu.seu.common;

import cn.edu.seu.R;

public class PortraitManager {
    private static Integer[] portraitSrc = new Integer[]{
            R.drawable.portrait0,
            R.drawable.portrait1,
            R.drawable.portrait2,
            R.drawable.portrait3
    };

    public static Integer getPortraitSrc(String portrait){
        int p = Integer.parseInt(portrait);
        if(p < 0 || p > portraitSrc.length)
            p = 0;
        return portraitSrc[p];

    }

    public static Integer getPortrait(){
        return (int)(Math.random() * portraitSrc.length);
    }
}
