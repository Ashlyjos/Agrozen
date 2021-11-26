package mca.project.agrozen;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> products = new ArrayList<>();

    public RecyclerAdapter (Context context,List<Product> products){
        this.mContext = context;
        this.products = products;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mname, mPrice, mloc, mdura, mphone;
        private ImageView mImageView;
        private LinearLayout mContainer;

        public MyViewHolder (View view){
            super(view);

            mname = view.findViewById(R.id.product_name);
            mImageView = view.findViewById(R.id.product_image);
            mphone = view.findViewById(R.id.product_phone);
            mPrice = view.findViewById(R.id.product_price);
            mloc = view.findViewById(R.id.product_location);
            mdura = view.findViewById(R.id.product_duration);
            mContainer = view.findViewById(R.id.product_container);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.products_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Product product = products.get(position);

        holder.mPrice.setText("Rupees "+product.getPrice());
        holder.mname.setText(product.getpdname());
        holder.mphone.setText(product.getphoneno());
        holder.mloc.setText(product.getLocation());
        holder.mdura.setText(product.getDuration());
        Glide.with(mContext).load(product.getImage()).into(holder.mImageView);

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,DetailedProductsActivity.class);
                intent.putExtra("productname",product.getpdname());
                intent.putExtra("duration",product.getDuration());
                intent.putExtra("location",product.getLocation());
                intent.putExtra("image",product.getImage());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("phonenumber",product.getphoneno());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}