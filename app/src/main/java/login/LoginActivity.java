package login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;
import com.example.xemxem.databinding.ActivityLoginBinding;

import java.io.Serializable;

import model.User;

public class LoginActivity extends AppCompatActivity implements ILogin{
    ActivityLoginBinding binding;
    PresenterLogin presenterLogin;
    EditText etTk;
    EditText etMK;
    EditText etXnMK;
    Button btnTao;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login);

        presenterLogin= new PresenterLogin(this,this);
        //presenterLogin.checkLogin(binding.etusername.getText().toString(),binding.etpassword.getText().toString());

        binding.btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLogin.checkLogin(binding.etusername.getText().toString(),
                        binding.etpassword.getText().toString());
            }
        });

        binding.btnTaotaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_create_user, viewGroup, false);
                builder.setView(dialogView);
                alertDialog= builder.create();
                alertDialog.show();
                btnTao= dialogView .findViewById(R.id.btnTao);
                etMK= dialogView .findViewById(R.id.ettaomk);
                etTk= dialogView .findViewById(R.id.etTaotdn);
                etXnMK= dialogView .findViewById(R.id.etxacnhanmk);
                btnTao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mk= etMK.getText().toString();
                        String xnMk= etXnMK.getText().toString();
                        String tdn= etTk.getText().toString();
                        if(mk.equals(xnMk))
                            presenterLogin.insertUser(tdn,mk);
                        else onMessenger("Mật khẩu không trùng khớp");
                    }
                });

            }
        });

    }

    @Override
    public void onSuccessFul() {
        Toast.makeText(getBaseContext(),"Tạo tài khoản thành công",Toast.LENGTH_LONG).show();
        alertDialog.dismiss();
    }

    @Override
    public void onMessenger(String mes) {
        Toast.makeText(getBaseContext(),mes,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogin(User user) {
//            binding.etusername.setText(etTk.getText().toString());
//            binding.etpassword.setText(etMK.getText().toString());
                    Intent i= new Intent();
                    i.putExtra("tdn",user.getTdn());
                    i.putExtra("mk",user.getMk());
                    i.putExtra("vip",user.getVip());
                    setResult(RESULT_OK,i);
                    finish();

    }


}