package com.example.xemxem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.xemxem.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

import favorite.FragmentFavorite;
import history.FragmentHistory;
import home.FragmentHome;
import home.item.video.Video;
import login.LoginActivity;
import model.User;
import profile.FragmentDangNhapThanhCong;
import profile.FragmentProfile;
import vip.FragmentThanhToanVip;
import watchvideo.FragmentListVideo;

import static com.example.xemxem.MainActivity2.SHARED_PREF_NAME;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Video> list;
    User user;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sharedPreferences= getBaseContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE) ;
        editor= sharedPreferences.edit();
        if(sharedPreferences.getBoolean("isLogin",false)){
            String tdn= sharedPreferences.getString("username","");
            String mk= sharedPreferences.getString("password","");
            int vip= sharedPreferences.getInt("vip",0);
            user= new User(tdn,mk);
            user.setVip(vip);
        }


        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        getFragment(FragmentHome.newInstance());

                        break;
                    case R.id.action_trending:
                        getFragment(FragmentHistory.newInstance());
                        break;
                    case R.id.action_favourite:
                        getFragment(FragmentFavorite.newInstance());
                        break;
                    default:
                        getFragment(FragmentHome.newInstance());
                        break;
                }

                return true;
            }
        });
    }

    public void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }
    public void getFragmentBackStack(FragmentThanhToanVip fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(FragmentThanhToanVip.TAG).commit();

    }
    public void gotoFragmentListVideo(String s, List<Video> videoList) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentListVideo fragmentListVideo = FragmentListVideo.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listvideo", (Serializable) videoList);
        bundle.putSerializable("title", (Serializable) s);
        fragmentListVideo.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, fragmentListVideo);
        fragmentTransaction.addToBackStack(FragmentListVideo.TAG);
        fragmentTransaction.commit();
    }
    public void gotoFragmentThanhToanVip(){

    }

    public void gotoFragmentWatchVideo(int position,Video video, List<Video> list) {
        Intent i= new Intent(getBaseContext(),MainActivity2.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("position",position);
        bundle.putSerializable("video",video);
        bundle.putSerializable("list", (Serializable) list);
        i.putExtras(bundle);
        startActivity(i);
    }
    public void gotoLoginActivity(){
        Intent i= new Intent(getBaseContext(), LoginActivity.class);
        startActivityForResult(i,11);

    }
    public List<Video> getDataVideoList(List<Video> videoList) {
        list = videoList;
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==11){
                    String tdn= data.getStringExtra("tdn");
                    String mk= data.getStringExtra("mk");
                    int vip= data.getIntExtra("vip",0);
                    user= new User(tdn,mk);
                    user.setVip(vip);
                    editor.putString("username",tdn);
                    editor.putString("password",mk);
                    editor.putInt("vip",vip);
                    editor.putBoolean("isLogin",true);
                    if(vip==1)
                        editor.putBoolean("isPlayAd",true);
                    else editor.putBoolean("isPlayAd",false);
                    editor.apply();
                    getFragment(FragmentDangNhapThanhCong.newInstance());
            }
        }
    }
    public User getUser(){
        return user;
    }
    public User setVipUser(){
        editor.putBoolean("isPlayAd",true);
        editor.putInt("vip",1);
        editor.apply();
        user.setVip(1);
        return user;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_user:
                if(sharedPreferences.getBoolean("isLogin",false))getFragment(FragmentDangNhapThanhCong.newInstance());
                else  getFragment(FragmentProfile.newInstance());
                break;
            default: break;
        }
        return true;
    }
}
