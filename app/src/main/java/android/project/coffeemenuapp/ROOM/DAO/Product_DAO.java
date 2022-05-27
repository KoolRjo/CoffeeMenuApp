package android.project.coffeemenuapp.ROOM.DAO;

import android.project.coffeemenuapp.ROOM.ENTITY.Product_Entity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Product_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product_Entity product);
    @Update
    void updateProduct(Product_Entity product);
    @Delete
    void deleteProduct(Product_Entity product);
    @Query("DELETE FROM products")
    void deleteAllProducts();
    @Query("SELECT*FROM products")
    LiveData<List<Product_Entity>> getAllProducts();
}
