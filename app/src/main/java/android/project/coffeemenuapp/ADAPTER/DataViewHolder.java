package android.project.coffeemenuapp.ADAPTER;

import android.graphics.Bitmap;
import android.project.coffeemenuapp.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;

public class DataViewHolder extends RecyclerView.ViewHolder {
    //Get all itemViews to holder data
        private final ImageView productImage;
        private final TextView productNameVN;
        private final TextView productNameEN;
        private final TextView productPrice;
    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.img_list_drink_detail);
        productNameVN = itemView.findViewById(R.id.tv_list_drinks_product_nameVN);
        productNameEN = itemView.findViewById(R.id.tv_list_drinks_product_nameEN);
        productPrice = itemView.findViewById(R.id.tv_list_drinks_product_price);
    }
    //Binding data to itemView
    public void bindTo(Bitmap image, String strProductNameVN, String strProductNameEN, String strProductPrice){
        productImage.setImageBitmap(image);
        productNameVN.setText(strProductNameVN);
        productNameEN.setText(strProductNameEN);
        productPrice.setText(MessageFormat.format("{0}VNĐ", strProductPrice));
    }
}
