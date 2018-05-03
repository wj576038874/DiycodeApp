package com.wenjie.diycode.mvp.model;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model
 * @FileName: com.wenjie.diycode.mvp.model.BaseModel.java
 * @Author: wenjie
 * @Date: 2017-08-04 12:36
 * @Description:
 * @Version:
 */
public class BaseModel {

    protected <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
