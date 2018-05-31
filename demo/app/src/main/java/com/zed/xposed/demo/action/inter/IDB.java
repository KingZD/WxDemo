package com.zed.xposed.demo.action.inter;

import android.content.Context;

import com.zed.xposed.demo.base.IBase;

/**
 * Created by zed on 2018/5/23.
 */
public abstract class IDB implements IBase {
    protected Context mContext;
    protected ClassLoader mClassLoader;

    public IDB(Context context, ClassLoader classLoader) {
        this.mContext = context;
        this.mClassLoader = classLoader;
    }
    //数据库
    public abstract void hookDB();
//    public abstract void hookWxQueryWithFactory();
//    public abstract void hookWxRawQueryWithFactory();
//    public abstract void hookSQLiteDatabase();
}
