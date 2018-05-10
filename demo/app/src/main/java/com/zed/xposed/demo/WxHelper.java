package com.zed.xposed.demo;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zed.xposed.demo.greedao.db.WxChatInvokeMsgDB;
import com.zed.xposed.demo.greedao.entity.WxChatInvokeMsg;
import com.zed.xposed.demo.greedao.util.ShapeUtil;
import com.zed.xposed.demo.log.LogUtils;
import com.zed.xposed.demo.model.cg;
import com.zed.xposed.demo.model.t;
import com.zed.xposed.demo.util.ViewUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zed on 2018/4/11.
 */

public class WxHelper implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (lpparam.packageName.contains("com.tencent.mm")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    final Context applicationContext = (Context) param.args[0];
                    LogUtils.init(applicationContext);
//                    addActionBarMenu(applicationContext, lpparam.classLoader);
//                    addActionBarMenu(applicationContext);
//                    addTabMenu(applicationContext, lpparam.classLoader);
//                    hookConversationWithAppBrandListView(applicationContext, lpparam.classLoader);
//                    replaceData(applicationContext, lpparam.classLoader);
//                    hookWxItemLongClick(applicationContext, lpparam.classLoader);
                    hookWxChatUIMM(applicationContext, lpparam.classLoader);
//                    hookWxChatUIWith(applicationContext, lpparam.classLoader);
//                    hookWxChatUI(applicationContext, lpparam.classLoader);
//                    hookWxChatData(applicationContext, lpparam.classLoader);
                    hookDB(applicationContext, lpparam.classLoader);
//                    test(applicationContext, lpparam.classLoader);
                }
            });
        }
    }

    private void test(final Context applicationContext, ClassLoader classLoader) throws Exception {
        final Class<?> aVar = XposedHelpers.findClassIfExists("com.tencent.mm.ui.chatting.viewitems.b.a", classLoader);
        Class<?> chattingUI$a = XposedHelpers.findClassIfExists("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader);
        Class<?> azVar = XposedHelpers.findClassIfExists("com.tencent.mm.storage.az", classLoader);
        final Method a = XposedHelpers.findMethodExact("com.tencent.mm.ui.chatting.viewitems.q", classLoader,
                "a",
                aVar,
                int.class,
                chattingUI$a,
                azVar,
                String.class);

        XposedBridge.hookMethod(a, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                LogUtils.i(aVar, a);
            }
        });
//        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.viewitems.q", classLoader,
//                "a",
//                aVar,
//                int.class,
//                chattingUI$a,
//                azVar,
//                String.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        LogUtils.i(param.args[1],param.args[4]);
//                    }
//
//                });
    }


    /**
     * 测试添加actionBar菜单项
     *
     * @param applicationContext
     */
    private void addActionBarMenu(final Context applicationContext) throws Exception {
        XposedBridge.log("Demo: addActionBarMenu");
        ClassLoader loader = applicationContext.getClassLoader();
        Class<?> aClass = loader.loadClass("com.tencent.mm.ui.LauncherUI");
        if (aClass != null) {
            XposedHelpers.findAndHookMethod(aClass,
                    "onCreateOptionsMenu",
                    Menu.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Menu menu = (Menu) param.args[0];
                            menu.add(0, 3, 0, "hahaha");
                            menu.add(0, 4, 0, "hehehe");
                            menu.add(0, 5, 0, "xixixi");
                            int size = menu.size();
                            for (int i = 0; i < size; i++) {
                                menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        Toast.makeText(applicationContext, item.getTitle(), Toast.LENGTH_SHORT).show();
                                        return false;
                                    }
                                });
                            }
                        }

                    });
        }
    }

    /**
     * 测试添加actionBar菜单项
     *
     * @param applicationContext
     */
    private void addActionBarMenu(final Context applicationContext, ClassLoader classLoader) {
        XposedBridge.log("Demo: addActionBarMenu");
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI",
                classLoader,
                "onCreateOptionsMenu",
                Menu.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Menu menu = (Menu) param.args[0];
                        menu.add(0, 3, 0, "hahaha");
                        menu.add(0, 4, 0, "hehehe");
                        menu.add(0, 5, 0, "xixixi");
                        int size = menu.size();
                        for (int i = 0; i < size; i++) {
                            menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    Toast.makeText(applicationContext, item.getTitle(), Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            });
                        }
                    }

                });
    }

    /**
     * 给底部按钮添加点击事件
     *
     * @param applicationContext
     * @param classLoader
     */
    private void addTabMenu(final Context applicationContext, ClassLoader classLoader) {
        XposedBridge.log("Demo: addTabMenu");
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUIBottomTabView$1",
                classLoader,
                "onClick",
                View.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("Demo: addTabMenu->" + param.args[0].toString());
                        Log.e("Demo: addTabMenu->", param.args[0].toString());
                        Toast.makeText(applicationContext, "ssssss", Toast.LENGTH_SHORT).show();
                        RelativeLayout arg = (RelativeLayout) param.args[0];
                    }
                });
    }

    /**
     * 观察聊天界面的时候发现了 ConversationWithAppBrandListView 组建
     * 得到了 item data为com.tencent.mm.storage.ae
     * adapter address-> com.tencent.mm.ui.conversation.g
     *
     * @param applicationContext
     * @param classLoader
     */
    private void hookConversationWithAppBrandListView(final Context applicationContext, final ClassLoader classLoader) {
        XposedBridge.log("Demo: hookListView");
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.conversation.ConversationWithAppBrandListView",
                classLoader,
                "setAdapter",
                ListAdapter.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ListAdapter adapter = (ListAdapter) param.args[0];
                        XposedBridge.log("Demo: hookListView adapter address-> " + adapter.toString());
                        Log.e("Demo: hookListView", " adapter address-> " + adapter.toString());
                        int count = adapter.getCount();
                        Log.e("Demo: hookListView->", "ConversationWithAppBrandListView has " + count + " child");
                        for (int i = 0; i < count; i++) {
                            Object s = adapter.getItem(i);
                            LogUtils.d("item data -> " + JSONObject.toJSONString(s));
                            Log.e("Demo: hookListView->", "item data -> " + JSONObject.toJSONString(s));
                        }
                    }
                });
    }

    /**
     * 微信列表界面长按点击事件
     * 引用地址 com.tencent.mm.ui.bizchat.BizChatConversationUI$a$17
     *
     * @param applicationContext
     * @param classLoader
     */
    private void hookWxItemLongClick(final Context applicationContext, ClassLoader classLoader) {
        Class<?> classIfExists = XposedHelpers.findClassIfExists("com.tencent.mm.ui.base.p$d", classLoader);
        if (classIfExists == null) return;
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.widget.i",
                classLoader,
                "a",
                View.class,
                int.class,
                long.class,
                View.OnCreateContextMenuListener.class,
                classIfExists,
                int.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        Log.e("Demo: hookWxItemLongClick->", param.args[1].toString() + "-" + param.args[5].toString() + "-" + param.args[6].toString());
                    }
                });
    }

    /**
     * 微信聊天界面
     *
     * @param applicationContext
     * @param classLoader
     */

    private void hookWxChatUIMM(final Context applicationContext, final ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.base.MMPullDownView",
                classLoader,
                "onLayout",
                boolean.class,
                int.class,
                int.class,
                int.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ViewGroup mMPullDownView = (ViewGroup) param.thisObject;
//                        if (mMPullDownView.getVisibility() == View.GONE) return;
                        for (int i = 0; i < mMPullDownView.getChildCount(); i++) {
                            View childAt = mMPullDownView.getChildAt(i);
                            if (childAt instanceof ListView) {
                                final ListView listView = (ListView) childAt;
                                final ListAdapter adapter = listView.getAdapter();
                                XposedHelpers.findAndHookMethod(adapter.getClass(),
                                        "getView",
                                        int.class,
                                        View.class,
                                        ViewGroup.class,
                                        new XC_MethodHook() {
                                            @Override
                                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                                super.beforeHookedMethod(param);
                                                int position = (int) param.args[0];
                                                View view = (View) param.args[1];
                                                JSONObject itemData = null;
//                                                LogUtils.i(position, view.toString());
                                                if (position < adapter.getCount()) {
                                                    itemData = JSON.parseObject(JSON.toJSONString(adapter.getItem(position)), JSONObject.class);
                                                    int itemViewType = adapter.getItemViewType(position);
//                                                    LogUtils.i(itemViewType);
                                                    //经过以上代码可以知道    itemViewType == 1的时候打印的值是正常对话列表的值
                                                    if (itemData != null && (view != null && view.toString().contains("com.tencent.mm.ui.chatting.viewitems.p"))) {
//                                                        if (itemData != null && itemViewType == 1 && (view != null && view.toString().contains("com.tencent.mm.ui.chatting.viewitems.p"))) {
                                                        String field_msgId = itemData.getString("field_msgId");
                                                        WxChatInvokeMsg wxChatInvokeMsg = WxChatInvokeMsgDB.queryByMsgId(applicationContext, field_msgId);
                                                        ViewGroup itemView = (ViewGroup) view;
                                                        View itemViewChild = itemView.getChildAt(0);
                                                        Object tag = itemViewChild.getTag(R.id.wx_parent_has_invoke_msg);
                                                        TextView textView;
                                                        if (tag == null) {
                                                            textView = new TextView(applicationContext);
                                                            textView.setGravity(Gravity.CENTER);
                                                            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                            itemViewChild.setTag(R.id.wx_parent_has_invoke_msg, textView);
                                                            textView.setId(R.id.wx_invoke_msg);
                                                            itemView.addView(textView);
                                                        } else {
                                                            textView = (TextView) itemViewChild.getTag(R.id.wx_parent_has_invoke_msg);
                                                        }
                                                        textView.setText("");
                                                        textView.setVisibility(View.GONE);
                                                        if (wxChatInvokeMsg != null) {
                                                            textView.setPadding(10, 5, 10, 5);
                                                            textView.setBackgroundDrawable(ShapeUtil.getCornerDrawable());
                                                            textView.setTextColor(Color.parseColor("#666666"));
                                                            View msgView = itemView.getChildAt(3);
                                                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                                                            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                                            lp.addRule(RelativeLayout.BELOW, msgView.getId());
                                                            lp.bottomMargin = 50;
                                                            textView.setText("这小子想撤回这个消息");
                                                            textView.setVisibility(View.VISIBLE);
                                                            LogUtils.i(position, view, itemViewType, itemData.toJSONString());
                                                        }
                                                    }
                                                }
//

                                            }

                                        });
                                break;

                            }
                        }
                    }
                });
    }

    /**
     * 微信聊天界面
     *
     * @param applicationContext
     * @param classLoader
     */
    XC_MethodHook.Unhook getView;
    XC_MethodHook.Unhook andHookMethod;

    private void hookWxChatUIWith(final Context applicationContext, final ClassLoader classLoader) {
        if (andHookMethod == null)
            andHookMethod = XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader, "ctt", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Field ySc = param.thisObject.getClass().getDeclaredField("ySc");
                    ySc.setAccessible(true);
                    ListView listView = (ListView) ySc.get(param.thisObject);
                    final ListAdapter adapter = listView.getAdapter();
                    if (getView == null)
                        getView = XposedHelpers.findAndHookMethod(adapter.getClass(),
                                "getView",
                                int.class,
                                View.class,
                                ViewGroup.class,
                                new XC_MethodHook() {

                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);
                                        int position = (int) param.args[0];
                                        View view = (View) param.args[1];
                                        ViewGroup viewGroup = (ViewGroup) param.args[2];
                                        Object item = null;
                                        if (position < adapter.getCount())
                                            item = adapter.getItem(position);
                                        if (item == null) return;
                                        if (view instanceof LinearLayout) {
                                            LinearLayout layout = (LinearLayout) view;
                                            layout.getChildAt(0).setVisibility(View.GONE);
                                            int childCount = layout.getChildCount();
                                            TextView textView = new TextView(applicationContext);
                                            textView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                                            textView.setText("sdsdsdsdsdadasd");
                                            layout.addView(textView);
                                            layout.setOrientation(LinearLayout.VERTICAL);
                                            LogUtils.e("Demo: hookWxChatUI->", "position->" + position, "View->" + view, "viewGroup->" + viewGroup, "data->" + JSONObject.toJSONString(item), "childCount->" + childCount);
                                        }

//                                        TextView textView = new TextView(applicationContext);
//                                        textView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
//                                        textView.setText("sdsdsdsdsdadasd");
//                                        ((LinearLayout)view).addView(textView);
//                                        ((LinearLayout)view).setOrientation(LinearLayout.VERTICAL);
                                    }
                                });
                }
            });
        //关闭的时候取消hook
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader, "onDestroy", new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                andHookMethod.unhook();
                andHookMethod = null;
                getView.unhook();
                getView = null;
            }
        });
    }

    /**
     * 微信聊天界面
     *
     * @param applicationContext
     * @param classLoader
     */
    private void hookWxChatUI(final Context applicationContext, final ClassLoader classLoader) {
        final Class<?> classIfExists = XposedHelpers.findClassIfExists("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader);
        if (classIfExists == null) return;
        XposedHelpers.findAndHookMethod(classIfExists,
                "onResume",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedHelpers.findAndHookMethod(classIfExists, "cuO", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Field ySc = param.thisObject.getClass().getDeclaredField("ySc");
                                ySc.setAccessible(true);
                                ListView listView = (ListView) ySc.get(param.thisObject);
                                ListAdapter adapter = listView.getAdapter();
                                if (adapter == null) return;
                                int count = adapter.getCount();
                                Log.e("Demo: hookWxChatUI->", "listview has " + count + " child");
                                for (int i = 0; i < count; i++) {
                                    Object s = adapter.getItem(i);
                                    Log.e("Demo: hookWxChatUI->", "item data -> " + JSONObject.toJSONString(s));
                                }
                            }
                        });
                    }
                });
    }

    /**
     * 微信聊天界面数据
     *
     * @param applicationContext
     * @param classLoader
     */
    /**
     * private void hookWxChatData(final Context applicationContext, final ClassLoader classLoader) {
     * final Class<?> classIfExists = XposedHelpers.findClassIfExists("com.tencent.mm.g.c.cg", classLoader);
     * if (classIfExists == null) return;
     * XposedHelpers.findAndHookMethod(classIfExists,
     * "c",
     * Cursor.class,
     * new XC_MethodHook() {
     *
     * @Override protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
     * super.beforeHookedMethod(param);
     * cg c = new cg();
     * Cursor cursor = (Cursor) param.args[0];
     * String[] columnNames = cursor.getColumnNames();
     * if (columnNames != null) {
     * int length = columnNames.length;
     * for (int i = 0; i < length; i++) {
     * int hashCode = columnNames[i].hashCode();
     * if (c.eQN == hashCode) {
     * c.field_msgId = cursor.getLong(i);
     * c.eQJ = true;
     * } else if (c.fnu == hashCode) {
     * c.field_msgSvrId = cursor.getLong(i);
     * }
     * else if (c.eVM == hashCode) {
     * c.field_isSend = cursor.getInt(i);
     * } else if (c.fnv == hashCode) {
     * c.field_isShowTimer = cursor.getInt(i);
     * } else if (c.eRS == hashCode) {
     * c.field_createTime = cursor.getLong(i);
     * } else if (c.fex == hashCode) {
     * c.field_talker = cursor.getString(i);
     * } else if (c.eSa == hashCode) {
     * c.field_content = cursor.getString(i);
     * } else if (c.fnw == hashCode) {
     * c.field_imgPath = cursor.getString(i);
     * } else if (c.fnx == hashCode) {
     * c.field_reserved = cursor.getString(i);
     * } else if (c.fjI == hashCode) {
     * c.field_lvbuffer = cursor.getBlob(i);
     * } else if (c.fny == hashCode) {
     * c.field_talkerId = cursor.getInt(i);
     * } else if (c.fnz == hashCode) {
     * c.field_transContent = cursor.getString(i);
     * } else if (c.fnA == hashCode) {
     * c.field_transBrandWording = cursor.getString(i);
     * } else if (c.fnB == hashCode) {
     * c.field_bizClientMsgId = cursor.getString(i);
     * } else if (c.eVB == hashCode) {
     * c.field_bizChatId = cursor.getLong(i);
     * } else if (c.fnC == hashCode) {
     * c.field_bizChatUserId = cursor.getString(i);
     * } else if (c.fiG == hashCode) {
     * c.field_msgSeq = cursor.getLong(i);
     * } else if (c.eVd == hashCode) {
     * c.field_flag = cursor.getInt(i);
     * } else if (c.eQO == hashCode) {
     * c.xPj = cursor.getLong(i);
     * }
     * }
     * try {
     * if (c.field_lvbuffer != null && c.field_lvbuffer.length != 0) {
     * t tVar = new t();
     * int bs = tVar.bs(c.field_lvbuffer);
     * if (bs != 0) {
     * Log.e("hookWxChatData", "parse LVBuffer error:" + bs);
     * return;
     * }
     * if (!tVar.cig()) {
     * c.fnD = tVar.getString();
     * }
     * if (!tVar.cig()) {
     * c.fnE = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnF = tVar.getString();
     * }
     * if (!tVar.cig()) {
     * c.fnG = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnH = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnI = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnJ = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnK = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnL = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.fnM = tVar.getString();
     * }
     * if (!tVar.cig()) {
     * c.fnN = tVar.getString();
     * }
     * if (!tVar.cig()) {
     * c.fnO = tVar.getString();
     * }
     * if (!tVar.cig()) {
     * c.fnP = tVar.getInt();
     * }
     * if (!tVar.cig()) {
     * c.eIB = tVar.getString();
     * }
     * if (!tVar.cig()) {
     * c.fnQ = tVar.getBuffer();
     * }
     * }
     * } catch (Exception e) {
     * Log.e("hookWxChatData", "get value failed");
     * }
     * }
     * <p>
     * Log.e("hookWxChatData", "data->" + JSONObject.toJSONString(c));
     * }
     * });
     * }
     **/

    /**
     * 直接hook sql达到获取撤回消息id的目的
     *
     * @param applicationContext
     * @param classLoader
     */
    private void hookDB(final Context applicationContext, final ClassLoader classLoader) {
        final Class<?> sQLiteDatabase = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase", classLoader);
        final Class<?> cancellationSignal = XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal", classLoader);
        if (sQLiteDatabase == null) return;
        XposedHelpers.findAndHookConstructor("com.tencent.wcdb.database.SQLiteProgram",
                classLoader,
                sQLiteDatabase,
                String.class,
                Object[].class,
                cancellationSignal,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        Object[] objArr = (Object[]) param.args[2];
                        String originalSql = param.args[1].toString();
                        //打印所有调用SQLiteProgram的sql
//                        LogUtils.e("hookDB", "sql -> " + param.args[1], "objArr:" + JSON.toJSONString(objArr));
                        if (objArr != null && originalSql.toUpperCase().startsWith("UPDATE MESSAGE")) {
                            for (Object obj : objArr) {
                                String sqlParam = obj.toString();//自己撤回10002 别人撤回10000
                                if (sqlParam.equals("10000")) {//别人撤回
                                    Object[] newObjArr = new Object[2];
//                                    param.args[1] = "UPDATE message SET type=? WHERE msgId=?";
                                    param.args[1] = "select * from message where type=? and msgId=?";
                                    param.args[2] = newObjArr;
                                    newObjArr[0] = 1;
                                    newObjArr[1] = objArr[objArr.length - 1];
//                                    param.args[1] = "UPDATE message SET content=(select (select content from message where msgId = ?)||X'0D'||X'0A'||X'0D'||X'0A'||(\"<sysmsg>wxInvoke卧槽，TA竟然要撤回上面的信息wxInvoke</sysmsg>\")),msgId=?,type=? WHERE msgId=?";
                                    LogUtils.e("hookDB", "originalSql->" + originalSql, "newSql->" + param.args[1], "sqlParam->" + JSON.toJSONString(newObjArr));

                                    WxChatInvokeMsg msg = new WxChatInvokeMsg();
                                    msg.setMsgId(newObjArr[1].toString());
                                    WxChatInvokeMsgDB.insertData(applicationContext, msg);
                                }
                            }
                        }
                        return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
                    }
                });

    }

    private void replaceData(final Context applicationContext, final ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.tencent.mm.g.c.ak",
                classLoader,
                "dQ",
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) {
                        Object arg = param.args[0];
                        Log.e("Demo: hookListView->", "ak -> " + arg.toString());
                        param.setResult("我是你爸爸");
                        return param;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.e("Demo: hookListView->", "ak after -> " + param.getResult());
                    }
                });
    }
}
