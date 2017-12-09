package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.home.HomeActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.R;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.SharedPrefs;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.model.LoginDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.presenter.LoginPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.presenter.LoginPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.provider.RetrofitLoginHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView{

    public String Username,Password;

    private SharedPrefs sharedPrefs;
    private LoginPresenter loginPresenter;

    @BindView(R.id.contact)
    EditText userName;
    @BindView(R.id.password)
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


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void signInClick(){
        Username=userName.getText().toString().trim();
        Password=password.getText().toString().trim();

        if(Username.isEmpty() || Password.isEmpty()){
            showProgressBar(false);
            showError("One or more fields empty!");
        }
        else{
            loginPresenter = new LoginPresenterImpl(this, new RetrofitLoginHelper());
            loginPresenter.getLoginData(Username,Password);
            hideKeyboard();
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
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
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
}
