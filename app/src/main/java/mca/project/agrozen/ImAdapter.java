package mca.project.agrozen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> imglist;
    ArrayList<String> rslist;
    ArrayList<String> dnlist;

    LayoutInflater ly;

    ImageView img;

    View v;

    public ImAdapter(Context c, ArrayList<String> imglist , ArrayList<String> rslist , ArrayList<String> dnlist ) {
        this.c = c;
        this.imglist = imglist;
        this.rslist = rslist;
        this.dnlist = dnlist;

        ly = (LayoutInflater.from(c));

    }

    @Override
    public int getCount() {
        return imglist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return imglist.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        v = ly.inflate(R.layout.piclist , null);

        img = v.findViewById(R.id.imglist_img);
        TextView txt = v.findViewById(R.id.imglist_result);
        TextView name = v.findViewById(R.id.imglist_dname);

        txt.setText(rslist.get(position));
        name.setText(dnlist.get(position));

        final String url = imglist.get(position);

        Picasso.with(c).load(url).into(img);

        return v;
    }
}
