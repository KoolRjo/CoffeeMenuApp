package android.project.coffeemenuapp.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.project.coffeemenuapp.NAVIGATION.NavigationHost;
import android.project.coffeemenuapp.Permission.UserPermission;
import android.project.coffeemenuapp.R;
import android.project.coffeemenuapp.ROOM.ENTITY.Product_Entity;
import android.project.coffeemenuapp.ROOM.MODEL.Product_ViewModel;
import android.project.coffeemenuapp.databinding.FragmentBuildMenuBinding;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Objects;

public class fragment_build_menu extends Fragment {
    private FragmentBuildMenuBinding binding;
    protected Context mContext;
    protected Product_ViewModel productViewModel ;
    protected ImageView imgProduct;
    protected ImageButton btnCamera;
    protected MaterialButton btnReset;
    protected MaterialButton btnSubmit;
    protected TextInputLayout inputLayoutProductNameVN;
    protected TextInputEditText edtProductNameVN;
    protected TextInputLayout inputLayoutProductNameEN;
    protected TextInputEditText edtProductNameEN;
    protected TextInputLayout inputLayoutProductPrice;
    protected TextInputEditText edtProductPrice;
    private static final String TAG = fragment_build_menu.class.getSimpleName();
    private Uri uri;
    private static int productID;
    UserPermission user_permission = new UserPermission();
    protected ActivityResultLauncher<Intent> resultLauncher;
    //Init all views on UI
    private void getAllViews(){
        imgProduct = binding.imgBuildMenuImage;
        btnCamera = binding.btnCamera;
        btnReset = binding.btnBuildMenuCancel;
        btnSubmit = binding.btnBuildMenuSubmit;
        inputLayoutProductNameVN = binding.inputLayoutBuildMenuNameVN;
        edtProductNameVN = binding.edtBuildMenuNameVN;
        inputLayoutProductNameEN = binding.inputLayoutBuildMenuNameEN;
        edtProductNameEN = binding.edtBuildMenuNameEN;
        inputLayoutProductPrice = binding.inputLayoutBuildMenuPrice;
        edtProductPrice = binding.edtBuildMenuPrice;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    //Checking whether imageView has a ImageBitmap or not
    private Boolean hasImageBitmap(ImageView img){
        Drawable drawable = img.getDrawable();
        boolean hasImage = false;
        if((drawable instanceof BitmapDrawable)){
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }
    //Checking whether all input field is valid
    private Boolean isAllFieldsValid(){
            if(!hasImageBitmap(imgProduct)){
                Toast.makeText(mContext,getString(R.string.error_incorrectImage),Toast.LENGTH_LONG).show();
                return false;
            }
            if(TextUtils.isEmpty(edtProductNameVN.getText())){
                inputLayoutProductNameVN.setError(getString(R.string.error_NoText_notification));
                return false;
            }else if(!TextUtils.isEmpty(edtProductNameVN.getText()) && TextUtils.getTrimmedLength(edtProductNameVN.getText())>20){
                inputLayoutProductNameVN.setError(getString(R.string.error_incorrectMaxLength_notification));
                return false;
            }
            if(TextUtils.isEmpty(edtProductNameEN.getText())){
                inputLayoutProductNameEN.setError(getString(R.string.error_NoText_notification));
                return false;
            }else if(!TextUtils.isEmpty(edtProductNameEN.getText()) && TextUtils.getTrimmedLength(edtProductNameEN.getText())>20){
                inputLayoutProductNameEN.setError(getString(R.string.error_incorrectMaxLength_notification));
                return false;
            }
            if(TextUtils.isEmpty(edtProductPrice.getText())){
                inputLayoutProductPrice.setError(getString(R.string.error_NoText_notification));
                return false;
            }else if(!TextUtils.isEmpty(edtProductPrice.getText()) && (TextUtils.getTrimmedLength(edtProductPrice.getText())>6 || !TextUtils.isDigitsOnly(edtProductPrice.getText()))){
                inputLayoutProductPrice.setError(getString(R.string.error_incorrectProductPrice));
                return false;
            }
        return true;
    }
    //Validate text input while typing
    private void OnTypingValidate(TextInputLayout textInputLayout,TextInputEditText editText,int MaxCount){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    textInputLayout.setError(getString(R.string.error_NoText_notification));
                }else if(!TextUtils.isEmpty(s) && TextUtils.getTrimmedLength(s) > MaxCount){
                    textInputLayout.setError(getString(R.string.error_incorrectMaxLength_notification));
                }else{
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //Validate the price input while typing
    private void OnTypingPriceValidate(TextInputLayout inputLayout, TextInputEditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    inputLayout.setError(getString(R.string.error_NoText_notification));
                }else if(!TextUtils.isEmpty(s) && (TextUtils.getTrimmedLength(s) > 6 || !TextUtils.isDigitsOnly(s))){
                    inputLayout.setError(getString(R.string.error_incorrectProductPrice));
                }else{
                    inputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //Get an image from your device gallery
    private void getImageFromGallery(ImageView imageView){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            uri = result.getData().getData();
                            new Thread(() -> {
                                try {
                                        //ImageDecoder.Source source = ImageDecoder.createSource(requireActivity().getContentResolver(),uri);
                                        //final Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),uri);
                                        imageView.post(() -> imageView.setImageBitmap(bitmap));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                    }
                }
        );
    }
    //create a random ID binding to a new product
    private int createProductID(){
        return (int)((Math.random()*900000)+100000);
    }
    //Add a new drink into database
    private void insertNewDrink(){
        btnSubmit.setOnClickListener(v->{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(()->{
                if(isAllFieldsValid()){
                    productID = createProductID();
                    Log.i(TAG,"ID after filter: "+productID);
                    String productNameVN = Objects.requireNonNull(edtProductNameVN.getText()).toString().trim();
                    String productNameEN = Objects.requireNonNull(edtProductNameEN.getText()).toString().trim();
                    Integer productPrice = Integer.parseInt(Objects.requireNonNull(edtProductPrice.getText()).toString().trim());
                    Product_Entity product = new Product_Entity(productID,productNameVN,productNameEN,productPrice,uri.toString());
                    productViewModel.insertProduct(product);
                    Log.i(TAG,"Image's Uri: "+uri.toString());
                    ((NavigationHost)requireActivity()).navigateTo(R.id.lao_display_list,new fragment_list_drinks_food(),true);
                }else{
                    Toast.makeText(mContext,getString(R.string.warning_fillUpProductInfor),Toast.LENGTH_SHORT).show();
                    Log.i(TAG,getString(R.string.warning_fillUpProductInfor));
                }
            });
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentBuildMenuBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        getAllViews();
        productViewModel = new ViewModelProvider(requireActivity()).get(Product_ViewModel.class);
        user_permission.readExternalStoragePermission(mContext,requireActivity());
        getImageFromGallery(imgProduct);
        btnCamera.setOnClickListener(v->{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(()->{
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                if(intent.resolveActivity(requireActivity().getPackageManager())!=null){
                    resultLauncher.launch(intent);
                }
            });
        });
        insertNewDrink();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        OnTypingValidate(inputLayoutProductNameVN,edtProductNameVN,inputLayoutProductNameVN.getCounterMaxLength());
        OnTypingValidate(inputLayoutProductNameEN,edtProductNameEN,inputLayoutProductNameEN.getCounterMaxLength());
        OnTypingPriceValidate(inputLayoutProductPrice,edtProductPrice);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
