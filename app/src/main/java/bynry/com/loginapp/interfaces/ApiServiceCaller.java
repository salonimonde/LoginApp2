package bynry.com.loginapp.interfaces;

import com.android.volley.NetworkResponse;

import bynry.com.loginapp.webservices.JsonResponse;

public interface ApiServiceCaller {
    void onAsyncSuccess(JsonResponse jsonResponse, String label);
    void onAsyncFail(String message, String label, NetworkResponse response);
    void onAsyncCompletelyFail(String message, String label);
}
