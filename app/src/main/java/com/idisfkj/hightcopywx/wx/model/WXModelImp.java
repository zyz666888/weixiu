package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.dao.WxDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SPUtils;
import java.util.ArrayList;
import java.util.List;

public class WXModelImp implements WXModel {
    private List<WXItemInfo> mList;
    private WXItemInfo wxItemInfo;

    @Override
    public void initData(WxDataHelper helper) {
        mList = new ArrayList<>();
        wxItemInfo = new WXItemInfo();
        if (!SPUtils.getString("regId").equals(App.DEVELOPER_ID)) {
            wxItemInfo.setTitle(App.DEVELOPER_NAME);
            wxItemInfo.setContent(App.DEVELOPER_MESSAGE);
            wxItemInfo.setTime(CalendarUtils.getCurrentDate());
            wxItemInfo.setRegId(App.DEVELOPER_ID);
            wxItemInfo.setNumber(App.DEVELOPER_NUMBER);
            wxItemInfo.setCurrentAccount(SPUtils.getString("userPhone"));
            helper.insert(wxItemInfo);
        }
    }
}
