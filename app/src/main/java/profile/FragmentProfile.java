package profile;

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
import com.example.xemxem.databinding.FragmentProfileBinding;

import model.User;
import vip.FragmentThanhToanVip;

public class FragmentProfile extends Fragment {
    FragmentProfileBinding binding;
    MainActivity mainActivity;
    User user;
    public static FragmentProfile newInstance() {

        Bundle args = new Bundle();

        FragmentProfile fragment = new FragmentProfile();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false);
        mainActivity= (MainActivity) getActivity();
        binding.btnChondangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.gotoLoginActivity();
            }
        });
        return binding.getRoot();
    }
}
