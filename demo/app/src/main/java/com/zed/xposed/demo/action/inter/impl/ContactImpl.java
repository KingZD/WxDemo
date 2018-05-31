package com.zed.xposed.demo.action.inter.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.zed.xposed.demo.action.inter.IContact;
import com.zed.xposed.demo.greedao.db.WxContactDB;
import com.zed.xposed.demo.greedao.entity.WxContact;
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

    /**
     * 直接取得联系人列表数据
     */
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
                                        int count = adapter.getCount();
                                        for (int i = 0; i < count; i++) {
                                            WxContact contact = JSON.parseObject(JSON.toJSONString(adapter.getItem(i)), WxContact.class);
                                            WxContact ct = WxContactDB.queryByUsername(mContext, contact.getField_username());
                                            if (ct == null) {
                                                LogUtils.i(JSON.toJSONString(contact));
                                                WxContactDB.insertData(mContext, contact);
                                            }
                                        }
//                                        XposedHelpers.findAndHookMethod(adapter.getClass(),
//                                                "getView",
//                                                int.class,
//                                                View.class,
//                                                ViewGroup.class,
//                                                new XC_MethodHook() {
//                                                    @Override
//                                                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                                        super.beforeHookedMethod(param);
//                                                        int position = (int) param.args[0];
//                                                        final View view = (View) param.args[1];
//                                                        final ViewGroup viewGroup = (ViewGroup) param.args[2];
//                                                        LogUtils.i(position, view, viewGroup, JSON.toJSONString(adapter.getItem(position)), adapter.getItem(position).toString());
//                                                    }
//                                                });
                                    }
                                });
                    }
                });
    }
}
