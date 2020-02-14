package com.commutersfamily.commuterfamily.AddCar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.commutersfamily.commuterfamily.Activities.DriveActivity;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;
import com.commutersfamily.commuterfamily.R;
import com.commutersfamily.commuterfamily.SessionManager.SessionManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class DriverCnic extends Fragment  {
    private ImageView imageview,imageviewLicense;
    private Button btnSelectImage,btnSelectImageLicense;
    private FloatingActionButton next;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private StorageReference productImageReference;
    private DatabaseReference productRef;
    private ProgressDialog loadingBar;
    private String saveCurrentDate,saveCurrentTime,productRandomkey,downloadImageUri;
    private SessionManager sessionManager;

    private Uri ImageUri;
    private static final int galleryPic=1;

    public DriverCnic() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.driver_cnic,container,false);
        imageview=view.findViewById(R.id.cnic);
    sessionManager=new SessionManager(getContext());


        btnSelectImage=view.findViewById(R.id.add_cnic);
        next=view.findViewById(R.id.nextDriverCNIC);
        loadingBar=new
                ProgressDialog(getContext());
        productImageReference= FirebaseStorage.getInstance().getReference().child("CNIC");
        productRef= FirebaseDatabase.getInstance().getReference().child("Commuters");
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeProductInformation();
            }
        });

        return view;
    }

    private void storeProductInformation() {
        loadingBar.setTitle("Please Wait ...");

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
                Toast.makeText(getContext(),"Product Image uploaded Succesfully..",Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(getContext(),"got product URL Succesfully..",Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }


    private void saveProductInfoToDatabase() {
        HashMap<String,Object> productMap=new HashMap<>();

        productMap.put("vid", productRandomkey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("VehicleDocuments", DemoClass.docsURI);
        productMap.put("DriverCnic",downloadImageUri);
        productMap.put("DriverLicence",DemoClass.Dlicence);
        productMap.put("VehicleNumber",DemoClass.vnum);
        productMap.put("VehicleType",DemoClass.vtype);

        productRef.child("Driver").child(Prevalent.currentOnlineUser.getPhone()).child("Car").updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            loadingBar.dismiss();
                            Toast.makeText(getContext(),"Product is Added Succesfully..",Toast.LENGTH_SHORT).show();


                            sessionManager.createSessionOfKey(productRandomkey);
                            DemoClass.CarKey=productRandomkey;
                            startActivity(new Intent(getContext(),DriveActivity.class));
                        }
                        else {
                            loadingBar.dismiss();
                            final String messege=task.getException().toString();
                            Toast.makeText(getContext(),"Error: "+messege,Toast.LENGTH_SHORT).show();

                        }
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
