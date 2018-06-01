package com.zed.xposed.demo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.zed.xposed.demo.R;
import com.zed.xposed.demo.greedao.entity.WxContact;
import com.zed.xposed.demo.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.width;

/**
 * Created by zed on 2018/6/1.
 */

public class WxMomentContactDialog extends AlertDialog {

    List<WxContact> datas;

    protected WxMomentContactDialog(@NonNull Context context) {
        super(context);
    }

    public static WxMomentContactDialog instance(@NonNull Context context) {
        return new WxMomentContactDialog(context);
    }

    public Builder build() {
        getData();
        Builder builder = new Builder(getContext());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_contact_moment, null);
        ListView lv = inflate.findViewById(R.id.lv);
        lv.setAdapter(new WxContactAdapter());
        TextView space = new TextView(getContext());
        space.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(getContext(), 40)));
        space.setText("小哥别再拉了，到底啦~");
        space.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        space.setGravity(Gravity.CENTER);
        lv.addFooterView(space);
        builder.setView(inflate);
        return builder;
    }

    List getData() {
        if (datas != null) return datas;
        return datas = new ArrayList<WxContact>() {
            {
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
                add(new WxContact());
            }
        };
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_contact_moment, parent, false);
                holder = new Holder();
                holder.tvName = convertView.findViewById(R.id.tvName);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.tvName.setBackgroundResource(wxContact.isCheck() ? R.color.color_e6d1d8 : R.color.white);
            holder.tvName.setText(String.valueOf(position));
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wxContact.setCheck(!wxContact.isCheck());
                    v.setBackgroundResource(wxContact.isCheck() ? R.color.color_e6d1d8 : R.color.white);
                }
            });
            return convertView;
        }

        class Holder {
            TextView tvName;
        }
    }
}
