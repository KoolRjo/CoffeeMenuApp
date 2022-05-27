package android.project.coffeemenuapp.ROOM.REPOSITORY;

import android.app.Application;
import android.project.coffeemenuapp.ROOM.DAO.Product_DAO;
import android.project.coffeemenuapp.ROOM.DATABASE.Product_Database;
import android.project.coffeemenuapp.ROOM.ENTITY.Product_Entity;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private final Product_DAO mProductDao;
    private final LiveData<List<Product_Entity>> getAllProducts;

    public Repository(Application application) {
        Product_Database productDatabase = Product_Database.getInstanceDatabase(application);
        mProductDao = productDatabase.productDAO();
        getAllProducts = mProductDao.getAllProducts();
    }
    public LiveData<List<Product_Entity>> getAllProducts(){
        return getAllProducts;
    }
    public void insertProduct(Product_Entity product){
        Product_Database.databaseWriteExecutor.execute(()-> mProductDao.insertProduct(product));
    }
    public void updateProduct(Product_Entity product){
        Product_Database.databaseWriteExecutor.execute(()-> mProductDao.updateProduct(product));
    }
    public void deleteProduct(Product_Entity product){
        Product_Database.databaseWriteExecutor.execute(()-> mProductDao.deleteProduct(product));
    }
}
