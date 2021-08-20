package home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import home.category.IOnItemClickCategory;
import com.example.xemxem.MainActivity;

import com.example.xemxem.R;
import home.item.video.Video;
import com.example.xemxem.databinding.FragmentHomeBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import home.category.AdapterCategory;
import home.category.Category;
import home.slide.AvtarSlideAdapter;
import me.relex.circleindicator.CircleIndicator;
import watchvideo.FragmentListVideo;
import watchvideo.IOnItemClickVideo;

public class FragmentHome extends Fragment implements IHomeVideo  {

    AvtarSlideAdapter avtarSlideAdapter;
    FragmentHomeBinding binding;
    AdapterCategory adapterCategory;
    List<Category> categoryList;
    MainActivity mainActivity;
    List<Video> videoGame;
    List<Video> videoMovie;
    List<Video> videoMusic;
    List<Video> videoNew;
    PresenterHomeVideo presenterHomeVideo;
    String urlStringFilm = "TrailerFilm";
    String urlStringGame = "Game";
    String urlStringMusic = "Musics";
    String urlStringNew = "News";
    List<String> list;
    Timer timer;
    List<Video> listVideo;
    ConcatAdapter concatAdapter;
    public static FragmentHome newInstance() {

        Bundle args = new Bundle();

        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mainActivity= (MainActivity) getActivity();
        list= new ArrayList<>();
        list.add(urlStringGame);
        list.add(urlStringFilm);
        list.add(urlStringMusic);
        list.add(urlStringNew);
        mainActivity= (MainActivity) getActivity();
        adapterCategory= new AdapterCategory(getContext(), mainActivity);
        avtarSlideAdapter= new AvtarSlideAdapter(getContext());

        presenterHomeVideo= new PresenterHomeVideo(this);
        presenterHomeVideo.onGetListVideo(list);
        binding.progressbar.setVisibility(View.VISIBLE);

        adapterCategory.setIOnItemClickVideo(new IOnItemClickCategory() {
            @Override
            public void onTitleCategory(String s,List<Video> videoList) {
                mainActivity.gotoFragmentListVideo(s,videoList);
            }
        });

        avtarSlideAdapter.setiOnItemClickVideo(new IOnItemClickVideo() {
            @Override
            public void onVideoAvatar(int position) {
                mainActivity.gotoFragmentWatchVideo(position,avtarSlideAdapter.getList().get(position),avtarSlideAdapter.getList());
            }

            @Override
            public void onTitleVideo(int position) {

            }
        });
        return binding.getRoot();
    }


    @Override
    public void onSuccessFul() {
        Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessenger(String mes) {

    }

    @Override
    public void onGetDataVideoHome(List<Category> list) {
        binding.progressbar.setVisibility(View.GONE);
        binding.tvTopVideo.setText("Top Trending");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rclvCategoty.setLayoutManager(linearLayoutManager);
        binding.rclvCategoty.setFocusable(false);
        binding.rclvCategoty.setNestedScrollingEnabled(false);

        adapterCategory.setData(list);
        binding.rclvCategoty.setAdapter(adapterCategory);
        LinearLayoutManager linearLayoutManager1= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        List<Video> listVideo= list.get(0).getVideoList();
        binding.rclvSlide.setLayoutManager(linearLayoutManager1);
        binding.rclvSlide.setFocusable(false);
        binding.rclvSlide.setNestedScrollingEnabled(false);

        avtarSlideAdapter.setData(listVideo);
        binding.rclvSlide.setAdapter(avtarSlideAdapter);
            LinearSnapHelper linearSnapHelper= new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(binding.rclvSlide);
            autoSlideAvatar(linearLayoutManager1);


        //binding.indicator.setViewPager(binding.viewpager);
        //avtarSlideAdapter.registerDataSetObserver(binding.indicator.getDataSetObserver());

        //autoSlideAvatar(listVideo);

    }

    @Override
    public void onGetListVideo(List<Video> list) {

    }

    private  void autoSlideAvatar(LinearLayoutManager linearLayoutManager1){

        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(linearLayoutManager1.findLastCompletelyVisibleItemPosition()<avtarSlideAdapter.getItemCount()-1){
                    linearLayoutManager1.smoothScrollToPosition(binding.rclvSlide,new RecyclerView.State(),
                            linearLayoutManager1.findLastCompletelyVisibleItemPosition()+1);
                }
                else {
                    linearLayoutManager1.smoothScrollToPosition(binding.rclvSlide,new RecyclerView.State(),
                            0);
                }
                if(linearLayoutManager1.findLastCompletelyVisibleItemPosition()<avtarSlideAdapter.getItemCount()-1);
            }
        },1000,4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!= null) {
            timer.cancel();
            timer=null;
        }
    }
}
