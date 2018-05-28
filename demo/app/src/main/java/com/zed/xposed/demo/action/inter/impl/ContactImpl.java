package com.zed.xposed.demo.action.inter.impl;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zed.xposed.demo.action.inter.IContact;
import com.zed.xposed.demo.log.LogUtils;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by zed on 2018/5/28.
 */
public class ContactImpl extends IContact {

    public ContactImpl(Context context, ClassLoader classLoader) {
        super(context, classLoader);
    }

    @Override
    public void hookContact() {
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.contact.AddressUI.a",
                mClassLoader,
                "coR",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Field oiy = XposedHelpers.findField(param.thisObject.getClass(), "oiy");
                        oiy.setAccessible(true);
                        LogUtils.i(oiy);
                        ListView oiyLv = (ListView) oiy.get(param.thisObject);
                        Class<?> mlvSuperClass = oiyLv.getClass().getSuperclass();
                        LogUtils.i(oiyLv);
                        XposedHelpers.findAndHookMethod(mlvSuperClass,
                                "setAdapter",
                                ListAdapter.class,
                                new XC_MethodHook() {
                                    @Override
                                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                        super.beforeHookedMethod(param);
                                        final ListAdapter adapter = (ListAdapter) param.args[0];
                                        LogUtils.i(adapter.toString());
                                    }
                                });
                    }
                });
    }
}
