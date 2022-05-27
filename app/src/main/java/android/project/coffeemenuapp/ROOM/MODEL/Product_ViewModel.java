package android.project.coffeemenuapp.ROOM.MODEL;

import android.app.Application;
import android.project.coffeemenuapp.ROOM.ENTITY.Product_Entity;
import android.project.coffeemenuapp.ROOM.REPOSITORY.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class Product_ViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> productID;
    public MutableLiveData<Integer> getProductID(){
        if(productID==null){
            productID = new MutableLiveData<>();
        }
        return productID;
    }

    private final Repository repository;
    private final LiveData<List<Product_Entity>> getAllProducts;

    public Product_ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        getAllProducts = repository.getAllProducts();
    }
    public LiveData<List<Product_Entity>>  getAllProducts(){
        return getAllProducts;
    }
    public void insertProduct(Product_Entity product){
        repository.insertProduct(product);
    }
    public void updateProduct(Product_Entity product){
        repository.updateProduct(product);
    }
    public void deleteProduct(Product_Entity product){
        repository.deleteProduct(product);
    }
}
