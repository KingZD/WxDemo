package com.zed.xposed.demo.action.inter;

import android.content.Context;

import com.zed.xposed.demo.base.IBase;

/**
 * Created by zed on 2018/5/23.
 */
public abstract class IMoment implements IBase {
    protected Context mContext;
    protected ClassLoader mClassLoader;

    public IMoment(Context mContext, ClassLoader mClassLoader) {
        this.mContext = mContext;
        this.mClassLoader = mClassLoader;
    }
    //朋友圈
    public abstract void hookWxMoments();
}
