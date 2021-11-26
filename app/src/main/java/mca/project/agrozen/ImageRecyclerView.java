package mca.project.agrozen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageRecyclerView extends RecyclerView.Adapter<ImageRecyclerView.ViewHolderAdapter> {

    Context c1;
    ArrayList<String> imgurllist = new ArrayList<>();
    ArrayList<String> namellist = new ArrayList<>();
    ArrayList<String> resultllist = new ArrayList<>();

    public ImageRecyclerView(Context c1, ArrayList<String> imgurllist, ArrayList<String> namellist, ArrayList<String> resultllist) {
        this.c1 = c1;
        this.imgurllist = imgurllist;
        this.namellist = namellist;
        this.resultllist = resultllist;
    }

    @NonNull
    @Override
    public ViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater ly = LayoutInflater.from(parent.getContext());
        View v = ly.inflate(R.layout.piclist , parent , false);

        return new ViewHolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapter holder, int position) {

        holder.name.setText(namellist.get(position));
        holder.result.setText(resultllist.get(position));

        Glide.with(c1).load(imgurllist.get(position)).into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return namellist.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {

        ImageView pic;
        TextView name,result;

        public ViewHolderAdapter(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.imglist_img);
            name = itemView.findViewById(R.id.imglist_dname);
            result = itemView.findViewById(R.id.imglist_result);
        }
    }

}
