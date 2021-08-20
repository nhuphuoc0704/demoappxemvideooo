package vip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.R;

import java.util.List;

public class AdapterThanhToanVip extends RecyclerView.Adapter<AdapterThanhToanVip.ViewHolder> {
    List<ItemThanhToan> list;
    Context context;
    IOnThanhToan iOnThanhToan;
    public AdapterThanhToanVip(Context context){
        this.context= context;

    }
    public void setData(List<ItemThanhToan>  list){
        this.list= list;
    }
    public  void setiOnThanhToan(IOnThanhToan iOnThanhToan){
        this.iOnThanhToan= iOnThanhToan;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoanvip,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ItemThanhToan itemThanhToan= list.get(position);
            holder.imgLoaithanhtoan.setImageResource(itemThanhToan.getImg());
            holder.tvLoaithanhtoan.setText(itemThanhToan.getName());
            holder.imgLoaithanhtoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnThanhToan.onImage();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLoaithanhtoan;
        ImageView imgLoaithanhtoan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLoaithanhtoan= itemView.findViewById(R.id.tvLoaiThanhtoan);
            imgLoaithanhtoan= itemView.findViewById(R.id.imgLoaiThanhtoan);
        }
    }
}
