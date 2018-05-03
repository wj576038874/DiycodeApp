package com.wenjie.diycode;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode
 * @FileName: com.wenjie.diycode.BaseEvent.java
 * @Author: wenjie
 * @Date: 2017-08-09 15:16
 * @Description:
 * @Version:
 */
public class BaseEvent<T> {
    private T t;

    public BaseEvent(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
