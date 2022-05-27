package android.project.coffeemenuapp.UI;

import android.content.Intent;
import android.project.coffeemenuapp.NAVIGATION.NavigationHost;
import android.project.coffeemenuapp.R;
import android.content.Context;
import android.os.Bundle;
import android.project.coffeemenuapp.databinding.FragmentLoginBinding;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class fragment_login_class extends Fragment {
    FragmentLoginBinding binding;
    protected Context mContext;
    protected TextInputLayout inputLayoutUserName;
    protected TextInputLayout inputLayoutPassword;
    protected TextInputEditText edtUserName;
    protected TextInputEditText edtPassword;
    protected MaterialButton btnLogin;
    protected MaterialButton btnSignUp;
    private void getComponentsUI(){
        inputLayoutUserName = binding.inputLayoutUsername;
        inputLayoutPassword = binding.inputLayoutPassword;
        edtUserName = binding.edtUsername;
        edtPassword = binding.edtPassword;
        btnLogin = binding.btnLogin;
        btnSignUp = binding.btnSignup;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private void navigateToSignUpFrg(){
        btnSignUp.setOnClickListener(v->{
            if(getActivity()!=null)
                ((NavigationHost) getActivity()).navigateTo(R.id.lao_signup_and_login_content, new fragment_singnup_class(),true);
        });
        btnLogin.setOnClickListener(view->{
            if(isValidAllFields()){
                Intent intent = new Intent(getActivity(),CenterHall.class);
                startActivity(intent);
            }
        });
    }
    //Validate all fields
    private boolean isValidAllFields(){
        if(Objects.requireNonNull(edtUserName.getText()).length()==0){
            inputLayoutUserName.setError(getString(R.string.error_NoText_notification));
            return false;
        }else if(edtUserName.getText().length()!=0 && edtUserName.getText().length() > 10){
            inputLayoutUserName.setError(getString(R.string.error_incorrectLength_notification));
            return false;
        }
        if(Objects.requireNonNull(edtPassword.getText()).length()==0){
            inputLayoutPassword.setError(getString(R.string.error_NoText_notification));
            return false;
        }else if(edtPassword.getText().length()!=0 && edtPassword.getText().length() !=8){
            inputLayoutPassword.setError(getString(R.string.error_incorrectLength_notification));
            return false;
        }
        return true;
    }
    //Validate all fields while typing
    private void validateOnTyping(TextInputEditText textInputEditText,TextInputLayout textInputLayout,int maxCount){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    textInputLayout.setError(getString(R.string.error_NoText_notification));
                }else if(s.length()!=0 && s.length()>maxCount){
                    textInputLayout.setError(getString(R.string.error_incorrectMaxLength_notification));
                }else{
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        getComponentsUI();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        navigateToSignUpFrg();
    }

    @Override
    public void onResume() {
        super.onResume();
        validateOnTyping(edtUserName,inputLayoutUserName,10);
        validateOnTyping(edtPassword,inputLayoutPassword,8);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
