package bynry.com.loginapp.webservices;

import bynry.com.loginapp.models.UserModel;

public class JsonResponse {
    public String SUCCESS = "success";
    public String message;
    public String success;
    public String result;
    public String status;
    public String data;

    public static String FAILURE = "failure";
    public UserModel responsedata;
}
