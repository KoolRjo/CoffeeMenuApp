package android.project.coffeemenuapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.project.coffeemenuapp.ADAPTER.DrinksListAdapter;
import android.project.coffeemenuapp.ADAPTER.GridLayoutManagerWrapper;
import android.project.coffeemenuapp.ROOM.MODEL.Product_ViewModel;
import android.project.coffeemenuapp.databinding.FragmentListDrinksFoodBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_list_drinks_food extends Fragment {
    private FragmentListDrinksFoodBinding binding;
    protected Context mContext;
    private static final String TAG = fragment_list_drinks_food.class.getSimpleName();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentListDrinksFoodBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Product_ViewModel viewModel = new ViewModelProvider(this).get(Product_ViewModel.class);
        final DrinksListAdapter adapter = new DrinksListAdapter(DrinksListAdapter.productDiffCallback);
        RecyclerView rvDrinks = binding.rvListProduct;
        // Dispatching list by GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManagerWrapper(getActivity(),2,GridLayoutManager.VERTICAL,false);
        rvDrinks.setLayoutManager(gridLayoutManager);
        DividerItemDecoration divider_vertical = new DividerItemDecoration(requireActivity(), GridLayoutManager.VERTICAL);
        DividerItemDecoration divider_horizontal = new DividerItemDecoration(requireActivity(), GridLayoutManager.HORIZONTAL);
        rvDrinks.setHasFixedSize(true);
        rvDrinks.setAdapter(adapter);
        rvDrinks.addItemDecoration(divider_vertical);
        rvDrinks.addItemDecoration(divider_horizontal);
        viewModel.getAllProducts().observe(getViewLifecycleOwner(),list->{
            if(!list.isEmpty()){
                adapter.submitList(list);
                Log.i(TAG,"Number of products: "+list.size());
                Log.i(TAG,"Last product ID: "+list.get(adapter.getItemCount()-1).getId());
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
