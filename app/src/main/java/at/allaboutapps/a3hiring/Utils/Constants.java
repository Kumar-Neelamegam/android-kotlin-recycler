package at.allaboutapps.a3hiring.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import at.allaboutapps.a3hiring.CoreModules.ClubList;
import at.allaboutapps.a3hiring.DataObject.ClubListObject;
import at.allaboutapps.a3hiring.R;

/**
 * Constants class that holds all the static variables and methods
 * MUTHUKUMAR NEELAMEGAM
 */
public class Constants {


    private static final Constants ourInstance = new Constants();

    public static   boolean ListviewType=true;

    public static String URL = "https://public.allaboutapps.at/hiring/clubs.json";

    static Constants getInstance() {
        return ourInstance;
    }

    private Constants() {


    }

    public String GetEdittextValue(EditText edt) {
        String str = "";
        if (edt.getText().length() == 0 && edt != null) {

        }
        return str;
    }


    public static String GetCurrentTime() {
        String str = null;
        try {

            str = "";
            SimpleDateFormat sdf4 = new SimpleDateFormat("h:mm a");
            String currentDateandTime = sdf4.format(new Date());     //8:29 PM
            str = currentDateandTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static boolean CheckNetwork(Context ctx) {
        ConnectivityManager cn = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {

            return true;
        } else {

            return false;
        }
    }

    public static String CheckString(String str) {
        if (str == null) {
            return "";
        } else if (str.trim().equalsIgnoreCase("null") || str.equalsIgnoreCase("null")) {
            return "";
        } else if (str.length() > 0) {
            return str;
        } else {
            return "-";
        }

    }


    public static void SnackBar(Context ctx, String Message, View parentLayout, int id) {

        Snackbar mSnackBar = Snackbar.make(parentLayout, Message, Snackbar.LENGTH_LONG);
        View view = mSnackBar.getView();
        view.setPadding(5, 5, 5, 5);

        if (id == 1)//Positive
        {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.green_500));
        } else if (id == 2)//Negative
        {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.md_deep_orange_300));
        } else//Negative
        {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.md_deep_orange_300));
        }


        TextView mainTextView = (view).findViewById(R.id.snackbar_text);
        mainTextView.setAllCaps(true);
        mainTextView.setTextSize(16);
        mainTextView.setTextColor(ctx.getResources().getColor(R.color.md_white_1000));
        mSnackBar.setDuration(3000);
        mSnackBar.show();


    }


    public static void Logger(String Message, Context ctx) {
        Log.e(ctx.getPackageCodePath(), Message);
    }


    public static String GetTimeStamp() {
        String str = "";
        try {

            Long tsLong = System.currentTimeMillis() / 1000;
            str = tsLong.toString();

            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }


    public static void HighlightMandatory(String LabelName, TextView textview) {
        String starsymbol = "<font color='#EE0000'><b>*</b></font>";
        textview.setText(Html.fromHtml(LabelName + starsymbol));
    }


    public static void ExitDialog(final Context mContext) {

        CustomDialog customDialog = new CustomDialog(mContext);
        customDialog.setLayoutColor(R.color.red_400);
        customDialog.setImage(R.drawable.ic_exit_to_app);
        customDialog.setTitle("Information");
        customDialog.setDescription("Are you sure want to exit?");
        customDialog.setNegativeButtonTitle("No");
        customDialog.setPossitiveButtonTitle("Yes");
        customDialog.setOnPossitiveListener(new CustomDialog.possitiveOnClick() {
            @Override
            public void onPossitivePerformed() {
                ((Activity) mContext).finishAffinity();
            }
        });
        customDialog.show();

    }


    public static void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 50) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(String.valueOf(finalCount));
                }
            }, time);
        }
    }


    public static void globalStartIntent(Context context, Class classes, Bundle bundle, int id) {
        ((Activity) context).finish();
        Intent intent = new Intent(context, classes);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        CustomIntent.customType(context, id);
        context.startActivity(intent);

    }

    public static void globalStartIntent2(Context context, Class classes, Bundle bundle) {

        Intent intent = new Intent(context, classes);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        CustomIntent.customType(context, 1);
        context.startActivity(intent);

    }


    public static void changeStatusBarColour(Context ctx) {
        if (Build.VERSION.SDK_INT >= 21) {
            ((Activity) ctx).getWindow().setNavigationBarColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            ((Activity) ctx).getWindow().setStatusBarColor(ContextCompat.getColor(ctx, R.color.colorPrimary)); //status bar or the time bar at the top
        }
    }

}//END
