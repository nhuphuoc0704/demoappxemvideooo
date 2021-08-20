package login;

import android.content.Context;

import java.util.List;

import model.User;
import sql.SQLHelper;

public class PresenterLogin {
    ILogin iLogin;
    Context context;
    SQLHelper sqlHelper;
    List<User> list;

    public PresenterLogin(Context context, ILogin iLogin) {
        this.context = context;
        this.iLogin= iLogin;
    }
    public void insertUser(String tdn, String mk){
        boolean check= true;
        if(sqlHelper==null) sqlHelper= new SQLHelper(context);

           sqlHelper.insertUser(tdn, mk);
           iLogin.onLogin(new User(tdn,mk));



    }
    public void checkLogin(String tdn, String mk){
        try{
            if(sqlHelper==null) sqlHelper= new SQLHelper(context);
            User user= sqlHelper.checkLogin(tdn, mk);
            if(user!=null) iLogin.onLogin(user);
            else iLogin.onMessenger("Không có tài khoản này");
        }
        catch (Exception exception){

        }

    }
}
