package home;

import android.content.pm.ActivityInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import home.category.Category;
import home.item.video.Video;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIController implements Callback<List<Video>> {
    private String url;
    public List<Category> list;
    IHomeVideo iHomeVideo;
    private String[] listURL={"TrailerFilm","Game","Musics","News"};
    public APIController (  String url,IHomeVideo iHomeVideo,List<Category> categoryList){
        this.iHomeVideo= iHomeVideo;
        this.list= categoryList;
        this.url= url;
    }

    public void start(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://demo1908811.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GetAPI getAPI= retrofit.create(GetAPI.class);

            Call<List<Video>> call =getAPI.loadVideo(url);
            call.enqueue(this);



    }
    @Override
    public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
        if(response.isSuccessful()) {
            List<Video> videoList = response.body();
            list.add(new Category(videoList,url));
            if(list.size()==4)
                iHomeVideo.onGetDataVideoHome(list);
            //iHomeVideo.onGetListVideo(videoList);
        } else {
            System.out.println(response.errorBody());
        }
    }


    @Override
    public void onFailure(Call<List<Video>> call, Throwable t) {
            t.printStackTrace();
    }
}
