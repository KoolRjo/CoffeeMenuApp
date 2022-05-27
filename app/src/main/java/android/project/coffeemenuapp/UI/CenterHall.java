package android.project.coffeemenuapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.project.coffeemenuapp.NAVIGATION.NavigationHost;
import android.project.coffeemenuapp.R;
import android.os.Bundle;
import android.project.coffeemenuapp.databinding.ActivityCenterHallBinding;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;


public class CenterHall extends AppCompatActivity implements NavigationHost {
    ActivityCenterHallBinding binding;
    private static final String tag = CenterHall.class.getSimpleName();
    fragment_list_drinks_food frgListDrinksFood = new fragment_list_drinks_food();
    fragment_list_events frgListEvents = new fragment_list_events();
    fragment_user_profile frgUserProfile = new fragment_user_profile();
    fragment_order_detail frgOrderDetail = new fragment_order_detail();
    fragment_build_menu frgBuildMenu = new fragment_build_menu();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar toolbar;

    private void getViews(){
        drawerLayout = binding.drawerLayoutCenterHall;
        navigationView = binding.navigationView;
        toolbar = binding.toolbar;
    }
    private void initFragment(int containerID, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerID, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressLint("NonConstantResourceId")
    public void onNavigationViewSelected(){
        navigationView.setNavigationItemSelectedListener(item->{
            switch(item.getItemId()){
                case R.id.item_first_page:
                    item.setChecked(true);
                    navigateTo(R.id.lao_display_list,frgListDrinksFood,false);
                    Toast.makeText(this,"Menu drink&food",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.item_second_page:
                    item.setChecked(true);
                    navigateTo(R.id.lao_display_list,frgListEvents,false);
                    Toast.makeText(this,"Introduce our events",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.item_third_page:
                    item.setChecked(true);
                    navigateTo(R.id.lao_display_list,frgUserProfile,false);
                    Toast.makeText(this,"Your own profile will show here for you",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.item_page_order_detail:
                    item.setChecked(true);
                    navigateTo(R.id.lao_display_list,frgOrderDetail,false);
                    Toast.makeText(this,"You can looking up your order here",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.item_build_menu:
                    item.setChecked(true);
                    navigateTo(R.id.lao_display_list,frgBuildMenu,false);
                    Toast.makeText(this,"You can add more drinks and food in Menu",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.item_sign_out:
                    item.setChecked(true);
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                    return true;
                default:
                    throw new ActivityNotFoundException();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCenterHallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getViews();
        initFragment(R.id.lao_display_list,frgListDrinksFood);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        }
        onNavigationViewSelected();
    }

    @Override
    public void navigateTo(int parentViewId, Fragment fragment, Boolean addToBackstack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(parentViewId,fragment);
        if(addToBackstack){
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }
}