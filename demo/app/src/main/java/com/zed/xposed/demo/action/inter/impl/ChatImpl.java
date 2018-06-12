package com.zed.xposed.demo.action.inter.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zed.xposed.demo.R;
import com.zed.xposed.demo.action.inter.IChat;
import com.zed.xposed.demo.greedao.db.WxChatInvokeMsgDB;
import com.zed.xposed.demo.greedao.entity.WxChatInvokeMsg;
import com.zed.xposed.demo.greedao.util.ShapeUtil;
import com.zed.xposed.demo.log.LogUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by zed on 2018/5/23.
 */
public class ChatImpl extends IChat {

    public ChatImpl(Context mContext, ClassLoader mClassLoader) {
        super(mContext, mClassLoader);
        LogUtils.i("聊天初始化操作完成");
    }

    /**
     * 微信聊天界面防止消息撤回
     */
    @Override
    public void hookWxChatUIMM() {
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.base.MMPullDownView",
                mClassLoader,
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
                                                        WxChatInvokeMsg wxChatInvokeMsg = WxChatInvokeMsgDB.queryByMsgId(mContext, field_msgId);
                                                        ViewGroup itemView = (ViewGroup) view;
                                                        View itemViewChild = itemView.getChildAt(0);
                                                        Object tag = itemViewChild.getTag(R.id.wx_parent_has_invoke_msg);
                                                        TextView textView;
                                                        if (tag == null) {
                                                            textView = new TextView(mContext);
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

    @Override
    public void autoRepeat() {

        final Class<?> afClass = XposedHelpers.findClass("com.tencent.mm.sdk.platformtools.af", mClassLoader);
//        //获取收到消息的标记通知栏
        Class<?> b$1Class = XposedHelpers.findClass("com.tencent.mm.booter.notification.b$1", mClassLoader);
        XposedHelpers.findAndHookMethod(b$1Class, "handleMessage", Message.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Message message = (Message) param.args[0];
                final String string = message.getData().getString("notification.show.talker");
                String string2 = message.getData().getString("notification.show.message.content");
                int i = message.getData().getInt("notification.show.message.type");
                int i2 = message.getData().getInt("notification.show.tipsflag");
                LogUtils.i(string, string2, i, i2, afClass);
                Class<?> gClass = XposedHelpers.findClass("com.tencent.mm.kernel.g", mClassLoader);
                Object g = XposedHelpers.callStaticMethod(gClass, "Ea");
                Object filedA = XposedHelpers.getObjectField(g, "fVR");

                Class<?> oClass = XposedHelpers.findClass("com.tencent.mm.ac.o", mClassLoader);
                Class<?> lClass = XposedHelpers.findClass("com.tencent.mm.ac.l", mClassLoader);
                Method methodA = XposedHelpers.findMethodExact(oClass, "a", lClass, int.class);
                Object o = XposedHelpers.callStaticMethod(oClass, "a", filedA);

                //这里只能自己看到回复消息 对方看不到
//                Class<?> aClass = XposedHelpers.findClass("com.tencent.mm.ai.a", mClassLoader);
//                Object a = XposedHelpers.newInstance(aClass, new Class[]{String.class, String.class}, string, "haha");
//                Object[] p = new Object[]{a, 0};

                //调用这里可以实现自动回复 并发送到对方
                Class<?> iClass = XposedHelpers.findClass("com.tencent.mm.modelmulti.i", mClassLoader);
                Object io = XposedHelpers.newInstance(iClass, new Class[]{String.class, String.class, int.class, int.class, Object.class}, string, "haha", 1, 1, new HashMap<String, String>() {{
                    put(string, string);
                }});
                Object[] pp = new Object[]{io, 0};
//                LogUtils.i(gClass, g, filedA, oClass, lClass, methodA, o, aClass, a, p, iClass, io, pp);
                LogUtils.i(gClass, g, filedA, oClass, lClass, methodA, o, iClass, io, pp);
                try {
//                    XposedBridge.invokeOriginalMethod(methodA, o, p);

                    XposedBridge.invokeOriginalMethod(methodA, o, pp);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e(e.getLocalizedMessage());
                }
                LogUtils.i("send ok");
            }
        });

    }
}
