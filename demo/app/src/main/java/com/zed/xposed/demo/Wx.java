package com.zed.xposed.demo;

import android.app.Application;
import android.content.Context;

import com.zed.xposed.demo.action.inter.IChat;
import com.zed.xposed.demo.action.inter.IContact;
import com.zed.xposed.demo.action.inter.impl.ChatImpl;
import com.zed.xposed.demo.action.inter.IDB;
import com.zed.xposed.demo.action.inter.impl.ContactImpl;
import com.zed.xposed.demo.action.inter.impl.DBImpl;
import com.zed.xposed.demo.action.inter.IMoment;
import com.zed.xposed.demo.action.inter.impl.MomentImpl;
import com.zed.xposed.demo.log.LogUtils;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zed on 2018/5/23.
 * 这里只关心初始化业务操作
 */
public abstract class Wx implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (lpparam.packageName.contains("com.tencent.mm")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Context context = (Context) param.args[0];
                    LogUtils.init(context);
                    LogUtils.i("初始化操作完成", param.thisObject);
                    initDB(new DBImpl(context, lpparam.classLoader));
                    initChat(new ChatImpl(context, lpparam.classLoader));
                    initMoment(new MomentImpl(context, lpparam.classLoader));
                    initContact(new ContactImpl(context, lpparam.classLoader));
                }
            });
        }
    }

    //初始化聊天模块
    abstract void initChat(IChat iChat);

    //初始化朋友圈模块
    abstract void initMoment(IMoment iMoment);

    //初始化数据库模块
    abstract void initDB(IDB idb);

    abstract void initContact(IContact iContact);
}
