package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterRateActivity;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.FileUtil;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Model.OrderUpload;
import com.dev.hasarelm.wastefooddonation.Model.donationDrop;
import com.dev.hasarelm.wastefooddonation.Model.images;
import com.dev.hasarelm.wastefooddonation.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class RiderCompleteOrderActivity extends BaseActivity {

    private ImageSwitcher mIvUploadImg;
    private EditText mTvStatus, mTvCompleteTime, mTvCompleteDate;
    private Button mBtnNext,mBtnPreviouse,mBtnUpload,mBtnOrderUpload;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ArrayList<Uri> imageUris;
    private int position =0;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private TimePickerDialog picker;
    private TimePickerDialog toPicker;
    private String type;
    private int id;
    private int driver_id;

    private OrderUpload mOrderUpload;
    private ArrayList<donationDrop>mDonationDrops;

    String mediaPath, mediaPath1;
    ImageView selectedImage;
    List<Uri> files = new ArrayList<>();

    private LinearLayout parentLinearLayout;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_complete_order);

        setToolbar(getResources().getString(R.string.register_activity_order), RiderCompleteOrderActivity.this);
        initView();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        imageUris = new ArrayList<>();

        try {

            Intent intent = getIntent();
             type = intent.getStringExtra("type");
             id = intent.getIntExtra("id", 0);
             driver_id = intent.getIntExtra("R_LOGIN_USER_ID", 0);
            String age = "";

        } catch (Exception e) {
            e.printStackTrace();
        }

        mTvCompleteTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              UsersetTime();
            }
        });

        mTvCompleteDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        mBtnOrderUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCompleteOrder();
            }
        });

        parentLinearLayout= findViewById(R.id.parent_linear_layout);

        ImageView addImage = findViewById(R.id.iv_add_image);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });
    }

    private void addImage() {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView=inflater.inflate(R.layout.image, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        parentLinearLayout.isFocusable();

        selectedImage = rowView.findViewById(R.id.number_edit_text);
        selectImage(RiderCompleteOrderActivity.this);

    }

    private void selectImage(Context context) {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Choose a Media");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {

                        Bitmap img = (Bitmap) data.getExtras().get("data");
                        selectedImage.setImageBitmap(img);
                        Picasso.get().load(getImageUri(RiderCompleteOrderActivity.this,img)).into(selectedImage);

                        String imgPath = FileUtil.getPath(RiderCompleteOrderActivity.this,getImageUri(RiderCompleteOrderActivity.this,img));

                        files.add(Uri.parse(imgPath));
                        Log.e("image", imgPath);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri img = data.getData();
                        Picasso.get().load(img).into(selectedImage);

                        String imgPath = FileUtil.getPath(RiderCompleteOrderActivity.this,img);

                        files.add(Uri.parse(imgPath));
                        Log.e("image", imgPath);

                    }
                    break;
            }
        }
    }

    //===== bitmap to Uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "intuenty", null);
        Log.d("image uri",path);
        return Uri.parse(path);
    }

    private void initView() {
        mTvStatus = findViewById(R.id.activity_order_status);
        mTvStatus.setKeyListener(null);
        mTvCompleteDate = findViewById(R.id.activity_complete_date);
        mTvCompleteDate.setKeyListener(null);
        mTvCompleteTime = findViewById(R.id.activity_register_et_completetime);
        mTvCompleteTime.setKeyListener(null);
        mBtnOrderUpload = findViewById(R.id.activity_register_upload_register);
    }

    private void uploadCompleteOrder(){
        try {
            final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
            String mDate = mTvCompleteDate.getText().toString().trim();
            String mTime = mTvCompleteTime.getText().toString().trim();

            List<MultipartBody.Part> list = new ArrayList<>();
            for (Uri uri:files) {
                Log.i("uris",uri.getPath());
                list.add(prepareFilePart("file", uri));
            }

            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<OrderUpload> call = endPoints.uploadOrderToServer(VLF_BASE_URL+"donation/pickdrop",list,driver_id,type,id,mDate,mTime);
            call.enqueue(new Callback<OrderUpload>() {
                @Override
                public void onResponse(Call<OrderUpload> call, Response<OrderUpload> response) {

                    if (response.code()==200){

                        myPd_ring.dismiss();
                        mOrderUpload = response.body();
                        mDonationDrops = mOrderUpload.getDonationDrop();
                        String message = mOrderUpload.getMessage();
                        String age = "";

                        new SweetAlertDialog(RiderCompleteOrderActivity.this)
                                .setTitleText("Your order complete")
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<OrderUpload> call, Throwable t) {
                    myPd_ring.dismiss();
                    Log.d("TAG","============================================"+t.getMessage().toString());
                }
            });
        }catch (Exception g){
            Log.d("TAG","============================================"+g.getMessage().toString());
        }

    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(fileUri.getPath());
        Log.i("here is error",file.getAbsolutePath());
        // create RequestBody instance from file

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);


    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;

            int month = selectedMonth + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + selectedDay;

            if(month < 10){

                formattedMonth = "0" + month;
            }
            if(selectedDay < 10){

                formattedDayOfMonth = "0" + selectedDay;
            }

            mTvCompleteDate.setText(selectedYear + "-" + (formattedMonth) + "-"
                    +formattedDayOfMonth );
        }
    };

    private void UsersetTime() {

        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                        String format_min = ""+ sMinute;
                        String format_hrs = ""+sHour;

                        if (sMinute<10){

                            format_min = "0"+minutes;
                        }

                        if (sHour<10)
                        {
                            format_hrs = "0"+sHour;
                        }

                        mTvCompleteTime.setText(format_hrs + ":" + format_min + ":00");
                    }
                }, hour, minutes, false);
        picker.show();
    }
}