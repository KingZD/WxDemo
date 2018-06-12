package com.zed.xposed.demo.action.inter;

import android.content.Context;

import com.zed.xposed.demo.base.IBase;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zed on 2018/5/23.
 */
public abstract class IChat implements IBase {
    protected Context mContext;
    protected ClassLoader mClassLoader;

    public IChat(Context mContext, ClassLoader mClassLoader) {
        this.mContext = mContext;
        this.mClassLoader = mClassLoader;
    }

    //微信聊天界面防撤回
    public abstract void hookWxChatUIMM();
    public abstract void autoRepeat();
}
