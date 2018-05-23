package com.zed.xposed.demo.action.inter.impl;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zed.xposed.demo.action.inter.IDB;
import com.zed.xposed.demo.greedao.db.WxChatInvokeMsgDB;
import com.zed.xposed.demo.greedao.entity.WxChatInvokeMsg;
import com.zed.xposed.demo.log.LogUtils;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by zed on 2018/5/23.
 */
public class DBImpl extends IDB {

    public DBImpl(Context context, ClassLoader classLoader) {
        super(context, classLoader);
        LogUtils.i("数据库初始化操作完成");
    }

    /**
     * 直接hook sql达到获取撤回消息id的目的
     */
    @Override
    public void hookDB() {
        final Class<?> sQLiteDatabase = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase", mClassLoader);
        final Class<?> cancellationSignal = XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal", mClassLoader);
        if (sQLiteDatabase == null) return;
        XposedHelpers.findAndHookConstructor("com.tencent.wcdb.database.SQLiteProgram",
                mClassLoader,
                sQLiteDatabase,
                String.class,
                Object[].class,
                cancellationSignal,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        Object[] objArr = (Object[]) param.args[2];
                        String originalSql = param.args[1].toString();
                        //打印所有调用SQLiteProgram的sql 猜测TABLE=AdSnsInfo 为朋友圈消息
//                        LogUtils.e("hookDB", "sql -> " + param.args[1], "objArr:" + JSON.toJSONString(objArr));
                        if (objArr != null && originalSql.toUpperCase().startsWith("UPDATE MESSAGE")) {
                            for (Object obj : objArr) {
                                String sqlParam = obj.toString();//自己撤回10002 别人撤回10000
                                if (sqlParam.equals("10000")) {//别人撤回
                                    Object[] newObjArr = new Object[2];
                                    param.args[1] = "select * from message where type=? and msgId=?";
                                    param.args[2] = newObjArr;
                                    newObjArr[0] = 1;
                                    newObjArr[1] = objArr[objArr.length - 1];
                                    LogUtils.e("hookDB", "originalSql->" + originalSql, "newSql->" + param.args[1], "sqlParam->" + JSON.toJSONString(newObjArr));

                                    WxChatInvokeMsg msg = new WxChatInvokeMsg();
                                    msg.setMsgId(newObjArr[1].toString());
                                    WxChatInvokeMsgDB.insertData(mContext, msg);
                                }
                            }
                        }
                        return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
                    }
                });

    }
}
