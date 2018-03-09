package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.view;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.home.HomeActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.R;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.SharedPrefs;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.model.LoginDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.presenter.LoginPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.presenter.LoginPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.provider.RetrofitLoginHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view.RegisterActivity;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView{

    public String Username,Password;

    private SharedPrefs sharedPrefs;
    private LoginPresenter loginPresenter;

    @BindView(R.id.user_name_login)
    EditText userName;
    @BindView(R.id.password_login)
    EditText password;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.sign_up_underline)
    View signUpUnderLine;
    @BindView(R.id.sign_in)
    TextView signIn;
    @BindView(R.id.sign_in_underline)
    View signInUnderLine;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.progressBar_sign_in)
    ProgressBar progressBar;
    @BindView(R.id.input_layout_password_login)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.input_layout_username_login)
    TextInputLayout inputLayoutUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPrefs = new SharedPrefs(this);

        signUp.setTextColor(getResources().getColor(R.color.grey));
        signIn.setTextColor(getResources().getColor(R.color.white));
        signUpUnderLine.setVisibility(View.INVISIBLE);
        signInUnderLine.setVisibility(View.VISIBLE);

        userName.addTextChangedListener(new MyTextWatcher(userName));
        password.addTextChangedListener(new MyTextWatcher(password));

        setupUI(findViewById(R.id.parent_view_login));


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInClick();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });
    }

    public void signInClick(){
        showProgressBar(true);
        Username=userName.getText().toString().trim();
        Password=password.getText().toString().trim();

        if(!validateUsername() || !validatePassword()){
            showProgressBar(false);
            MDToast mdToast = MDToast.makeText(this, "One or more fields empty", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
        }
        else{
            loginPresenter = new LoginPresenterImpl(this, new RetrofitLoginHelper());
            loginPresenter.getLoginData(Username,Password);
        }

    }

    public void goToSignUp(){
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String message) {
        Log.d("Login Error","Login Unsuccessful");
    }

    @Override
    public void showLoginStatus(LoginDataResponse loginDataResponse) {

        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        sharedPrefs.setUserName(Username);
        sharedPrefs.setAccessToken(loginDataResponse.getToken());
        finish();

    }
    public boolean validateUsername(){

        if(userName.getText().toString().trim().isEmpty())
        {
            inputLayoutUsername.setError(getString(R.string.err_msg_empty));
            requestFocus(userName);
            return false;
        }
        else{
            inputLayoutUsername.setErrorEnabled(false);
            return true;
        }

    }
    public boolean validatePassword(){

        if(password.getText().toString().trim().isEmpty())
        {
            inputLayoutPassword.setError(getString(R.string.err_msg_empty));
            requestFocus(password);
            return false;
        }
        else{
            inputLayoutPassword.setErrorEnabled(false);
            return true;
        }

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.user_name_login:
                    validateUsername();
                    break;
                case R.id.password_login:
                    validatePassword();
                    break;
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    //The below function helps to hide keyboard if clicked anywhere except edit text
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
