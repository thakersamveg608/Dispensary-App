package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.R;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.SharedPrefs;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.view.LoginActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.view.OtpActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter.RegisterPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter.RegisterPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.provider.RetrofitRegisterHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    public String Name,Contact,Password,RePassword,BloodGroup,Username,Email;

    private SharedPrefs sharedPrefs;

    private RegisterPresenter registerPresenter;

    @BindView(R.id.name)
    EditText fullName;
    @BindView(R.id.contact)
    EditText contactNumber;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.retype_password)
    EditText reTypePassword;
    @BindView(R.id.blood_group)
    EditText bloodGroup;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.progressBar_register)
    ProgressBar progressBar;
    @BindView(R.id.sign_in)
    TextView signIn;
    @BindView(R.id.sign_in_underline)
    View signInUnderLine;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.sign_up_underline)
    View signUpUnderLine;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.email)
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        sharedPrefs = new SharedPrefs(this);

        signIn.setTextColor(getResources().getColor(R.color.grey));
        signUp.setTextColor(getResources().getColor(R.color.white));
        signInUnderLine.setVisibility(View.INVISIBLE);
        signUpUnderLine.setVisibility(View.VISIBLE);

        initialise();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClick();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignIn();
            }
        });

    }
    public void initialise(){
        contactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 10) {
                    hideKeyboard();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

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
    private void registerClick(){
        Name=fullName.getText().toString().trim();
        Contact=contactNumber.getText().toString().trim();
        Password=password.getText().toString().trim();
        RePassword=reTypePassword.getText().toString().trim();
        BloodGroup=bloodGroup.getText().toString().trim();
        Username=userName.getText().toString().trim();
        Email=email.getText().toString().trim();

        if(Name.isEmpty() || Contact.isEmpty() || Password.isEmpty() || RePassword.isEmpty() || BloodGroup.isEmpty() || Username.isEmpty() || Email.isEmpty()){
            showProgressBar(false);
            showError("You have one or more fields empty!");
        }
        else if(Contact.length()!=10){
            showProgressBar(false);
            showError("Contact number is not valid!");
        }
        else if(!Password.equals(RePassword)){
            showProgressBar(false);
            showError("The passwords you entered does not match!");
        }
        else if(emailInvalid(Email)){
            Toast.makeText(this, "ENTER VALID EMAIL ID!",
                    Toast.LENGTH_LONG).show();
        }
        else{
            registerPresenter = new RegisterPresenterImpl(new RetrofitRegisterHelper(),this);
            registerPresenter.getRegisterData(Name,Contact,Password,BloodGroup,Username,Email);
        }
    }
    public void goToSignIn(){
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
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
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegisterStatus(RegisterDataResponse registerDataResponse) {

        Intent i = new Intent(RegisterActivity.this, OtpActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        sharedPrefs.setMobile(Contact);
        sharedPrefs.setName(Name);
        sharedPrefs.setBloodGroup(BloodGroup);
        sharedPrefs.setPassword(Password);
        sharedPrefs.setUserName(Username);
        sharedPrefs.setEmail(Email);
        sharedPrefs.setAccessToken(registerDataResponse.getToken());
        finish();

    }
    public boolean emailInvalid(String email) {
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        boolean a = matcher.matches();
        return !a;
    }
}
