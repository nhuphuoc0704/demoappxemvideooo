package profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;
import com.example.xemxem.databinding.FragmentLoginSuccessfulBinding;

import home.FragmentHome;
import model.User;
import vip.FragmentThanhToanVip;

import static com.example.xemxem.MainActivity2.SHARED_PREF_NAME;

public class FragmentDangNhapThanhCong extends Fragment {
    FragmentLoginSuccessfulBinding binding;
    MainActivity mainActivity;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    User user;
    public static FragmentDangNhapThanhCong newInstance() {

        Bundle args = new Bundle();

        FragmentDangNhapThanhCong fragment = new FragmentDangNhapThanhCong();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_login_successful,container,false);
        mainActivity= (MainActivity) getActivity();
        user= mainActivity.getUser();
        if(user.getVip()==1){
            binding.tvTentaikhoanDangnhap.setText("Welcome Back "+user.getTdn()+"(VIP MEMBER)");
            binding.tvNoidung.setText(getResources().getString(R.string.boquangcaothanhcong));
            binding.btnNangcapVIP.setVisibility(View.GONE);
        }
        else binding.tvTentaikhoanDangnhap.setText("Welcome "+ user.getTdn());
        binding.btnNangcapVIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragmentBackStack(FragmentThanhToanVip.newInstance());

            }
        });

        binding.btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                editor= sharedPreferences.edit();
                editor.putString("username",null);
                editor.putString("password",null);
                editor.putBoolean("isLogin",false);
                editor.putBoolean("isPlayAd",false);
                editor.putInt("vip",0);
                editor.apply();
                mainActivity.getFragment(FragmentProfile.newInstance());
            }
        });
        return binding.getRoot();
    }
}
