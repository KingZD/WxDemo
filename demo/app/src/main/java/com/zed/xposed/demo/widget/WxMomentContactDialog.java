package com.zed.xposed.demo.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zed.xposed.demo.R;
import com.zed.xposed.demo.greedao.entity.WxContact;
import com.zed.xposed.demo.log.LogUtils;
import com.zed.xposed.demo.util.ViewUtil;

import java.util.List;

/**
 * Created by zed on 2018/6/1.
 * 这里全部用代码写布局是为了防止 进行hook的时候引用外部资源引起微信shutdown
 */

public class WxMomentContactDialog extends Dialog {
    List<WxContact> datas;

    public interface CallBack {
        void sure();
    }

    protected WxMomentContactDialog(@NonNull Context context) {
        super(context);
    }

    public static WxMomentContactDialog instance(@NonNull Context context) {
        return new WxMomentContactDialog(context);
    }

    public Dialog build(List<WxContact> datas, final CallBack callBack) {
        this.datas = datas;
        View inflate = createDialogContactMoment(callBack);
        ListView lv = inflate.findViewById(R.id.lv);
        lv.setAdapter(new WxContactAdapter());

        LinearLayout button = new LinearLayout(getContext());
        button.setOrientation(LinearLayout.VERTICAL);
        TextView space = new TextView(getContext());
        space.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 40)));
        space.setText("小哥别再拉了，到底啦~");
        space.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        space.setGravity(Gravity.CENTER);
        button.addView(space);
        lv.addFooterView(button);
        setContentView(inflate);
        return this;
    }

    public ViewGroup createDialogContactMoment(final CallBack callBack) {
        LinearLayout container = new LinearLayout(getContext());
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.gravity = Gravity.CENTER;
        container.setLayoutParams(ll);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        container.setBackgroundColor(Color.parseColor("#ffffff"));

        TextView tvOne = new TextView(getContext());
        tvOne.setText("请选择联系人");
        tvOne.setGravity(Gravity.CENTER);
        TextPaint tp = tvOne.getPaint();
        tp.setFakeBoldText(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 20));
        layoutParams.topMargin = ViewUtil.dp2px(getContext(), 10);
        tvOne.setLayoutParams(layoutParams);
        container.addView(tvOne);

        TextView tvTwo = new TextView(getContext());
        tvTwo.setText("(单机选择/取消)");
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 20));
        tvTwo.setGravity(Gravity.CENTER);
        tp = tvTwo.getPaint();
        tp.setFakeBoldText(true);
        container.addView(tvTwo);

        ListView lv = new ListView(getContext());
        lv.setId(R.id.lv);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.topMargin = ViewUtil.dp2px(getContext(), 10);
        layoutParams.height = 0;
        layoutParams.weight = 1;
        lv.setLayoutParams(layoutParams);
        lv.setVerticalScrollBarEnabled(false);
        container.addView(lv);


        Button sure = new Button(getContext());
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 40));
        layoutParams.topMargin = ViewUtil.dp2px(getContext(), 10);
        sure.setLayoutParams(layoutParams);
        sure.setText("确认");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callBack.sure();
            }
        });
        container.addView(sure);
        return container;
    }

    public ViewGroup createItemDialogContactMoment() {
        LinearLayout container = new LinearLayout(getContext());
        container.setPadding(ViewUtil.dp2px(getContext(), 10), 0, 0, 0);
        AbsListView.LayoutParams ll = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(ll);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setBackgroundColor(Color.parseColor("#ffffff"));

        TextView tvOne = new TextView(getContext());
        tvOne.setId(R.id.tvName);
        tvOne.setBackgroundColor(Color.parseColor("#ffffff"));
        tvOne.setGravity(Gravity.CENTER | Gravity.START);
        TextPaint tp = tvOne.getPaint();
        tp.setFakeBoldText(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 40));
        layoutParams.topMargin = ViewUtil.dp2px(getContext(), 10);
        tvOne.setLayoutParams(layoutParams);
        container.addView(tvOne);

        View tvTwo = new View(getContext());
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 1));
        tvTwo.setBackgroundColor(Color.parseColor("#cccccc"));
        tvTwo.setLayoutParams(layoutParams);
        container.addView(tvTwo);
        return container;
    }


    class WxContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return datas.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final WxContact wxContact = datas.get(position);
            Holder holder = null;
            if (convertView == null) {
                convertView = createItemDialogContactMoment();
                holder = new Holder();
                holder.tvName = convertView.findViewById(R.id.tvName);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tvName.setBackgroundColor(wxContact.isCheck() ? Color.parseColor("#e6d1d8") : Color.parseColor("#ffffff"));
            holder.tvName.setText(wxContact.getField_nickname());
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wxContact.setCheck(!wxContact.isCheck());
                    v.setBackgroundColor(wxContact.isCheck() ? Color.parseColor("#e6d1d8") : Color.parseColor("#ffffff"));
                }
            });
            return convertView;
        }

        class Holder {
            TextView tvName;
        }
    }
}
