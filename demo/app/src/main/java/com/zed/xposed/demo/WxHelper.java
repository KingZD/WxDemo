package com.zed.xposed.demo;

import com.zed.xposed.demo.action.inter.IChat;
import com.zed.xposed.demo.action.inter.IContact;
import com.zed.xposed.demo.action.inter.IDB;
import com.zed.xposed.demo.action.inter.IMoment;

/**
 * Created by zed on 2018/4/11.
 * 根据业务类型划分为三个模块以前的老代码放在{@link WxHelperOld}
 * 这里只关心开启了什么功能 具体实现逻辑放在了{@link com.zed.xposed.demo.action.inter.impl}目录下
 * 方便以后app进行开关操作
 */

public class WxHelper extends Wx {


    @Override
    void initChat(IChat iChat) {
        iChat.hookWxChatUIMM();
        iChat.autoRepeat();
    }

    @Override
    void initMoment(IMoment iMoment) {
        iMoment.hookWxMoments();
    }

    @Override
    void initDB(IDB idb) {
        idb.hookDB();
    }

    @Override
    void initContact(IContact iContact) {
        iContact.hookContact();
    }
}
