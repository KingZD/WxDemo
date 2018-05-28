package com.zed.xposed.demo.action.inter;

import android.content.Context;

import com.zed.xposed.demo.base.IBase;

/**
 * Created by zed on 2018/5/28.
 */
public abstract class IContact implements IBase{
    protected Context mContext;
    protected ClassLoader mClassLoader;
    public IContact(Context context, ClassLoader classLoader) {
        this.mContext = context;
        this.mClassLoader = classLoader;
    }
    //数据库
    public abstract void hookContact();
}
