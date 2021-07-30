package com.example.verificationlibrary.utils.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.verificationlibrary.utils.utils.GenUtil;
import com.example.verificationlibrary.utils.utils.VerificationUtil;

import static android.app.Activity.RESULT_OK;


public class FakeFragment extends Fragment {
    private VerificationUtil verificationUtil;
    private Activity innerActivity;


    public void setVerificationUtil(VerificationUtil verificationUtil) {
        this.verificationUtil = verificationUtil;
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        innerActivity = activity;
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intentPDF, GenUtil.PICK_FILE_PDF);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        switch (requestCode) {
            case GenUtil.PICK_FILE_PDF: {
                if(resultCode==RESULT_OK){
                    String filePath = GenUtil.getFilPath(data);
                    String displayName = GenUtil.getFileName(data, innerActivity);
                    if (verificationUtil != null) {
                        verificationUtil.onFilePicked(displayName, filePath);

                    }
                }
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
