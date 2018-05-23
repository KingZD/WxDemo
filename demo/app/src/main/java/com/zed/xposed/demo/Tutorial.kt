package com.zed.xposed.demo

/**
 * Created by zed on 2018/4/11.
 */
import com.zed.xposed.demo.action.inter.IChat
import com.zed.xposed.demo.action.inter.IDB
import com.zed.xposed.demo.action.inter.IMoment

/**
 * Created by zed on 2018/4/11.
 * 根据业务类型划分为三个模块以前的老代码放在{@link WxHelperOld}
 * 这里只关心开启了什么功能 具体实现逻辑放在了{@link com.zed.xposed.demo.action.inter.impl}目录下
 * 方便以后app进行开关操作
 */

open class Tutorial : Wx() {

    override fun initChat(iChat: IChat?) {
//        iChat?.hookWxChatUIMM()
    }

    override fun initMoment(iMoment: IMoment?) {
//        iMoment?.hookWxMoments()
    }

    override fun initDB(idb: IDB?) {
//        idb?.hookDB()
    }


}