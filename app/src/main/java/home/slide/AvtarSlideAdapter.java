package home.slide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.xemxem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import home.item.video.Video;
import watchvideo.IOnItemClickVideo;

public class AvtarSlideAdapter extends RecyclerView.Adapter<AvtarSlideAdapter.ViewHolder> {
    Context context;
    List<Video> list;
    IOnItemClickVideo iOnItemClickVideo;

    public AvtarSlideAdapter(Context context) {
        this.context = context;

    }
    public void setData(List<Video> list){
        this.list= list;
    }

    public void setiOnItemClickVideo(IOnItemClickVideo iOnItemClickVideo){
        this.iOnItemClickVideo= iOnItemClickVideo;
    }
    public List<Video> getList(){
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_pager,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video= list.get(position);
        holder.textView.setText(position+1+"");
        Picasso.with(context).load(video.getAvatar()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnItemClickVideo.onVideoAvatar(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imgItemSilde);
            textView= itemView.findViewById(R.id.tvTopNumber);
        }
    }
}
