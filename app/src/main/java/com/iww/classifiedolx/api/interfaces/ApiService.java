package com.iww.classifiedolx.api.interfaces;

import com.iww.classifiedolx.api.AllApiResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by indiawebwide on 18/04/17.
 */

public interface ApiService {


    @GET
    Call<Object> sendSms(@Url String url);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.MainCategoryRes> fetchMainCategory(@Field("action") String action);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.SubCategoryRes> fetchSubCategory(@Field("action") String action,@Field("CatId") String CatId);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.AdverResp> fetchAdsList(@Field("action") String action,@Field("CatId") String CatId,@Field("SubCatId") String SubCatId);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.CommonRes> forgetPassword(@Field("action") String action,@Field("Email_Id") String Email_Id);



    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> registerApi(@Field("action") String action,@Field("Name") String Name,@Field("Email_Id") String Email_Id,@Field("Phone") String Phone,@Field("Password") String Password );

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> loginApi(@Field("action") String action,@Query(value = "Email_Id", encoded = true) String Email_Id,@Field("Password") String Password);

   /* @POST("json.php")
    Call<AllApiResponse.LoginResp> loginCall = RetorfitService.service.loginUser(formBody);
*/
    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> updateProfileApi(@Field ("action") String action,@Field("Name") String Name,@Field("Email_Id") String Email_Id,@Field("Phone") String Phone,@Field("Password") String Password ,@Field("User_Id") String User_Id );


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.CommonRes> updatePassword(@Field("action") String action,@Field("Old_Password") String Old_Password,@Field("New_Password") String New_Password,@Field("User_Id") String User_Id);


/*    @FormUrlEncoded
    @POST("generateOTP/")
    Call<VLoveApiResp.GenerateOTPResp> generateOTP( @Field("phone") String Phone  );*/


}
