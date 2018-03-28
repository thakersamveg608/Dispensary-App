package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.R;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.SharedPrefs;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.view.LoginActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.view.OtpActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter.RegisterPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter.RegisterPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.provider.RetrofitRegisterHelper;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    public String Name,Contact,Password,RePassword,BloodGroup,Username,Email;

    private SharedPrefs sharedPrefs;
    SmsVerifyCatcher smsVerifyCatcher;

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
    @BindView(R.id.input_layout_password_register)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.input_layout_username_register)
    TextInputLayout inputLayoutUsername;
    @BindView(R.id.input_layout_name_register)
    TextInputLayout inputLayoutName;
    @BindView(R.id.input_layout_email_register)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_contact_register)
    TextInputLayout inputLayoutContact;
    @BindView(R.id.input_layout_blood_register)
    TextInputLayout inputLayoutBlood;
    @BindView(R.id.input_layout_repassword_register)
    TextInputLayout inputLayoutRepassword;

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
        setupUI(findViewById(R.id.parent_view_register));

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
        fullName.addTextChangedListener(new MyTextWatcher(fullName));
        contactNumber.addTextChangedListener(new MyTextWatcher(contactNumber));
        password.addTextChangedListener(new MyTextWatcher(password));
        reTypePassword.addTextChangedListener(new MyTextWatcher(reTypePassword));
        bloodGroup.addTextChangedListener(new MyTextWatcher(bloodGroup));
        userName.addTextChangedListener(new MyTextWatcher(userName));
        email.addTextChangedListener(new MyTextWatcher(email));
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
            }
        });
    }
    private void registerClick(){
        showProgressBar(true)
        ;
        Name=fullName.getText().toString().trim();
        Contact=contactNumber.getText().toString().trim();
        Password=password.getText().toString().trim();
        RePassword=reTypePassword.getText().toString().trim();
        BloodGroup=bloodGroup.getText().toString().trim();
        Username=userName.getText().toString().trim();
        Email=email.getText().toString().trim();

        if(!validateName() || !validateContact() || !validatePassword() || !validateRePassword() || !validateBloodGroup() || !validateUsername() || !validateEmail()){
            showProgressBar(false);
            MDToast mdToast = MDToast.makeText(this, "One or more fields empty", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
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
            showProgressBar(false);
            showError("Invalid Email ID!");
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
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        MDToast mdToast = MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        mdToast.show();
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
    public boolean validateName(){
        if(fullName.getText().toString().trim().isEmpty())
        {
            inputLayoutName.setError(getString(R.string.err_msg_empty));
            requestFocus(fullName);
            return false;
        }
        else{
            inputLayoutUsername.setErrorEnabled(false);
            return true;
        }
    }
    public  boolean validateContact(){
        if(contactNumber.getText().toString().trim().isEmpty())
        {
            inputLayoutContact.setError(getString(R.string.err_msg_empty));
            requestFocus(contactNumber);
            return false;
        }
        else{
            inputLayoutUsername.setErrorEnabled(false);
            return true;
        }
    }
    public  boolean validatePassword()
    {
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
    public  boolean validateRePassword(){

        if(reTypePassword.getText().toString().trim().isEmpty())
        {
            inputLayoutRepassword.setError(getString(R.string.err_msg_empty));
            requestFocus(reTypePassword);
            return false;
        }
        else{
            inputLayoutRepassword.setErrorEnabled(false);
            return true;
        }

    }
    public boolean validateBloodGroup(){
        if(bloodGroup.getText().toString().trim().isEmpty())
        {
            inputLayoutBlood.setError(getString(R.string.err_msg_empty));
            requestFocus(bloodGroup);
            return false;
        }
        else{
            inputLayoutUsername.setErrorEnabled(false);
            return true;
        }
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
    public boolean validateEmail(){

        if(email.getText().toString().trim().isEmpty())
        {
            inputLayoutEmail.setError(getString(R.string.err_msg_empty));
            requestFocus(email);
            return false;
        }
        else{
            inputLayoutEmail.setErrorEnabled(false);
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
                case R.id.user_name:
                    validateUsername();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
                case R.id.name:
                    validateName();
                    break;
                case R.id.blood_group:
                    validateBloodGroup();
                    break;
                case R.id.retype_password:
                    validateRePassword();
                    break;
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.contact:
                    validateContact();
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
                    hideSoftKeyboard(RegisterActivity.this);
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
