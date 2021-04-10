package com.dev.hasarelm.wastefooddonation.Common;

import com.dev.hasarelm.wastefooddonation.Model.DistrictsModel;
import com.dev.hasarelm.wastefooddonation.Model.DonaterRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestListModel;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestModel;
import com.dev.hasarelm.wastefooddonation.Model.DonationTypeModel;
import com.dev.hasarelm.wastefooddonation.Model.DonationUpdetes;
import com.dev.hasarelm.wastefooddonation.Model.ForgetPasswordModel;
import com.dev.hasarelm.wastefooddonation.Model.LoginUserModel;
import com.dev.hasarelm.wastefooddonation.Model.NotificationModel;
import com.dev.hasarelm.wastefooddonation.Model.OrderUpload;
import com.dev.hasarelm.wastefooddonation.Model.RiderRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.UserDetails;
import com.dev.hasarelm.wastefooddonation.Model.VehicleTypeModel;
import com.dev.hasarelm.wastefooddonation.Model.WeightRangeModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface EndPoints {

    //vehicle type get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<VehicleTypeModel> getVehicleTypes(@Url String Url);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<DistrictsModel> getDistrict(@Url String Url);

    //rider register method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<RiderRegisterModel> riderRegister(@Url String Url , @Body RequestBody cartList);

    //rider register method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<DonaterRegisterModel> donetorRegister(@Url String Url , @Body RequestBody cartList);

    //login method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<LoginUserModel> loginUser(@Url String Url , @Body RequestBody cartList);

    //forget password method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<ForgetPasswordModel> forgetPassword(@Url String Url , @Query("email") String email);

    //forget password method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<DonationRequestModel> sendDonation(@Url String Url, @Body RequestBody cartList);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<DonationTypeModel> getTypes(@Url String Url);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<WeightRangeModel> getWeightsList(@Url String Url);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<DonationRequestListModel> getAllDonationList(@Url String Url, @Query("donater_id") int id, @Query("state") int state );

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<NotificationModel> getAllNotifications(@Url String Url, @Query("user_id") int user_id);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<UserDetails> getUserDetails(@Url String Url, @Query("id") int id);

    @Headers({"Content-Type: application/json"})
    @PUT
    Call<NotificationModel> updateClickNotification(@Url String Url, @Query("id") int id);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<DonationRequestListModel> getAllDonationRequest(@Url String Url,@Query("vehicle_type_id") int vehicle_type_id,@Query("state") int state);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<DonationUpdetes> updateOrder(@Url String Url, @Query("id") int id, @Query("driver_id") int driver_id, @Query("type") String type);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<DonationRequestListModel> getCompleteOrders(@Url String Url, @Query("state") int state);

  /*  //forget password method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<ResponseBody> uploadOrderToServer(@Url String Url ,
                                           @Part MultipartBody.Part file,
                                           @Query("driver_id") int driver_id,
                                           @Query("type") String type,
                                           @Query("id") int id,
                                           @Query("drop_date ") String drop_date ,
                                           @Query("drop_time ") String drop_time  );*/

    @Multipart
    @POST
    Call<OrderUpload> uploadOrderToServer(@Url String Url,@Part List<MultipartBody.Part> files , @Query("driver_id") int driver_id,
                                        @Query("type") String type,
                                        @Query("id") int id,
                                        @Query("drop_date ") String drop_date ,
                                        @Query("drop_time ") String drop_time  );

    @Headers({"Content-Type: application/json"})
    @GET
    Call<DonationRequestListModel> getAllCompleteOrderList(@Url String Url, @Query("driver_id") int id, @Query("state") int state );

}
