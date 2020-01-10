package com.iww.classifiedolx.Utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Toast;
import com.iww.classifiedolx.R;

import java.util.Locale;


/**
 * Created by abc on 12/24/2015.
 */
public class AlertMessage {


    private Dialog dialog;
    AlertDialog alert11;
    public void showErrorPopup(Context context, String title, String message)
    {
     AlertDialog.Builder   builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
         alert11 = builder1.create();
        alert11.show();
    }
    public void hideErrorPopup()
    {
        if (alert11 != null && alert11.isShowing()) {
            alert11.dismiss();
            alert11 = null;

        }

    }


    public void showSuccessIntent(Context context, String title, String message) {

    /*    final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText(title);
        pDialog.setContentText(message);

        pDialog.show();*/


        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public void showCommonDialog(Context mContext) {

      /*  mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Loading Please wait ....");
        mDialog.setIndeterminate(false);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.show();*/

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_pre);
        dialog.setCancelable(false);

        dialog.show();

    }


    public void cancelDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;

        }
    }


    public void setLanguage(String language, Context mContext) {
        Locale myLocale = new Locale(language);
        Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


    }

}
