package android.project.coffeemenuapp.Permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class UserPermission extends AppCompatActivity {
    public static final int request_permission_CAMERA = 1;
    public static final int request_permission_READ_EXTERNAL_STORAGE = 2;
    //Self check using Camera permission
    public void onCameraPermission(Context context, Activity activity){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.CAMERA)){
                Toast.makeText(context,"Ứng dụng cần được cấp quyền để sử dụng CAMERA",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},request_permission_CAMERA);
            }
        }
    }
    //Self check READ EXTERNAL STORAGE permission
    public void readExternalStoragePermission(Context context, Activity activity){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(context,"Ứng dụng cần được cấp quyền để đọc bộ sưu tập ",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},request_permission_READ_EXTERNAL_STORAGE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case request_permission_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Bạn đã xác nhận sử dụng Camera trong ứng dụng",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Xin lỗi bạn vì sự làm phiền",Toast.LENGTH_SHORT).show();
                }
                break;
            case request_permission_READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Bạn đã xác nhận cho phép ứng dụng truy cập bộ sưu tập",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Xin lỗi bạn vì sự làm phiền",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
