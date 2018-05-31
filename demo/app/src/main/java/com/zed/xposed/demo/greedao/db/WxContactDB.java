package com.zed.xposed.demo.greedao.db;

import android.content.Context;

import com.zed.xposed.demo.greedao.dao.WxContactDao;
import com.zed.xposed.demo.greedao.entity.WxContact;
import com.zed.xposed.demo.greedao.entity.WxContact;
import com.zed.xposed.demo.greedao.entity.WxContact;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by zed on 2018/5/8.
 */
public class WxContactDB {
    /**
     * 添加数据至数据库  
     *
     * @param context
     * @param stu
     */
    public static void insertData(Context context, WxContact stu) {
        DbManager.getDaoSession(context).getWxContactDao().insert(stu);
    }


    /**
     * 将数据实体通过事务添加至数据库  
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<WxContact> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DbManager.getDaoSession(context).getWxContactDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖  
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；  
     *
     * @param context
     * @param WxContact
     */
    public static void saveData(Context context, WxContact WxContact) {
        DbManager.getDaoSession(context).getWxContactDao().save(WxContact);
    }

    /**
     * 删除数据至数据库  
     *
     * @param context
     * @param WxContact 删除具体内容  
     */
    public static void deleteData(Context context, WxContact WxContact) {
        DbManager.getDaoSession(context).getWxContactDao().delete(WxContact);
    }


    /**
     * 删除全部数据  
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DbManager.getDaoSession(context).getWxContactDao().deleteAll();
    }

    /**
     * 更新数据库  
     *
     * @param context
     * @param WxContact
     */
    public static void updateData(Context context, WxContact WxContact) {
        DbManager.getDaoSession(context).getWxContactDao().update(WxContact);
    }


    /**
     * 查询所有数据  
     *
     * @param context
     * @return
     */
    public static List<WxContact> queryAll(Context context) {
        QueryBuilder<WxContact> builder = DbManager.getDaoSession(context).getWxContactDao().queryBuilder();

        return builder.build().list();
    }

    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static WxContact queryByUsername(Context context,String fieldUsername) {
        QueryBuilder<WxContact> builder = DbManager.getDaoSession(context).getWxContactDao().queryBuilder();
        Query<WxContact> build = builder.where(WxContactDao.Properties.Field_username.eq(fieldUsername)).build();
        return build.unique();
    }


    /**
     *  分页加载  
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)  
     * @param pageNum  每页显示多少个  
     * @return
     */
    public static List<WxContact> queryPaging( int pageSize, int pageNum,Context context){
        WxContactDao WxContactDao = DbManager.getDaoSession(context).getWxContactDao();
        List<WxContact> listMsg = WxContactDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }
}
