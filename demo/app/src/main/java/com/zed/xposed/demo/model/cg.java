package com.zed.xposed.demo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class cg {
    public long xPj = -1;
    public static final String[] eQF = new String[0];
    public static final int eQN = "msgId".hashCode();
    public static final int eQO = "rowid".hashCode();
//    public static final int eQU = DownloadInfo.STATUS.hashCode();
    public static final int eRS = "createTime".hashCode();
//    public static final int eRV = DownloadSettingTable$Columns.TYPE.hashCode();
    public static final int eSa = "content".hashCode();
    public static final int eVB = "bizChatId".hashCode();
    public static final int eVM = "isSend".hashCode();
    public static final int eVd = "flag".hashCode();
    public static final int fex = "talker".hashCode();
    public static final int fiG = "msgSeq".hashCode();
    public static final int fjI = "lvbuffer".hashCode();
    public static final int fnA = "transBrandWording".hashCode();
    public static final int fnB = "bizClientMsgId".hashCode();
    public static final int fnC = "bizChatUserId".hashCode();
    public static final int fnu = "msgSvrId".hashCode();
    public static final int fnv = "isShowTimer".hashCode();
    public static final int fnw = "imgPath".hashCode();
    public static final int fnx = "reserved".hashCode();
    public static final int fny = "talkerId".hashCode();
    public static final int fnz = "transContent".hashCode();
    public String eIB;
    public boolean eQJ = false;
    public boolean eQR = false;
    public boolean eRE = false;
    public boolean eRw = false;
    public boolean eRz = false;
    public boolean eVb = false;
    public boolean eVn = false;
    public boolean eVy = false;
    public boolean feh = false;
    public boolean fiA = false;
    public long field_bizChatId;
    public String field_bizChatUserId;
    public String field_bizClientMsgId;
    public String field_content;
    public long field_createTime;
    public int field_flag;
    public String field_imgPath;
    public int field_isSend;
    public int field_isShowTimer;
    public byte[] field_lvbuffer;
    public long field_msgId;
    public long field_msgSeq;
    public long field_msgSvrId;
    public String field_reserved;
    public int field_status;
    public String field_talker;
    public int field_talkerId;
    public String field_transBrandWording;
    public String field_transContent;
    public int field_type;
    public boolean fjw = false;
    public String fnD;
    public int fnE;
    public String fnF;
    public int fnG;
    public int fnH;
    public int fnI;
    public int fnJ;
    public int fnK;
    public int fnL;
    public String fnM;
    public String fnN;
    public String fnO;
    public int fnP;
    public byte[] fnQ;
    public boolean fnl = false;
    public boolean fnm = false;
    public boolean fnn = false;
    public boolean fno = false;
    public boolean fnp = false;
    public boolean fnq = false;
    public boolean fnr = false;
    public boolean fns = false;
    public boolean fnt = false;

    public final void at(long j) {
        this.field_msgId = j;
        this.eQJ = true;
    }

    public final long wN() {
        return this.field_msgId;
    }

    public final void au(long j) {
        this.field_msgSvrId = j;
        this.fnl = true;
    }

    public final long wO() {
        return this.field_msgSvrId;
    }

    public final void setType(int i) {
        this.field_type = i;
        this.eRz = true;
    }

    public int getType() {
        return this.field_type;
    }

    public void eV(int i) {
        this.field_status = i;
        this.eQR = true;
    }

    public final void eW(int i) {
        this.field_isSend = i;
        this.eVy = true;
    }

    public final int wP() {
        return this.field_isSend;
    }

    public final void av(long j) {
        this.field_createTime = j;
        this.eRw = true;
    }

    public final long wQ() {
        return this.field_createTime;
    }

    public final void ed(String str) {
        this.field_talker = str;
        this.feh = true;
    }

    public final String wR() {
        return this.field_talker;
    }

    public final void setContent(String str) {
        this.field_content = str;
        this.eRE = true;
    }

    public final String wS() {
        return this.field_content;
    }

    public final void ee(String str) {
        this.field_imgPath = str;
        this.fnn = true;
    }

    public final void ef(String str) {
        this.field_reserved = str;
        this.fno = true;
    }

    public final void C(byte[] bArr) {
        this.field_lvbuffer = bArr;
        this.fjw = true;
    }

    public final void eg(String str) {
        this.field_transContent = str;
        this.fnq = true;
    }

    public final void eh(String str) {
        this.field_bizClientMsgId = str;
        this.fns = true;
    }

    public final void aw(long j) {
        this.field_bizChatId = j;
        this.eVn = true;
    }

    public final void ax(long j) {
        this.field_msgSeq = j;
        this.fiA = true;
    }

    public final void ff(int i) {
        this.field_flag = i;
        this.eVb = true;
    }
    /**
    public void c(Cursor cursor) {
        String[] columnNames = cursor.getColumnNames();
        if (columnNames != null) {
            int length = columnNames.length;
            for (int i = 0; i < length; i++) {
                int hashCode = columnNames[i].hashCode();
                if (eQN == hashCode) {
                    this.field_msgId = cursor.getLong(i);
                    this.eQJ = true;
                } else if (fnu == hashCode) {
                    this.field_msgSvrId = cursor.getLong(i);
                } else if (eRV == hashCode) {
                    this.field_type = cursor.getInt(i);
                } else if (eQU == hashCode) {
                    this.field_status = cursor.getInt(i);
                } else if (eVM == hashCode) {
                    this.field_isSend = cursor.getInt(i);
                } else if (fnv == hashCode) {
                    this.field_isShowTimer = cursor.getInt(i);
                } else if (eRS == hashCode) {
                    this.field_createTime = cursor.getLong(i);
                } else if (fex == hashCode) {
                    this.field_talker = cursor.getString(i);
                } else if (eSa == hashCode) {
                    this.field_content = cursor.getString(i);
                } else if (fnw == hashCode) {
                    this.field_imgPath = cursor.getString(i);
                } else if (fnx == hashCode) {
                    this.field_reserved = cursor.getString(i);
                } else if (fjI == hashCode) {
                    this.field_lvbuffer = cursor.getBlob(i);
                } else if (fny == hashCode) {
                    this.field_talkerId = cursor.getInt(i);
                } else if (fnz == hashCode) {
                    this.field_transContent = cursor.getString(i);
                } else if (fnA == hashCode) {
                    this.field_transBrandWording = cursor.getString(i);
                } else if (fnB == hashCode) {
                    this.field_bizClientMsgId = cursor.getString(i);
                } else if (eVB == hashCode) {
                    this.field_bizChatId = cursor.getLong(i);
                } else if (fnC == hashCode) {
                    this.field_bizChatUserId = cursor.getString(i);
                } else if (fiG == hashCode) {
                    this.field_msgSeq = cursor.getLong(i);
                } else if (eVd == hashCode) {
                    this.field_flag = cursor.getInt(i);
                } else if (eQO == hashCode) {
                    this.xPj = cursor.getLong(i);
                }
            }
            try {
                if (this.field_lvbuffer != null && this.field_lvbuffer.length != 0) {
                    t tVar = new t();
                    int bs = tVar.bs(this.field_lvbuffer);
                    if (bs != 0) {
                        Log.e("BaseMsgInfo", "parse LVBuffer error:" + bs);
                        return;
                    }
                    if (!tVar.cig()) {
                        this.fnD = tVar.getString();
                    }
                    if (!tVar.cig()) {
                        this.fnE = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnF = tVar.getString();
                    }
                    if (!tVar.cig()) {
                        this.fnG = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnH = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnI = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnJ = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnK = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnL = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.fnM = tVar.getString();
                    }
                    if (!tVar.cig()) {
                        this.fnN = tVar.getString();
                    }
                    if (!tVar.cig()) {
                        this.fnO = tVar.getString();
                    }
                    if (!tVar.cig()) {
                        this.fnP = tVar.getInt();
                    }
                    if (!tVar.cig()) {
                        this.eIB = tVar.getString();
                    }
                    if (!tVar.cig()) {
                        this.fnQ = tVar.getBuffer();
                    }
                }
            } catch (Exception e) {
                Log.e("BaseMsgInfo", "get value failed");
            }
        }
    }

    public ContentValues ww() {
        try {
            if (this.fjw) {
                t tVar = new t();
                tVar.cih();
                tVar.We(this.fnD);
                tVar.DG(this.fnE);
                tVar.We(this.fnF);
                tVar.DG(this.fnG);
                tVar.DG(this.fnH);
                tVar.DG(this.fnI);
                tVar.DG(this.fnJ);
                tVar.DG(this.fnK);
                tVar.DG(this.fnL);
                tVar.We(this.fnM);
                tVar.We(this.fnN);
                tVar.We(this.fnO);
                tVar.DG(this.fnP);
                tVar.We(this.eIB);
                tVar.bt(this.fnQ);
                this.field_lvbuffer = tVar.cii();
            }
        } catch (Exception e) {
            Log.e("MicroMsg.SDK.BaseMsgInfo", "get value failed, %s"+ e.getMessage());
        }
        ContentValues contentValues = new ContentValues();
        if (this.eQJ) {
            contentValues.put("msgId", Long.valueOf(this.field_msgId));
        }
        if (this.fnl) {
            contentValues.put("msgSvrId", Long.valueOf(this.field_msgSvrId));
        }
        if (this.eRz) {
            contentValues.put(DownloadSettingTable$Columns.TYPE, Integer.valueOf(this.field_type));
        }
        if (this.eQR) {
            contentValues.put(DownloadInfo.STATUS, Integer.valueOf(this.field_status));
        }
        if (this.eVy) {
            contentValues.put("isSend", Integer.valueOf(this.field_isSend));
        }
        if (this.fnm) {
            contentValues.put("isShowTimer", Integer.valueOf(this.field_isShowTimer));
        }
        if (this.eRw) {
            contentValues.put("createTime", Long.valueOf(this.field_createTime));
        }
        if (this.feh) {
            contentValues.put("talker", this.field_talker);
        }
        if (this.field_content == null) {
            this.field_content = "";
        }
        if (this.eRE) {
            contentValues.put("content", this.field_content);
        }
        if (this.fnn) {
            contentValues.put("imgPath", this.field_imgPath);
        }
        if (this.fno) {
            contentValues.put("reserved", this.field_reserved);
        }
        if (this.fjw) {
            contentValues.put("lvbuffer", this.field_lvbuffer);
        }
        if (this.fnp) {
            contentValues.put("talkerId", Integer.valueOf(this.field_talkerId));
        }
        if (this.field_transContent == null) {
            this.field_transContent = "";
        }
        if (this.fnq) {
            contentValues.put("transContent", this.field_transContent);
        }
        if (this.field_transBrandWording == null) {
            this.field_transBrandWording = "";
        }
        if (this.fnr) {
            contentValues.put("transBrandWording", this.field_transBrandWording);
        }
        if (this.field_bizClientMsgId == null) {
            this.field_bizClientMsgId = "";
        }
        if (this.fns) {
            contentValues.put("bizClientMsgId", this.field_bizClientMsgId);
        }
        if (this.eVn) {
            contentValues.put("bizChatId", Long.valueOf(this.field_bizChatId));
        }
        if (this.field_bizChatUserId == null) {
            this.field_bizChatUserId = "";
        }
        if (this.fnt) {
            contentValues.put("bizChatUserId", this.field_bizChatUserId);
        }
        if (this.fiA) {
            contentValues.put("msgSeq", Long.valueOf(this.field_msgSeq));
        }
        if (this.eVb) {
            contentValues.put("flag", Integer.valueOf(this.field_flag));
        }
        if (this.xPj > 0) {
            contentValues.put("rowid", Long.valueOf(this.xPj));
        }
        return contentValues;
    }
**/
    public final void ei(String str) {
        this.fnD = str;
        this.fjw = true;
    }

    public final void fg(int i) {
        this.fnE = i;
        this.fjw = true;
    }

    public final void ej(String str) {
        this.fnF = str;
        this.fjw = true;
    }

    public final void fh(int i) {
        this.fnG = i;
        this.fjw = true;
    }

    public final void fi(int i) {
        this.fnH = i;
        this.fjw = true;
    }

    public final void fj(int i) {
        this.fnL = i;
        this.fjw = true;
    }

    public final void ek(String str) {
        this.fnN = str;
        this.fjw = true;
    }

    public final void el(String str) {
        this.fnO = str;
        this.fjw = true;
    }

    public final void fk(int i) {
        this.fnP = i;
        this.fjw = true;
    }
}
