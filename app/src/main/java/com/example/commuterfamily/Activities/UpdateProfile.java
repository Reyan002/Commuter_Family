package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends AppCompatActivity {

    private CircleImageView profile_image_view;
    private TextView close_btn,update_btn,profile_change_btn;
    private EditText phone_ed,fname_ed,adress_ed;
    private Uri imageUri;
    private String Url="";
    private StorageReference storageReferencePicture;
    private String checker="";
    private StorageTask uploadTask;
    private String myUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        storageReferencePicture= FirebaseStorage.getInstance().getReference().child("Profile pictures");
        profile_image_view=(CircleImageView) findViewById(R.id.setting_profile_image_change);
        close_btn=(TextView)findViewById(R.id.setting_close);
        update_btn=(TextView)findViewById(R.id.setting_update);
        profile_change_btn=(TextView)findViewById(R.id.setting_profile_image_change_btn);

        phone_ed=(EditText)findViewById(R.id.setting_phone_number);
        fname_ed=(EditText)findViewById(R.id.setting_full_name);
        adress_ed=(EditText)findViewById(R.id.setting_address);

        userPrfileinfo(profile_image_view,fname_ed,phone_ed,adress_ed);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    userInfoSaved();
                }
                else{
                    updateOnlyUserInfo();
                }
            }
        });

        profile_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(UpdateProfile.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode , resultCode , data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();

            profile_image_view.setImageURI(imageUri);
        }
        else{

            Toast.makeText(this,"Error Try Again",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UpdateProfile.this,UpdateProfile.class));
            finish();
        }

    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("Name", fname_ed.getText().toString());
        userMap. put("Phone", adress_ed.getText().toString());
        userMap. put("Email", phone_ed.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(UpdateProfile.this, DashboardDrawerActivity.class));
        Toast.makeText(UpdateProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void userInfoSaved()
    {
        if(TextUtils.isEmpty(fname_ed.getText().toString())){
            Toast.makeText(this,"Name is Mandatory",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(phone_ed.getText().toString())){
            Toast.makeText(this,"Phone is Mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(adress_ed.getText().toString())){
            Toast.makeText(this,"Adress is Mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked")){

            uploadImage();

        }

    }

    private void uploadImage() {
        final ProgressDialog loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Update Profile");
        loadingBar.setMessage("Please Wait, while we are updating your Account info");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        if(imageUri!=null){
            final StorageReference fileRef=storageReferencePicture
                    .child(Prevalent.currentOnlineUser.getPhone()+".jpg");
            uploadTask=fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUrl=task.getResult();

                        myUrl = downloadUrl.toString();
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String,Object> userMap=new HashMap<>();
                        userMap.put("Name",fname_ed.getText().toString());
                        userMap.put("Adress",adress_ed.getText().toString());
                        userMap.put("PhoneOrder",phone_ed.getText().toString());
                        userMap.put("image",myUrl);
                        reference.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                        loadingBar.dismiss();

                        startActivity(new Intent(UpdateProfile.this,DashboardDrawerActivity.class));

                        Toast.makeText(UpdateProfile.this,"Profile Info Updated Succesfully",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(UpdateProfile.this,"Error:",Toast.LENGTH_SHORT).show();

                    }
                }
            });//onFailure
        }
        else{
            Toast.makeText(UpdateProfile.this,"Image Is not Selected",Toast.LENGTH_SHORT).show();

        }

    }

    private void userPrfileinfo(final CircleImageView profile_image_view , final EditText fname_ed , final EditText phone_ed , final EditText adress_ed) {

        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("image").exists()){
                        String image=dataSnapshot.child("image").getValue().toString();
                        String name=dataSnapshot.child("Name").getValue().toString();
                        String phone=dataSnapshot.child("Phone").getValue().toString();
                        String adress=dataSnapshot.child("Adress").getValue().toString();

                        Picasso.get().load(image).into(profile_image_view);
                        fname_ed.setText(name);
                        phone_ed.setText(phone);
                        adress_ed.setText(adress);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
