package home;

import java.util.List;

import home.category.Category;
import home.item.video.Video;

public interface IHomeVideo {
    void onSuccessFul();
    void onMessenger(String mes);
    void onGetDataVideoHome(List<Category> list);
    void onGetListVideo(List<Video> list);

}
