package com.zed.xposed.demo.action.inter.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteCursor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zed.xposed.demo.action.inter.IDB;
import com.zed.xposed.demo.greedao.db.WxChatInvokeMsgDB;
import com.zed.xposed.demo.greedao.entity.WxChatInvokeMsg;
import com.zed.xposed.demo.greedao.entity.WxContact;
import com.zed.xposed.demo.greedao.util.WxContactDataUtil;
import com.zed.xposed.demo.log.LogUtils;
import com.zed.xposed.demo.util.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
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
                        String sql = param.args[1].toString();
                        //打印所有调用SQLiteProgram的sql 猜测TABLE=AdSnsInfo 为朋友圈消息
//                        LogUtils.e("hookDB", "sql -> " + sql, "objArr:" + JSON.toJSONString(objArr));
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
                        } else if (objArr != null && sql.toUpperCase().startsWith("INSERT INTO MESSAGE")) {

                            LogUtils.e(param.args);
//                            Class<?> aClass = XposedHelpers.findClass("com.tencent.mm.ui.chatting.o", mClassLoader);
//                            Method fz = XposedHelpers.findMethodExact(aClass, "FZ", String.class);
//                            fz.invoke(aClass.newInstance(),"haha");
//                            XposedHelpers.findAndHookMethod(aClass, "FZ", String.class, new XC_MethodHook() {
//                                @Override
//                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                    super.beforeHookedMethod(param);
//                                    LogUtils.i(param.args);
//                                }
//                            });

                        }
                        return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
                    }
                });

    }

    /**
     * hook 微信 com.tencent.wcdb.database.SQLiteDatabase 类里面的
     * public final Cursor queryWithFactory(CursorFactory cursorFactory,
     * **************************************boolean z,
     * **************************************String str,
     * **************************************String[] strArr,
     * **************************************String str2,
     * **************************************String[] strArr2,
     * **************************************String str3,
     * **************************************String str4,
     * **************************************String str5,
     * **************************************String str6,
     * **************************************CancellationSignal)
     * 方法
     */
    public void hookWxQueryWithFactory() {
        Class<?> cancellationSignal = XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal", mClassLoader);
        Class<?> cursorFactory = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase.CursorFactory", mClassLoader);
        final Class<?> mSQLiteDatabase = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase", mClassLoader);
        final Method queryWithFactory = XposedHelpers.findMethodExact(mSQLiteDatabase,
                "queryWithFactory",
                cursorFactory,
                boolean.class,
                String.class,
                String[].class,
                String.class,
                String[].class,
                String.class,
                String.class,
                String.class,
                String.class,
                cancellationSignal);
        XposedBridge.hookMethod(queryWithFactory, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                Object o = XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
//                Cursor cursor = (Cursor) o;
//                CursorWrapper wrapper = new CursorWrapper(cursor);
//                while (wrapper.moveToNext()) {
//                    WxContact contact = new WxContact();
//                    contact.setField_username(wrapper.getString(0));
//                    contact.setField_nickname(wrapper.getString(1));
//                    contact.setField_alias(wrapper.getString(2));
//                    contact.setField_conRemark(wrapper.getString(3));
//                    contact.setField_verifyFlag(wrapper.getInt(4));
//                    contact.setField_showHead(wrapper.getInt(5));
//                    contact.setField_weiboFlag(wrapper.getInt(6));
//                    contact.setfNU(wrapper.getLong(7));
//                    contact.setField_deleteFlag(wrapper.getInt(8));
//                    contact.setField_lvbuff(wrapper.getBlob(9));
//                    contact.setField_descWordingId(wrapper.getString(10));
//                    contact.setField_openImAppid(wrapper.getString(11));
//
//                    if (wrapper.getColumnCount() >= 14) {
//                        contact.setField_descWording(wrapper.getString(12));
//                        contact.setField_descWordingQuanpin(wrapper.getString(13));
//                    }
//                    WxContactDataUtil.ckU(contact);
//                    LogUtils.i(JSONObject.toJSONString(contact));
//                }
                return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
            }

        });

    }


    /**
     * hook 微信 com.tencent.wcdb.database.SQLiteDatabase 类里面的
     * public final Cursor rawQueryWithFactory(CursorFactory cursorFactory,
     * ***************************************String str,
     * ***************************************String[] strArr,
     * ***************************************String str2,
     * ***************************************CancellationSignal cancellationSignal)
     * 方法
     */
    public void hookWxRawQueryWithFactory() {
        Class<?> cancellationSignal = XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal", mClassLoader);
        Class<?> cursorFactory = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase.CursorFactory", mClassLoader);
        final Class<?> mSQLiteDatabase = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase", mClassLoader);
        Method rawQueryWithFactory = XposedHelpers.findMethodExact(mSQLiteDatabase,
                "rawQueryWithFactory",
                cursorFactory,
                String.class,
                String[].class,
                String.class,
                cancellationSignal);
        XposedBridge.hookMethod(rawQueryWithFactory, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                String sql = param.args[1] == null ? "" : param.args[1].toString();
                Object o = XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
                if (param.args != null && sql.contains("rcontact")) {
                    Cursor cursor = (Cursor) o;
                    LogUtils.i(cursor.getExtras());
//                    CursorWrapper wrapper = new CursorWrapper(cursor);
//                    while (wrapper.moveToNext()) {
//                        WxContact contact = new WxContact();
//                        contact.setField_username(wrapper.getString(0));
//                        contact.setField_nickname(wrapper.getString(1));
//                        contact.setField_alias(wrapper.getString(2));
//                        contact.setField_conRemark(wrapper.getString(3));
//                        contact.setField_verifyFlag(wrapper.getInt(4));
//                        contact.setField_showHead(wrapper.getInt(5));
//                        contact.setField_weiboFlag(wrapper.getInt(6));
//                        contact.setfNU(wrapper.getLong(7));
//                        contact.setField_deleteFlag(wrapper.getInt(8));
//                        contact.setField_lvbuff(wrapper.getBlob(9));
//                        contact.setField_descWordingId(wrapper.getString(10));
//                        contact.setField_openImAppid(wrapper.getString(11));
//
//                        if (wrapper.getColumnCount() >= 14) {
//                            contact.setField_descWording(wrapper.getString(12));
//                            contact.setField_descWordingQuanpin(wrapper.getString(13));
//                        }
//                        WxContactDataUtil.ckU(contact);
//                        LogUtils.i(JSONObject.toJSONString(contact));
//                    }
//                    LogUtils.v(param.method);
//                    LogUtils.v(param.args);
                }
                return o;
            }
        });
    }

    public void hookSQLiteDatabase() {
        final Class<?> mSQLiteDatabase = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase", mClassLoader);
        final Class mSQLiteOpenHelper = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteOpenHelper", mClassLoader);
        LogUtils.i(mSQLiteOpenHelper);
        Method getWritableDatabase = XposedHelpers.findMethodExact(mSQLiteOpenHelper, "getWritableDatabase");
        LogUtils.i(getWritableDatabase);
        XposedBridge.hookMethod(getWritableDatabase, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtils.i(param.getResult(), param.method, param.thisObject);
                Object mDatabase = param.getResult();
                LogUtils.i(mDatabase);
                Method rawQuery = XposedHelpers.findMethodExact(mSQLiteDatabase, "rawQuery", String.class, String[].class);
                LogUtils.i(rawQuery);

//                Object o = rawQuery.invoke(mDatabase, "select username ,nickname ,alias,conRemark,verifyFlag,showHead,weiboFlag,rowid ,deleteFlag,lvbuff,descWordingId,openImAppid from rcontact  where (type & 1!=0) and type & 32=0  and type & 8 =0  AND type & 64 !=0  AND username != 'tmessage' AND username != 'officialaccounts' AND username != 'helper_entry' AND username != 'blogapp' order by case when verifyFlag & 8 != 0 then 0 else 1 end , showHead asc,  case when length(conRemarkPYFull) > 0 then upper(conRemarkPYFull)  else upper(quanPin) end asc,  case when length(conRemark) > 0 then upper(conRemark)  else upper(quanPin) end asc,  upper(quanPin) asc,  upper(nickname) asc,  upper(username) asc", null);
//                LogUtils.i(o);
//                Cursor cursor = (Cursor) o;
//                while (cursor.moveToNext()) {
//                    WxContact contact = new WxContact();
//                    contact.setField_username(cursor.getString(0));
//                    contact.setField_nickname(cursor.getString(1));
//                    contact.setField_alias(cursor.getString(2));
//                    contact.setField_conRemark(cursor.getString(3));
//                    contact.setField_verifyFlag(cursor.getInt(4));
//                    contact.setField_showHead(cursor.getInt(5));
//                    contact.setField_weiboFlag(cursor.getInt(6));
//                    contact.setfNU(cursor.getLong(7));
//                    contact.setField_deleteFlag(cursor.getInt(8));
//                    contact.setField_lvbuff(cursor.getBlob(9));
//                    contact.setField_descWordingId(cursor.getString(10));
//                    contact.setField_openImAppid(cursor.getString(11));
//
//                    if (cursor.getColumnCount() >= 14) {
//                        contact.setField_descWording(cursor.getString(12));
//                        contact.setField_descWordingQuanpin(cursor.getString(13));
//                    }
//                    WxContactDataUtil.ckU(contact);
//                    LogUtils.i(JSONObject.toJSONString(contact));
//                }
            }
        });
    }
}
