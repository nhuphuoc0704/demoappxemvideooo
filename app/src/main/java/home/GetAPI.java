package home;

import java.util.List;

import home.item.video.Video;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetAPI {
    @GET("{title}")
    Call<List<Video>> loadVideo(@Path("title") String str);

}
