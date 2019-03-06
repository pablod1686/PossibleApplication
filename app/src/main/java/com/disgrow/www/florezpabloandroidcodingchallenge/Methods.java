package com.disgrow.www.florezpabloandroidcodingchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Methods {

    public static void setParamsView(View v,int w, int h){

        ViewGroup.LayoutParams params = v.getLayoutParams();
        if(w!=0)params.width = w;
        if(h!=0)params.height = h;
        v.setLayoutParams(params);

    }

    public static void setParamsView(View v,int w, int h, int left, int top, int right, int botton){

        ViewGroup.LayoutParams params = v.getLayoutParams();
        if(w!=0)params.width = w;
        if(h!=0)params.height = h;
        v.setLayoutParams(params);

        setMargenes(v,left,top,right,botton);
    }

    public static void setTextViewProperties(TextView tv, float textSize, int color, Typeface font, String text,int left, int top, int right, int botton){

        if(textSize!=0)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        if(color!=0)
            tv.setTextColor(color);

        if(font!=null)
            tv.setTypeface(font);

        if(text!=null)
            tv.setText(text);

        setMargenes(tv,left,top,right,botton);

    }

    public static void setTextViewProperties(TextView tv, float textSize, int color, Typeface font, String text){

        if(textSize!=0)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        if(color!=0)
            tv.setTextColor(color);

        if(font!=null)
            tv.setTypeface(font);

        if(text!=null)
            tv.setText(text);

    }

    public static void setMargenes(View v, int izquierda, int arriba, int derecha, int abajo) {
        ViewGroup.MarginLayoutParams llhelpParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        llhelpParams.setMargins(izquierda, arriba, derecha, abajo);
    }

    public static int getHeightScreen(Activity activity, Resources resources) {

        int heightScreen;

        WindowManager windowManager = (WindowManager) activity.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();

        if (Build.VERSION.SDK_INT >= 19) {
            display.getRealSize(outPoint);
        }else {
            display.getSize(outPoint);
        }
        if (outPoint.y > outPoint.x) {
            heightScreen = outPoint.y;
        } else {
            heightScreen = outPoint.x;
        }

        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }

        heightScreen = heightScreen - result;
        return heightScreen;
    }

    public static int getWidthScreen(Activity activity){

        int widthScreen;

        WindowManager windowManager = (WindowManager) activity.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();

        if (Build.VERSION.SDK_INT >= 19) {
            display.getRealSize(outPoint);
        }else {
            display.getSize(outPoint);
        }

        if (outPoint.y > outPoint.x) {
            widthScreen = outPoint.x;
        } else {
            widthScreen = outPoint.y;
        }

        return widthScreen;
    }

    public static Bitmap resizeImage(Bitmap mBitmap, float newWidth, float newHeigth) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);

    }

    public static boolean isOnline(Context c){
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void myCustomToast(Context context, String s, Drawable bg, int lettersColor, Typeface font, int textSize, int left, int right) {

        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        View view = toast.getView();
        centerText(view);

        view.setBackground(bg);

        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        text.setTextColor(lettersColor);
        setMargenes(text,left,0,right,0);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        text.setTypeface(font);
        toast.show();

    }

    static void centerText(View view) {
        if( view instanceof TextView){
            ((TextView) view).setGravity(Gravity.CENTER);
        }else if( view instanceof ViewGroup){
            ViewGroup group = (ViewGroup) view;
            int n = group.getChildCount();
            for( int i = 0; i<n; i++ ){
                centerText(group.getChildAt(i));
            }
        }
    }
}
