package com.iww.classifiedolx.api.interfaces;

import com.iww.classifiedolx.api.AllApiResponse;
import okhttp3.MultipartBody;
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
    Call<AllApiResponse.SubCategoryRes> fetchSubCategory(@Field("action") String action, @Field("CatId") String CatId);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.AdverResp> fetchAdsList(@Field("action") String action, @Field("CatId") String CatId, @Field("SubCatId") String SubCatId, @Field("User_Id") String User_Id );


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.AdverResp> fetchAdsByUserIdList(@Field("action") String action, @Field("User_Id") String User_Id );


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.CommonRes> forgetPassword(@Field("action") String action, @Field("Email_Id") String Email_Id);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> registerApi(@Field("action") String action, @Field("Name") String Name, @Field("Email_Id") String Email_Id, @Field("Phone") String Phone, @Field("Password") String Password);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> loginApi(@Field("action") String action, @Query(value = "Email_Id", encoded = true) String Email_Id, @Field("Password") String Password);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> updateProfileApi(@Field("action") String action, @Field("Name") String Name, @Field("Email_Id") String Email_Id, @Field("Phone") String Phone, @Field("Password") String Password, @Field("User_Id") String User_Id);

    @Multipart
    @POST("json.php")
    Call<AllApiResponse.LoginResp> updateProfileImgApi(@Query("action") String action, @Query("User_Id") String user_id, @Part MultipartBody.Part Profile);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.LoginResp> getProfileData(@Field("action") String action, @Field("User_Id") String User_Id);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.CommonRes> updatePassword(@Field("action") String action, @Field("Old_Password") String Old_Password, @Field("New_Password") String New_Password, @Field("User_Id") String User_Id);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.CityResp> fetchCityData(@Field("action") String action);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.AreaResp> fetchAreaData(@Field("action") String action, @Field("cityId") String cityId);

    @Multipart
    @POST("json.php")
    Call<AllApiResponse.LoginResp> addNewAdvertisementApi(@Query("action") String Add_Ad, @Query("User_Id") String User_Id,
                                                          @Query("Ad_title") String Ad_title,
                                                          @Query("Ad_Description") String Ad_Description,@Query("show_mobile_on_add") String show_mobile_on_add,
                                                          @Query("cityId") String cityId,@Query("cityName") String cityName,
                                                          @Query("areaId") String areaId,@Query("areaName") String areaName,
                                                          @Query("CatId") String CatId, @Query("SubCatId") String SubCatId,
                                                          @Query("field1") String field1, @Query("field_second1") String field_second1,
                                                          @Query("field2") String field2, @Query("field_second2") String field_second2,
                                                          @Query("field3") String field3, @Query("field_second3") String field_second3,
                                                          @Query("field4") String field4, @Query("field_second4") String field_second4,
                                                          @Query("field5") String field5, @Query("field_second5") String field_second5,
                                                          @Query("field6") String field6, @Query("field_second6") String field_second6,
                                                          @Query("field7") String field7, @Query("field_second7") String field_second7,
                                                          @Query("field8") String field8, @Query("field_second8") String field_second8,
                                                          @Query("field9") String field9, @Query("field_second9") String field_second9,
                                                          @Query("field10") String field10, @Query("field_second10") String field_second10,
                                                       @Part MultipartBody.Part image1, @Part MultipartBody.Part image2,
                                                          @Part MultipartBody.Part image3, @Part MultipartBody.Part image4);


    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.AdverFullDetailResp> getAdsDetailById(@Field("action") String action, @Field("Ads_Id") String Ads_Id);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.AdverResp> getAllFavAdsLst(@Field("action") String action, @Field("User_Id") String User_Id);

    @FormUrlEncoded
    @POST("json.php")
    Call<AllApiResponse.CommonRes> addInFav(@Field("action") String action, @Field("User_Id") String User_Id ,@Field("Ads_Id") String Ads_Id);



  /*    @FormUrlEncoded
    @POST("generateOTP/")
    Call<VLoveApiResp.GenerateOTPResp> generateOTP( @Field("phone") String Phone  );*/


}
