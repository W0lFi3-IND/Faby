package com.findingbetteryou.faby.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findingbetteryou.faby.R;
import com.findingbetteryou.faby.modules.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OtpConfirmation extends AppCompatActivity {
    private EditText otpDigit1;
    private EditText otpDigit2;
    private EditText otpDigit3;
    private EditText otpDigit4;
    private EditText otpDigit5;
    private EditText otpDigit6;
    private TextView mobileNumberOTPConfirmation;
    private Button verify;
    private static final String TAG = "OtpConfirmation";
    private String verificationID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_confirmation);

        bindLayoutFiles();

        setTextWatcherToEditTexts();

        sendVerificationCode(getIntent().getStringExtra("phonenumber"));


        String userPhoneNumber = getIntent().getStringExtra("phonenumber");
        String mobileNumberOTPConfirmationString = mobileNumberOTPConfirmation.getText().toString();
        mobileNumberOTPConfirmation.setText(mobileNumberOTPConfirmationString+" "+userPhoneNumber);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = "";
                otp = otpDigit1.getText().toString() + otpDigit2.getText().toString() + otpDigit3.getText().toString() +
                        otpDigit4.getText().toString() + otpDigit5.getText().toString() + otpDigit6.getText().toString();

                if(otp.isEmpty() || otp.length()<6){
                    Toast.makeText(OtpConfirmation.this, getString(R.string.invalidOTP), Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyCode(otp);
            }
        });
    }

    private void sendVerificationCode(String userPhoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(userPhoneNumber,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String actualOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG, "CodeSent");
            super.onCodeSent(actualOtp, forceResendingToken);
            verificationID = actualOtp;
        }
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpConfirmation.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    };

    private void verifyCode(String code){
        if(verificationID.isEmpty() || code.isEmpty()){
            Toast.makeText(this, getString(R.string.invalidOTP), Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(OtpConfirmation.this, getString(R.string.signInSuccesfull), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpConfirmation.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(OtpConfirmation.this, getString(R.string.errorMessage), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setTextWatcherToEditTexts() {
        otpDigit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otpDigit1.getText().toString().length() == 1){
                    otpDigit2.requestFocus();
                }
            }
        });

        otpDigit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otpDigit1.getText().toString().length() == 1){
                    otpDigit3.requestFocus();
                }
            }
        });

        otpDigit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otpDigit1.getText().toString().length() == 1){
                    otpDigit4.requestFocus();
                }
            }
        });

        otpDigit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otpDigit1.getText().toString().length() == 1){
                    otpDigit5.requestFocus();
                }
            }
        });

        otpDigit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otpDigit1.getText().toString().length() == 1){
                    otpDigit6.requestFocus();
                }
            }
        });
    }

    private void bindLayoutFiles() {
        otpDigit1 = findViewById(R.id.otpDigit1);
        otpDigit2 = findViewById(R.id.otpDigit2);
        otpDigit3 = findViewById(R.id.otpDigit3);
        otpDigit4 = findViewById(R.id.otpDigit4);
        otpDigit5 = findViewById(R.id.otpDigit5);
        otpDigit6 = findViewById(R.id.otpDigit6);
        verify = findViewById(R.id.verifyButton);
        mobileNumberOTPConfirmation = findViewById(R.id.mobileNumberOTPConfirmation);
    }
}