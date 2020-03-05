package bynry.com.loginapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonUtility {

    private static CommonUtility nUtilHelper;

    public static CommonUtility getInstance(Context context) {
        if (nUtilHelper == null) {
            nUtilHelper = new CommonUtility();
        }
        return nUtilHelper;
    }
    public static boolean checkConnectivity(Context pContext) {
        ConnectivityManager lConnectivityManager = (ConnectivityManager)
                pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetInfo = lConnectivityManager.getActiveNetworkInfo();
        return lNetInfo != null && lNetInfo.isConnected();
    }
}
