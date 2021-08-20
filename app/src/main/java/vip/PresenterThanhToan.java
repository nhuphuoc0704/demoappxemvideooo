package vip;

import android.content.Context;

import sql.SQLHelper;

public class PresenterThanhToan {
    SQLHelper sqlHelper;
    IThanhToan iThanhToan;
    Context context;

    public PresenterThanhToan(IThanhToan iThanhToan, Context context) {
        this.iThanhToan = iThanhToan;
        this.context = context;
    }

    public void upDateVipSuccess(String tdn){
        if(sqlHelper==null) sqlHelper=new SQLHelper(context);
        sqlHelper.updateVIP(tdn);
        iThanhToan.onSuccessFull();
    }
}
