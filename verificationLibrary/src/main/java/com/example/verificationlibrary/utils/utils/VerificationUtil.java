package com.example.verificationlibrary.utils.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.verificationlibrary.R;
import com.example.verificationlibrary.utils.fragments.FakeFragment;
import com.example.verificationlibrary.utils.interfaces.VerificationResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class VerificationUtil {
    private Activity activity;
    private TextView fileNameHolder;
    private Button pickFileButton, startVerification, cancelVerification;
    private BottomSheetDialog bottomSheetDialog;
    private View bottomSheetView, loadingHolder, verifiedView;
    private View verificationHolder;
    private LottieAnimationView verificationTick, closeButton;
    VerificationResponse verificationResponse;


    public VerificationUtil(Activity activity) {
        this.activity = activity;
    }



    public void init(VerificationResponse verificationResponse) {
        this.verificationResponse=verificationResponse;
        initComponents();
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }



    public void onFilePicked(String filename, String filePath) {
        verificationHolder.setVisibility(View.VISIBLE);
        fileNameHolder.setText(filename);
        pickFileButton.setVisibility(View.GONE);
    }



    private void initComponents() {
        bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(activity.getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet,
                        (LinearLayout) activity.findViewById(R.id.bottomSheetContainer));
        fileNameHolder = bottomSheetView.findViewById(R.id.verification_filename);
        verificationHolder = bottomSheetView.findViewById(R.id.verification_items);
        pickFileButton = bottomSheetView.findViewById(R.id.verification_pick_file);
        closeButton = bottomSheetView.findViewById(R.id.verification_close);
        startVerification = bottomSheetView.findViewById(R.id.verification_start);
        cancelVerification = bottomSheetView.findViewById(R.id.verification_cancel);
        verifiedView = bottomSheetView.findViewById(R.id.verified_holder);
        verificationTick = bottomSheetView.findViewById(R.id.verified_animation);
        loadingHolder = bottomSheetView.findViewById(R.id.verification_loading_holder);
        pickFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pickFile();
            }
        });
        startVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });
        cancelVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }


    private void verify() {
        closeButton.setVisibility(View.INVISIBLE);
        verificationHolder.setVisibility(View.GONE);
        loadingHolder.setVisibility(View.VISIBLE);
        bottomSheetDialog.setCancelable(false);
        uploadFile();
    }


    private void uploadFile() {
        //TODO we can perform file upload here. I am using a delay to mock that
        final int interval = 3000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                closeButton.setVisibility(View.VISIBLE);
                loadingHolder.setVisibility(View.GONE);
                verifiedView.setVisibility(View.VISIBLE);
                bottomSheetDialog.setCancelable(true);
                Runnable afterAnimation = new Runnable() {
                    public void run()
                    {
                        verificationResponse.onSuccess();
                        bottomSheetDialog.dismiss();
                    }
                };
                verificationTick.postOnAnimationDelayed(afterAnimation, interval - 500);
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }




    void pickFile(){
        FragmentManager fragMan = activity.getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        FakeFragment fragment=new FakeFragment();
        fragment.setVerificationUtil(this);
        fragTransaction.add(fragment , "fragment");
        fragTransaction.commit();
    }

}
