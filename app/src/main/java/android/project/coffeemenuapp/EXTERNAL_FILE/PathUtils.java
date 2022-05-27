package android.project.coffeemenuapp.EXTERNAL_FILE;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;

import java.io.File;

public class PathUtils {
    public File getAppExternalFileDir(Context context,String typeDir){
        if(Build.VERSION.SDK_INT >= 29){
            return context.getExternalFilesDir(typeDir);
        }
        return Environment.getExternalStorageDirectory();
    }
    public static String getPath(final Context context, final Uri uri){
        if(DocumentsContract.isDocumentUri(context, uri)){

        }
        return null;
    }
}
