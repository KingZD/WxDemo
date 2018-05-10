package com.zed.xposed.demo

/**
 * Created by zed on 2018/4/11.
 */
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

open class Tutorial : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: LoadPackageParam?) {
//        if (lpparam?.packageName == "com.wrbug.xposeddemo") {
//            XposedBridge.log("Loaded Test app: " + lpparam.packageName)
//
//        }
    }

}