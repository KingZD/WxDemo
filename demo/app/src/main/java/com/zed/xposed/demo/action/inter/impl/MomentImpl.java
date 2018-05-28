package com.zed.xposed.demo.action.inter.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zed.xposed.demo.action.inter.IMoment;
import com.zed.xposed.demo.log.LogUtils;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by zed on 2018/5/23.
 */
public class MomentImpl extends IMoment {

    public MomentImpl(Context mContext, ClassLoader mClassLoader) {
        super(mContext, mClassLoader);
        LogUtils.i("朋友圈初始化操作完成");
    }

    /**
     * hook 朋友圈
     */

    @Override
    public void hookWxMoments() {
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.sns.ui.bb",
                mClassLoader,
                "onCreate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Field mat = XposedHelpers.findFieldIfExists(param.thisObject.getClass(), "mActivity");
                        final Activity mActivity = (Activity) mat.get(param.thisObject);
                        Field odm = XposedHelpers.findFieldIfExists(param.thisObject.getClass(), "odm");
                        ListView mlv = (ListView) odm.get(param.thisObject);
                        Class<?> mlvSuperClass = mlv.getClass().getSuperclass();
//                        LogUtils.i(mlv.toString(), mlvSuperClass.toString());
                        XposedHelpers.findAndHookMethod(mlvSuperClass,
                                "setAdapter",
                                ListAdapter.class,
                                new XC_MethodHook() {
                                    @Override
                                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                        super.beforeHookedMethod(param);
                                        final ListAdapter adapter = (ListAdapter) param.args[0];
//                                        LogUtils.i(adapter.toString());
                                        XposedHelpers.findAndHookMethod(adapter.getClass(),
                                                "getView",
                                                int.class,
                                                View.class,
                                                ViewGroup.class,
                                                new XC_MethodHook() {
                                                    @Override
                                                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                                        super.beforeHookedMethod(param);
//                                                        int position = (int) param.args[0];
                                                        final View view = (View) param.args[1];
//                                                        ViewGroup viewGroup = (ViewGroup) param.args[2];
                                                        if (view != null) {
                                                            //fl 第一个view是图片 第二个view是朋友圈内容
                                                            final ViewGroup fl = (ViewGroup) view;
//                                                            StringBuffer sb = new StringBuffer();

//                                                            for (int i = 0; i < fl.getChildCount(); i++) {
//                                                                sb.append(fl.getChildAt(i).toString());
//                                                                sb.append("@");
//                                                                sb.append(fl.getChildAt(i).getId());
//                                                                sb.append("@");
//                                                                sb.append(applicationContext.getResources().getResourceName(fl.getChildAt(i).getId()));
//                                                                sb.append("\n");
//                                                            }

//                                                            LogUtils.i(position, view, sb.toString(), viewGroup, JSON.toJSONString(adapter.getItem(position)), adapter.getItem(position).toString());

                                                            view.setOnLongClickListener(new View.OnLongClickListener() {

                                                                @Override
                                                                public boolean onLongClick(View v) {
                                                                    new AlertDialog.Builder(mActivity)
                                                                            .setTitle("温馨提示")
                                                                            .setMessage("是否对当前消息进行疯狂点赞").setNegativeButton("是的", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            mActivity.runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    if (fl != null && fl.getChildCount() > 1) {
                                                                                        LinearLayout msgLinear = (LinearLayout) fl.getChildAt(1);
                                                                                        for (int i = 0; i < msgLinear.getChildCount(); i++) {
                                                                                            View mc = msgLinear.getChildAt(i);
                                                                                            String resourceName = mContext.getResources().getResourceName(mc.getId());
                                                                                            if (mc instanceof TextView) {
//                                                                                                mc.setVisibility(View.VISIBLE);
//                                                                                                ((TextView) mc).append("看雪论坛，看雪论坛++，看雪论坛+++");
                                                                                                //因为有些gettext 得到的是spanned 调toString会引起shutdown 所以用以下方式打印即可
//                                                                                                LogUtils.i(((TextView) mc).getText());
                                                                                            } else if (mc instanceof ViewStub) {
//                                                                                                ((ViewStub) mc).inflate();
                                                                                            } else if ("com.tencent.mm:id/de_".equals(resourceName)) {
                                                                                                ViewGroup vg = (ViewGroup) mc;
                                                                                                mc.setVisibility(View.VISIBLE);
                                                                                                if (vg.getChildCount() > 0) {
                                                                                                    TextView likeView = (TextView) vg.getChildAt(0);
                                                                                                    likeView.append("看雪论坛，看雪论坛++，看雪论坛+++");
                                                                                                    LogUtils.i((Object) likeView.getCompoundDrawables());
                                                                                                } else {
                                                                                                    TextView tv = new TextView(mContext);
                                                                                                    tv.setText(",看雪论坛,看雪论坛++,看雪论坛+++");
                                                                                                    tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                                                                    vg.addView(tv);
                                                                                                }
                                                                                            }
//                                                                                            LogUtils.i(mc, resourceName, "com.tencent.mm:id/de_");
                                                                                        }
                                                                                    }
                                                                                }
                                                                            });

                                                                        }
                                                                    }).setNeutralButton("不是的", null).show();
                                                                    return false;
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
}
