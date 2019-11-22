package com.example.commuterfamily.AddCar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.commuterfamily.Activities.AddCarActivity;
import com.example.commuterfamily.Activities.DriveActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class DriverLicense extends Fragment {
    private ImageView imageview,imageviewLicense;
    private Button btnSelectImage,btnSelectImageLicense;
    private Bitmap bitmap;
    private File destination = null;
    private FloatingActionButton next;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private StorageReference productImageReference;
    private DatabaseReference productRef;
    private ProgressDialog loadingBar;
    private String saveCurrentDate,saveCurrentTime,productRandomkey,downloadImageUri;

    private Uri ImageUri;
    private static final int galleryPic=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.car_license,container,false);
        imageview=view.findViewById(R.id.v_license);
        btnSelectImage=view.findViewById(R.id.v_license_add);
        productImageReference= FirebaseStorage.getInstance().getReference().child("LICENSE");
        loadingBar=new ProgressDialog(getContext());

        next=view.findViewById(R.id.nextLicense);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( downloadImageUri==""){

                    Toast.makeText(getContext(), "Please Give Licence Picture", Toast.LENGTH_SHORT).show();

                }
                else{
                    storeProductInformation();
                    AddCarActivity.fragmentManagerAddCar.beginTransaction()
                            .replace(R.id.fragment_container_add_car, new DriverCnic()
                                    , null)
                            .commit(); storeProductInformation();
                    AddCarActivity.fragmentManagerAddCar.beginTransaction()
                            .replace(R.id.fragment_container_add_car, new DriverCnic()
                                    , null)
                            .commit();
                }

            }
        });
        return view;
    }
    private void storeProductInformation() {
        loadingBar.setTitle("Adding your Vehicle");
        loadingBar.setMessage("Dear User, Please wait while we are adding new Vehicle");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, YYYY");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTIme=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTIme.format(calendar.getTime());

        productRandomkey=saveCurrentDate + saveCurrentTime;

        final StorageReference filePath=productImageReference.child(ImageUri.getLastPathSegment()+productRandomkey+".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();

                Toast.makeText(getContext(),"Error: "+message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(getContext(),"Product Image uploaded Succesfully..",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUri=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUri=task.getResult().toString();

                            loadingBar.dismiss();
//                            Toast.makeText(getContext(),"got product URL Succesfully..",Toast.LENGTH_SHORT).show();

                            DemoClass.Dlicence=downloadImageUri;
                        }
                    }
                });
            }
        });

    }
    private void openGallery() {
        Intent galleryInten=new Intent();
        galleryInten.setAction(Intent.ACTION_GET_CONTENT);
        galleryInten.setType("image/*");
        startActivityForResult(galleryInten,galleryPic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        if(requestCode==galleryPic&&resultCode==RESULT_OK&&data!=null){
            ImageUri=data.getData();
            imageview.setImageURI(ImageUri);
        }
    }

}
