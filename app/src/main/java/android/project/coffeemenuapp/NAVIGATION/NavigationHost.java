package android.project.coffeemenuapp.NAVIGATION;

import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public interface NavigationHost {
    void navigateTo(int parentViewId,Fragment fragment,Boolean addToBackstack);
}
