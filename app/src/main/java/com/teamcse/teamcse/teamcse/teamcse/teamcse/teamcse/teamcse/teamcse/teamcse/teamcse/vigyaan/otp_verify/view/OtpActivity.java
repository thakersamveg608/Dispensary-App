package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.home.HomeActivity;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.R;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.SharedPrefs;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.model.OtpData;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.presenter.OtpVerifyPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.presenter.OtpVerifyPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.provider.RetrofitOtpVerifyHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter.RegisterPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter.RegisterPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.provider.RetrofitRegisterHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpActivity extends AppCompatActivity implements OtpView{

    private String message,otpNumber;

    private OtpVerifyPresenter otpVerifyPresenter;

    private SharedPrefs sharedPrefs;

    @BindView(R.id.verify)
    Button verifyOtp;
    @BindView(R.id.otp)
    EditText otp;
    @BindView(R.id.resend_otp)
    Button resendOtp;
    @BindView(R.id.progressBar_otp)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        sharedPrefs = new SharedPrefs(this);

        otp.addTextChangedListener(new TextWatcher() {
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

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed_verify();

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

    public void proceed_verify() {
        otpNumber = otp.getText().toString();
        verifyOtp.setClickable(false);
        if (otpNumber.isEmpty()){
            Toast.makeText(this,"This field cannot be empty!",Toast.LENGTH_SHORT).show();
            verifyOtp.setClickable(true);
        }
        else {
            otpVerifyPresenter = new OtpVerifyPresenterImpl(OtpActivity.this,new RetrofitOtpVerifyHelper());
            otpVerifyPresenter.otpData(otpNumber, sharedPrefs.getMobile(),sharedPrefs.getAccessToken());
        }

    }
    public void resend(View v) {
        RegisterPresenter registerPresenter = new RegisterPresenterImpl(new RetrofitRegisterHelper(),new RegisterView() {
            @Override
            public void showProgressBar(boolean show) {

            }


            @Override
            public void showError(String message) {

            }

            @Override
            public void showRegisterStatus(RegisterDataResponse registerDataResponse) {

            }
        });
        registerPresenter.getRegisterData(sharedPrefs.getName(),sharedPrefs.getMobile(),sharedPrefs.getPassword(),sharedPrefs.getBloodGroup(),sharedPrefs.getUserName(),sharedPrefs.getEmail());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyOtp.setVisibility(View.VISIBLE);
            }
        },30000);


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
    public void showMessage(String message) {

    }

    @Override
    public void OtpStatus(OtpData otpData) {

        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        sharedPrefs.setLogin(true);
        finish();

    }

    @Override
    public void verify_bttn_clickable() {

    }
}
