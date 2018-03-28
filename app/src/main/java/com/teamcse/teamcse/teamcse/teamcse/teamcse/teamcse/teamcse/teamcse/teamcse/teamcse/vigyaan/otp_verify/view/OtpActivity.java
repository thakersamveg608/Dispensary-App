package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OtpActivity extends AppCompatActivity implements OtpView,OTPListener{

    private String message,otpNumber;

    private OtpVerifyPresenter otpVerifyPresenter;

    private SharedPrefs sharedPrefs;

    @BindView(R.id.verify)
    Button verifyOtp;
    @BindView(R.id.otp)
    EditText otp;
//    @BindView(R.id.resend_otp)
//    Button resendOtp;
    @BindView(R.id.progressBar_otp)
    ProgressBar progressBar;
    @BindView(R.id.input_layout_otp)
    TextInputLayout inputLayoutOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        sharedPrefs = new SharedPrefs(this);


        otp.addTextChangedListener(new MyTextWatcher(otp));
        setupUI(findViewById(R.id.parent_view_otp));
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed_verify();

            }
        });
        OtpReader.bind(this,"CodeSV");
    }


    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    public void proceed_verify() {
        otpNumber = otp.getText().toString();
        verifyOtp.setClickable(false);
        if (!validateOtp()){
            verifyOtp.setClickable(true);
        }
        else {
            otpVerifyPresenter = new OtpVerifyPresenterImpl(OtpActivity.this,new RetrofitOtpVerifyHelper());
            otpVerifyPresenter.otpData(otpNumber, sharedPrefs.getMobile(),sharedPrefs.getAccessToken());
        }

    }
//    public void resend(View v) {
//        RegisterPresenter registerPresenter = new RegisterPresenterImpl(new RetrofitRegisterHelper(),new RegisterView() {
//            @Override
//            public void showProgressBar(boolean show) {
//                if (show) {
//                    progressBar.setVisibility(View.VISIBLE);
//                } else {
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//            }
//
//
//            @Override
//            public void showError(String message) {
//
//            }
//
//            @Override
//            public void showRegisterStatus(RegisterDataResponse registerDataResponse) {
//
//            }
//        });
//        registerPresenter.getRegisterData(sharedPrefs.getName(),sharedPrefs.getMobile(),sharedPrefs.getPassword(),sharedPrefs.getBloodGroup(),sharedPrefs.getUserName(),sharedPrefs.getEmail());
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                verifyOtp.setVisibility(View.VISIBLE);
//            }
//        },30000);
//
//
//    }

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
            Log.d("Error","OTP Verification error.");
    }

    @Override
    public void OtpStatus(OtpData otpData) {

        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        sharedPrefs.setLogin(true);
        finish();

    }

    public boolean validateOtp(){
        if(otp.getText().toString().trim().isEmpty())
        {
            inputLayoutOtp.setError(getString(R.string.err_msg_empty));
            requestFocus(otp);
            MDToast mdToast = MDToast.makeText(this, getString(R.string.err_msg_empty), MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return false;
        }
        else{
            inputLayoutOtp.setErrorEnabled(false);
            return true;
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void otpReceived(String messageText) {
        otp.setText(parseCode(messageText));
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
                case R.id.otp:
                    validateOtp();
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
                    hideSoftKeyboard(OtpActivity.this);
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
