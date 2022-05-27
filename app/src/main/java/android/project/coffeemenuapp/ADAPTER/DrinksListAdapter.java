package android.project.coffeemenuapp.ADAPTER;

import android.graphics.Bitmap;
import android.net.Uri;
import android.project.coffeemenuapp.R;
import android.project.coffeemenuapp.ROOM.ENTITY.Product_Entity;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;


public class DrinksListAdapter extends ListAdapter<Product_Entity,DataViewHolder> {
    private final AsyncListDiffer<Product_Entity> mDiffer = new AsyncListDiffer<>(this, productDiffCallback);
    private ViewGroup viewGroup;
    //private DataViewHolder holder ;
    public DrinksListAdapter(@NonNull DiffUtil.ItemCallback<Product_Entity> diffCallback) {
        super(diffCallback);
    }
    //Counting the number of product was stored in database
    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().isEmpty() ? 0 : mDiffer.getCurrentList().size();
    }
    //Dispatching the new list on UI
    @Override
    public void submitList(@Nullable List<Product_Entity> list) {
        super.submitList(list);
        mDiffer.submitList(list);
    }
    //get Image from String Uri
    private Bitmap getImageBitmapFromUri(String stringURI,ViewGroup parent){
        Bitmap bitmap;
        try {
            Uri uri = Uri.parse(stringURI);
            bitmap = MediaStore.Images.Media.getBitmap(parent.getContext().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }
    //Binding data to ViewHolder
    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Product_Entity product = mDiffer.getCurrentList().get(position);
        holder.bindTo(getImageBitmapFromUri(product.getImage(),viewGroup),product.getNameVN(),product.getNameEN(),Integer.toString(product.getPrice()));
    }
    //Get viewType by Layout's id
    @Override
    public int getItemViewType(int position) {
        return R.layout.product_detail_cardview;
    }
    //Handle even onClick in itemView
    private void onClickItemView(RecyclerView rv, View itemView){
        int pos = rv.getChildLayoutPosition(itemView);
        if(pos != -1){
            String productNameVN = mDiffer.getCurrentList().get(pos).getNameVN();
            Toast.makeText(viewGroup.getContext(),productNameVN, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(viewGroup.getContext(),"San pham da bi xoa khoi Menu",Toast.LENGTH_LONG).show();
        }
    }
    //Create ViewHolder
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        viewGroup = parent;
        final int layoutID = R.layout.product_detail_cardview;
        if (viewType == layoutID) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(viewType, parent, false);
            itemView.setOnClickListener(v-> onClickItemView((RecyclerView) parent,itemView));
        } else {
            throw new IllegalStateException("Unexpected viewType's value: " + viewType);
        }

        return new DataViewHolder(itemView);
    }
    //Now we check whether the difference from old list and new list
    public static final DiffUtil.ItemCallback<Product_Entity> productDiffCallback = new DiffUtil.ItemCallback<Product_Entity>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product_Entity oldItem, @NonNull Product_Entity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product_Entity oldItem, @NonNull Product_Entity newItem) {
            return  oldItem.getNameVN().equalsIgnoreCase(newItem.getNameVN()) &&
                    oldItem.getNameEN().equalsIgnoreCase(newItem.getNameEN()) &&
                    oldItem.getPrice().equals(newItem.getPrice());
        }
    };

}
