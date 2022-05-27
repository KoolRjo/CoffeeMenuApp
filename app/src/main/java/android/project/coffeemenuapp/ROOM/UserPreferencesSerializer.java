package android.project.coffeemenuapp.ROOM;

import android.project.coffeemenuapp.proto.UserPrefs;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;
import androidx.datastore.rxjava3.RxDataStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class UserPreferencesSerializer implements Serializer<UserPrefs> {
    private static final String TAG = UserPreferencesSerializer.class.getSimpleName();
    @Override
    public UserPrefs getDefaultValue() {
        return UserPrefs.getDefaultInstance();
    }

    @Nullable
    @Override
    public UserPrefs readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super UserPrefs> continuation) {
        try {
            return UserPrefs.parseFrom(inputStream);
        } catch (IOException e) {
            Log.i(TAG,"Cannot read from Proto _ Exception: "+e);
        }
        return null;
    }

    @Nullable
    @Override
    public UserPrefs writeTo(UserPrefs userPrefs, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            userPrefs.writeTo(outputStream);
        } catch (IOException e) {
            Log.i(TAG,"Cannot write to Proto _ Exception: "+e);
        }
        return null;
    }
}
