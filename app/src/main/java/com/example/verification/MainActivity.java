 package com.example.verification;

 import android.app.Activity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import com.example.verificationlibrary.utils.interfaces.VerificationResponse;
 import com.example.verificationlibrary.utils.utils.VerificationUtil;


 public class MainActivity extends AppCompatActivity{
     //Create an instance of verification util which comes from verificationLinrary
     VerificationUtil verificationUtil;
     Activity activity;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         //initialize verificationUtil
         verificationUtil= new VerificationUtil(this);
         setContentView(R.layout.activity_main);
         activity = this;
         Button showBottomSheet = findViewById(R.id.bottomSheetDialog);
         showBottomSheet.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //when user clicks on verification button initialize the verification
                 //This wil show a Bottomsheet
                 verificationUtil.init(new VerificationResponse() {

                     //This is called on successful completion of verifification process
                     @Override
                     public void onSuccess() {
                         Toast.makeText(activity, "Success",Toast.LENGTH_LONG).show();
                     }
                 });
             }
         });

     }

 }