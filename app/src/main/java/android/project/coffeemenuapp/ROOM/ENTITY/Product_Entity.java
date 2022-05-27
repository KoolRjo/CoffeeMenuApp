package android.project.coffeemenuapp.ROOM.ENTITY;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "products",
        indices = {@Index("productNameVN"),
                   @Index(value = {"productNameEN","productPrice"})})
public class Product_Entity {
    @PrimaryKey
    @ColumnInfo(name = "productID")
    private int id;
    @ColumnInfo(name = "productNameVN")
    private String nameVN;
    @ColumnInfo(name = "productNameEN")
    private String nameEN;
    @ColumnInfo(name = "productPrice")
    private Integer price;
    @ColumnInfo(name = "productImage")
    private String image;
    //Constructor
    public Product_Entity() {
    }
    //Constructor with argument
    public Product_Entity(int id, String nameVN, String nameEN, Integer price, String image) {
        this.id = id;
        this.nameVN = nameVN;
        this.nameEN = nameEN;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameVN() {
        return nameVN;
    }

    public void setNameVN(String nameVN) {
        this.nameVN = nameVN;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
