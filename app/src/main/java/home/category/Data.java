package home.category;

import java.util.List;

import home.item.video.Video;

public class Data {
    int type;
    List<Video> list;

    public Data(int type, List<Video> list) {
        this.type = type;
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }
}
