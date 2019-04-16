package com.example.easylearnhome.utils;

import com.example.easylearnhome.R;

public class UheadUtils {
    //param: String uhead:数据库中保存的内容，类型：字符串，例如："1","2",等...
    //获取用户的头像资源id
    public static int getUhead_id(String uhead){
        try{
            int i = Integer.parseInt(uhead);
            switch (i){
                case 1:
                    return R.drawable.avater1;
                case 2:
                    return R.drawable.avater2;
                case 3:
                    return R.drawable.avater3;
                case 4:
                    return R.drawable.avater4;
                case 5:
                    return R.drawable.avater5;
                case 6:
                    return R.drawable.avater6;
                case 7:
                    return R.drawable.avater7;
                case 8:
                    return R.drawable.avater8;
                case 9:
                    return R.drawable.avater9;
                case 10:
                    return R.drawable.avater10;
                case 11:
                    return R.drawable.avater11;
                case 12:
                    return R.drawable.avater12;
            }
        }catch (Exception e){
        }
        return R.drawable.avater1;
    }
}
