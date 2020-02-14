
package com.commutersfamily.commuterfamily.Fragments.SignUp;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Activities.SignUpActivity;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeFragment extends Fragment {
  private String mVerificationId;
  private FirebaseAuth mAuth;
  private EditText editText1, editText2, editText3, editText4,editText5,editText6;
  private EditText[] editTexts;
  private AppCompatButton btn_next;
  private ImageView btn_back;
  private View view;
  private EditText editText;

  public CodeFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_code, container, false);
    mAuth = FirebaseAuth.getInstance();

    editText=view.findViewById(R.id.code);





    btn_next = view.findViewById(R.id.btn_next_3);
    btn_back = view.findViewById(R.id.btn_back_3);
    sendVerificationCode(DemoClass.number);

    btn_next.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {



        String code = editText.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
          editText.setError("Enter valid code");
          editText.requestFocus();
          return;
        }

        //verifying the code entered manually
        verifyVerificationCode(code);


      }
    });

    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SignUpActivity.fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new NumberFragment(), null)
                .commit();
      }
    });

    return view;
  }

  private void verifyVerificationCode(String code) {
    //creating the credential
    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

    //signing the user
    signInWithPhoneAuthCredential(credential);
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mAuth.signInWithCredential(credential)
            .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  SignUpActivity.fragmentManager.beginTransaction()
                          .replace(R.id.fragment_container, new InfoFragment(), null)
                          .commit();

                } else {

                  //verification unsuccessful.. display an error message

                  String message = "Somthing is wrong, we will fix it soon...";

                  if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Invalid code entered...";
                  }

                  Snackbar snackbar = Snackbar.make( getView(), message, Snackbar.LENGTH_LONG);
                  snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                  });
                  snackbar.show();
                }
              }
            });
  }
  private void sendVerificationCode(String mobile) {
    PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+92" + mobile,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
    );
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+92" + mobile,
//                60,
//                TimeUnit.SECONDS,
//                TaskExecutors.MAIN_THREAD,
//                mCallbacks);
  }
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

      //Getting the code sent by SMS
      String code = phoneAuthCredential.getSmsCode();

      //sometime the code is not detected automatically
      //in this case the code will be null
      //so user has to manually enter the code

      // verifyVerificationCode(code);


    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
      Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
      super.onCodeSent(s, forceResendingToken);

      //storing the verification id that is sent to the user
      mVerificationId = s;
    }
  };

  public class PinTextWatcher implements TextWatcher {

    private int currentIndex;
    private boolean isFirst = false, isLast = false;
    private String newTypedString = "";

    PinTextWatcher(int currentIndex) {
      this.currentIndex = currentIndex;

      if (currentIndex == 0)
        this.isFirst = true;
      else if (currentIndex == editTexts.length - 1)
        this.isLast = true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      newTypedString = s.subSequence(start, start + count).toString().trim();
    }

    @Override
    public void afterTextChanged(Editable s) {
      String text = newTypedString;

      /* Detect paste event and set first char */
      if (text.length() > 1)
        text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

      editTexts[currentIndex].removeTextChangedListener(this);
      editTexts[currentIndex].setText(text);
      editTexts[currentIndex].setSelection(text.length());
      editTexts[currentIndex].addTextChangedListener(this);

      if (text.length() == 1)
        moveToNext();
      else if (text.length() == 0)
        moveToPrevious();
    }


    private void moveToNext() {
      if (!isLast)
        editTexts[currentIndex + 1].requestFocus();

      if (isAllEditTextsFilled() && isLast) { // isLast is optional
        editTexts[currentIndex].clearFocus();
        //hideKeyboard();
      }
    }

    private void moveToPrevious() {
      if (!isFirst)
        editTexts[currentIndex - 1].requestFocus();
    }

    private boolean isAllEditTextsFilled() {
      for (EditText editText : editTexts)
        if (editText.getText().toString().trim().length() == 0)
          return false;
      return true;
    }
//
//        private void hideKeyboard() {
//            if (getCurrentFocus() != null) {
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//            }
//        }

  }

  public class PinOnKeyListener implements View.OnKeyListener {

    private int currentIndex;

    PinOnKeyListener(int currentIndex) {
      this.currentIndex = currentIndex;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
        if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
          editTexts[currentIndex - 1].requestFocus();
      }
      return false;        }
  }


}