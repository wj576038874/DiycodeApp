package com.wenjie.diycode.utils;

import android.view.Menu;

import java.lang.reflect.Field;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.utils
 * @FileName: com.wenjie.diycode.utils.MenuUtil.java
 * @Author: wenjie
 * @Date: 2017-09-27 17:08
 * @Description:
 * @Version:
 */
public class MenuUtil {
    /**
     * *显示溢出菜单图标
     **/
    public static void setIconVisible(Menu menu, boolean visable) {
        Field field;
        try {
            field = menu.getClass().getDeclaredField("mOptionalIconsVisible");
            field.setAccessible(true);
            field.set(menu, visable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
