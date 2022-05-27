package android.project.coffeemenuapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.project.coffeemenuapp.NAVIGATION.NavigationHost;
import android.project.coffeemenuapp.R;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements NavigationHost {
    fragment_login_class frmLogin = new fragment_login_class();
    //Init the first fragment display on Activity when this one in OnCreate
    private void InitFragment(int parentViewId, Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(parentViewId,fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitFragment(R.id.lao_signup_and_login_content,frmLogin);
    }
    @Override
    public void navigateTo(int parentViewId, Fragment fragment, Boolean addToBackstack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(parentViewId, fragment);
        if(addToBackstack){
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }
}