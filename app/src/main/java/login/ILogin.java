package login;

import model.User;

public interface ILogin {
    void onSuccessFul();
    void onMessenger(String mes);
    void onLogin(User user);
}
