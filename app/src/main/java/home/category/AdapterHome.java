package home.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;

import java.util.List;

import home.item.video.AdapterVideoItem;
import home.slide.AvtarSlideAdapter;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {
    public static final int SLIDE= 1;
    public static final int CATEGORY= 2;
    Context context;
    List<Data> list;
    MainActivity mainActivity;

    public AdapterHome(Context context,MainActivity mainActivity){
        this.context=context;
        this.mainActivity= mainActivity;
    }
    public void setData(List<Data> list){
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data= list.get(position);
        if(data.getType()==1){
            LinearLayoutManager manager= new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.recyclerView.setLayoutManager(manager);
            AvtarSlideAdapter avtarSlideAdapter= new AvtarSlideAdapter(context);
            avtarSlideAdapter.setData(data.getList());
            holder.recyclerView.setAdapter(avtarSlideAdapter);
        }
        else  if(data.getType()==2){
            LinearLayoutManager manager= new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
            holder.recyclerView.setLayoutManager(manager);
            AdapterCategory adapterCategory= new AdapterCategory(context,mainActivity);
//            adapterCategory.setData(data.getList());
//            holder.recyclerView.setAdapter(avtarSlideAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView= itemView.findViewById(R.id.rclv);
        }
    }
}
