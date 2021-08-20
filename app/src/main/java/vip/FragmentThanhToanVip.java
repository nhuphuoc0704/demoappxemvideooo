package vip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;
import com.example.xemxem.databinding.FragmentThanhtoanvipBinding;

import java.util.ArrayList;
import java.util.List;

import profile.FragmentDangNhapThanhCong;
import watchvideo.AdapterListVideo;

public class FragmentThanhToanVip extends Fragment implements IThanhToan {
    FragmentThanhtoanvipBinding binding;
    List<ItemThanhToan> list;
    AdapterThanhToanVip adapterThanhToanVip;
    MainActivity mainActivity;
    PresenterThanhToan presenterThanhToan;
    public static  final String TAG= FragmentThanhToanVip.class.getName();

    public static FragmentThanhToanVip newInstance() {

        Bundle args = new Bundle();

        FragmentThanhToanVip fragment = new FragmentThanhToanVip();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_thanhtoanvip,container,false);
        list= new ArrayList<>();
        getData();
        adapterThanhToanVip= new AdapterThanhToanVip(getContext());
        LinearLayoutManager linearLayoutManager= new GridLayoutManager(getContext(),2, RecyclerView.VERTICAL,false);
        adapterThanhToanVip.setData(list);
        presenterThanhToan= new PresenterThanhToan(this,getContext());
        binding.reclThanhtoanVip.setLayoutManager(linearLayoutManager);
        binding.reclThanhtoanVip.setAdapter(adapterThanhToanVip);
        mainActivity= (MainActivity) getActivity();
        adapterThanhToanVip.setiOnThanhToan(new IOnThanhToan() {
            @Override
            public void onImage() {
                mainActivity.setVipUser();
                presenterThanhToan.upDateVipSuccess(mainActivity.getUser().getTdn());
            }
        });
        binding.imgbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return binding.getRoot();
    }
    private void getData(){
        list.add(new ItemThanhToan(R.drawable.viettek,"Viettel"));
        list.add(new ItemThanhToan(R.drawable.vinaphone,"Vinaphone"));
        list.add(new ItemThanhToan(R.drawable.mobiphone,"Mobiphone"));
        list.add(new ItemThanhToan(R.drawable.zingcard,"Zing card"));
        list.add(new ItemThanhToan(R.drawable.garena,"Garena card"));
        list.add(new ItemThanhToan(R.drawable.bank,"Bank"));
    }

    @Override
    public void onSuccessFull() {
        Toast.makeText(getContext(),"Nâng cấp VIP MEMBER thành công",Toast.LENGTH_LONG).show();
        mainActivity.getFragment(FragmentDangNhapThanhCong.newInstance());
    }

    @Override
    public void onMessenger(String mes) {

    }
}
