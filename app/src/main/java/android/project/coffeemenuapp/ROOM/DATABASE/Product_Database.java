package android.project.coffeemenuapp.ROOM.DATABASE;

import static android.project.coffeemenuapp.ROOM.DATABASE.Product_Database.Database_Version;

import android.content.Context;
import android.project.coffeemenuapp.ROOM.DAO.Product_DAO;
import android.project.coffeemenuapp.ROOM.ENTITY.Product_Entity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product_Entity.class}, version = Database_Version)
public abstract class Product_Database extends RoomDatabase {
    public static final int Database_Version = 1;
    public static final String databaseName = "My_Product_Database";
    public abstract Product_DAO productDAO();
    private static volatile Product_Database instanceDatabase;
    private static final int NumberOfThreading = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NumberOfThreading);

    public static Product_Database getInstanceDatabase(final Context context){
        if(instanceDatabase == null){
            synchronized (Product_Database.class){
                if(instanceDatabase == null){
                    instanceDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                                            Product_Database.class,databaseName)
                                                            .addCallback(clearDataCallback)
                                                            .build();
                }
            }
        }
        return instanceDatabase;
    }

    private static final RoomDatabase.Callback clearDataCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                Product_DAO productDao = instanceDatabase.productDAO();
                productDao.deleteAllProducts();
            });
        }
    };
}
