package android.project.coffeemenuapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.project.coffeemenuapp.NAVIGATION.NavigationHost;
import android.project.coffeemenuapp.databinding.FragmentSignUpBinding;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.project.coffeemenuapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class fragment_singnup_class extends Fragment{
    private FragmentSignUpBinding binding;
    protected Context mContext;
    protected TextInputLayout inputLayoutUsername;
    protected TextInputEditText edtUsername;
    protected TextInputLayout inputLayoutPass;
    protected TextInputEditText edtPass;
    protected TextInputLayout inputLayoutRePass;
    protected TextInputEditText edtRePass;
    protected MaterialButton btnSubmit;
    protected MaterialButton btnBack;
    private void getAllViews(){
        inputLayoutUsername = binding.inputLayoutSignupUsername;
        edtUsername = binding.edtSignupUsername;
        inputLayoutPass = binding.inputLayoutSignupPassword;
        edtPass = binding.edtSignupPassword;
        inputLayoutRePass = binding.inputLayoutSignupRePassword;
        edtRePass = binding.edtSignupRePassword;
        btnSubmit = binding.btnSignupSubmit;
        btnBack = binding.btnSignupLogin;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    //Check all fields valid or not
    public boolean isAllFieldsValid(){
        if(Objects.requireNonNull(edtUsername.getText()).length()==0){
            inputLayoutUsername.setError(getString(R.string.error_NoText_notification));
            return false;
        }else if(edtUsername.getText().length()!=0 && edtUsername.getText().length()>10){
            inputLayoutUsername.setError(getString(R.string.error_incorrectMaxLength_notification));
            return false;
        }
        if(Objects.requireNonNull(edtPass.getText()).length() == 0){
            inputLayoutPass.setError(getString(R.string.error_NoText_notification));
            return false;
        }else if(edtPass.getText().length() != 0 && edtPass.getText().length() != 8){
            inputLayoutPass.setError(getString(R.string.error_incorrectLength_notification));
            return false;
        }
        if(Objects.requireNonNull(edtRePass.getText()).length() == 0){
            inputLayoutRePass.setError(getString(R.string.error_NoText_notification));
            return false;
        }else if(edtRePass.getText().length() != 0 && !edtRePass.getText().toString().trim().equals(edtPass.getText().toString().trim())){
            inputLayoutRePass.setError(getString(R.string.error_incorrectRePassword));
            return false;
        }
        return true;
    }
    //Sign up a new account
    public void onSubmitButton(){
        btnSubmit.setOnClickListener(v->{
            if(isAllFieldsValid()){
                if(getActivity()!=null){
                    //usersDAO.saveData(mContext,edtUsername.getText().toString().trim(),edtPass.getText().toString().trim());
                    ((NavigationHost)getActivity()).navigateTo(R.id.lao_signup_and_login_content,new fragment_login_class(),false);
                }
            }
        });
    }
    //Back to Login fragment
    public void onBackButton(){
        btnBack.setOnClickListener(v->{
            if(getActivity()!=null) {
                ((NavigationHost) getActivity()).navigateTo(R.id.lao_signup_and_login_content, new fragment_login_class(), false);
            }
        });
    }
    //clear error text if correct input
    public void checkInputTextLength(TextInputEditText textInputEditText,TextInputLayout textInputLayout,int maxCount){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    textInputLayout.setError(getString(R.string.error_NoText_notification));
                }else if(s.length() != 0 && s.length() > maxCount){
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
    public void checkRePasswordCorrect(TextInputEditText textInputEditText){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(edtPass.getText()).toString().trim().contentEquals(s)){
                    inputLayoutRePass.setError(null);
                }else{
                    inputLayoutRePass.setError(getString(R.string.error_incorrectRePassword));
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
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        getAllViews();
        onBackButton();
        onSubmitButton();
        if(edtUsername.getText() != null && edtPass.getText() != null && edtRePass.getText() != null){
            checkInputTextLength(edtUsername,inputLayoutUsername,10);
            checkInputTextLength(edtPass,inputLayoutPass,8);
            checkRePasswordCorrect(edtRePass);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
