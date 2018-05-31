package com.zed.xposed.demo.greedao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by zed on 2018/5/30.
 */
@Entity
public class WxContact {
    private String field_username;
    private String field_nickname;
    private String field_alias;
    private String field_conRemark;
    private int field_verifyFlag;
    private int field_showHead;
    private int field_weiboFlag;
    private long fNU;
    private int field_deleteFlag;
    private byte[] field_lvbuff;
    private String field_descWordingId;
    private String field_openImAppid;
    private String field_descWording;
    private String field_descWordingQuanpin;
    private String field_remarkDesc;
    private String field_signature;

    @Generated(hash = 678797076)
    public WxContact(String field_username, String field_nickname,
                     String field_alias, String field_conRemark, int field_verifyFlag,
                     int field_showHead, int field_weiboFlag, long fNU, int field_deleteFlag,
                     byte[] field_lvbuff, String field_descWordingId,
                     String field_openImAppid, String field_descWording,
                     String field_descWordingQuanpin, String field_remarkDesc,
                     String field_signature) {
        this.field_username = field_username;
        this.field_nickname = field_nickname;
        this.field_alias = field_alias;
        this.field_conRemark = field_conRemark;
        this.field_verifyFlag = field_verifyFlag;
        this.field_showHead = field_showHead;
        this.field_weiboFlag = field_weiboFlag;
        this.fNU = fNU;
        this.field_deleteFlag = field_deleteFlag;
        this.field_lvbuff = field_lvbuff;
        this.field_descWordingId = field_descWordingId;
        this.field_openImAppid = field_openImAppid;
        this.field_descWording = field_descWording;
        this.field_descWordingQuanpin = field_descWordingQuanpin;
        this.field_remarkDesc = field_remarkDesc;
        this.field_signature = field_signature;
    }

    @Generated(hash = 1597177920)
    public WxContact() {
    }

    public long getfNU() {
        return fNU;
    }

    public void setfNU(long fNU) {
        this.fNU = fNU;
    }

    public String getField_alias() {
        return field_alias;
    }

    public void setField_alias(String field_alias) {
        this.field_alias = field_alias;
    }

    public String getField_conRemark() {
        return field_conRemark;
    }

    public void setField_conRemark(String field_conRemark) {
        this.field_conRemark = field_conRemark;
    }

    public int getField_deleteFlag() {
        return field_deleteFlag;
    }

    public void setField_deleteFlag(int field_deleteFlag) {
        this.field_deleteFlag = field_deleteFlag;
    }

    public String getField_descWording() {
        return field_descWording;
    }

    public void setField_descWording(String field_descWording) {
        this.field_descWording = field_descWording;
    }

    public String getField_descWordingId() {
        return field_descWordingId;
    }

    public void setField_descWordingId(String field_descWordingId) {
        this.field_descWordingId = field_descWordingId;
    }

    public String getField_descWordingQuanpin() {
        return field_descWordingQuanpin;
    }

    public void setField_descWordingQuanpin(String field_descWordingQuanpin) {
        this.field_descWordingQuanpin = field_descWordingQuanpin;
    }

    public byte[] getField_lvbuff() {
        return field_lvbuff;
    }

    public void setField_lvbuff(byte[] field_lvbuff) {
        this.field_lvbuff = field_lvbuff;
    }

    public String getField_nickname() {
        return field_nickname;
    }

    public void setField_nickname(String field_nickname) {
        this.field_nickname = field_nickname;
    }

    public String getField_openImAppid() {
        return field_openImAppid;
    }

    public void setField_openImAppid(String field_openImAppid) {
        this.field_openImAppid = field_openImAppid;
    }

    public String getField_remarkDesc() {
        return field_remarkDesc;
    }

    public void setField_remarkDesc(String field_remarkDesc) {
        this.field_remarkDesc = field_remarkDesc;
    }

    public int getField_showHead() {
        return field_showHead;
    }

    public void setField_showHead(int field_showHead) {
        this.field_showHead = field_showHead;
    }

    public String getField_signature() {
        return field_signature;
    }

    public void setField_signature(String field_signature) {
        this.field_signature = field_signature;
    }

    public String getField_username() {
        return field_username;
    }

    public void setField_username(String field_username) {
        this.field_username = field_username;
    }

    public int getField_verifyFlag() {
        return field_verifyFlag;
    }

    public void setField_verifyFlag(int field_verifyFlag) {
        this.field_verifyFlag = field_verifyFlag;
    }

    public int getField_weiboFlag() {
        return field_weiboFlag;
    }

    public void setField_weiboFlag(int field_weiboFlag) {
        this.field_weiboFlag = field_weiboFlag;
    }

    public long getFNU() {
        return this.fNU;
    }

    public void setFNU(long fNU) {
        this.fNU = fNU;
    }
}
