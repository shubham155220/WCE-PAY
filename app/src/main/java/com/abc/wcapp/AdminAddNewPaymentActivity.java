package com.abc.wcapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewPaymentActivity extends AppCompatActivity {

    private EditText fee,description,cast;
    private Button addfee;
    private ImageView photo;
    private String CategoryName ,feedescription,price,castname,savecurrentdate,savecurrenttime;
    private String RandomKey,downloadImageUrl;
    private DatabaseReference ProductRefference;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_payment);

        CategoryName = getIntent().getExtras().get("category").toString();

        cast = (EditText)findViewById(R.id.etcname) ;
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        ProductRefference = FirebaseDatabase.getInstance().getReference().child("Product");
        fee = (EditText)findViewById(R.id.etfee);
        addfee = (Button)findViewById(R.id.btaddfee);
        description = (EditText)findViewById(R.id.etdescription);
        photo = (ImageView)findViewById(R.id.ivphoto);
        loading = new ProgressDialog(this);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        addfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,GalleryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick  && resultCode == RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            photo.setImageURI(ImageUri);
        }
    }

    private void validate()
    {
        feedescription  = description.getText().toString();
        price = fee.getText().toString();
        castname = cast.getText().toString();
        if(ImageUri==null)
        {
            Toast.makeText(this,"Product image is mandatory...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(feedescription)||TextUtils.isEmpty(price)||TextUtils.isEmpty(castname))
        {
            Toast.makeText(this ,"Please fill all the information",Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeFeeInformation();
        }
    }

    private void storeFeeInformation() {
        loading.setTitle("Add New Product");
        loading.setMessage("Dear Admin, Please wait  while we are adding new product.");
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(calendar.getTime());
        RandomKey = savecurrentdate+savecurrenttime;
        final StorageReference filepath = ProductImagesRef.child(ImageUri.getLastPathSegment()+RandomKey + ".jpg");
        final UploadTask uploadTask = filepath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewPaymentActivity.this,"Error:"+message , Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddNewPaymentActivity.this,"Product image uploaded successfully",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AdminAddNewPaymentActivity.this,"Got product image in the storage",Toast.LENGTH_SHORT).show();
                            SaveInfoToDatabase();
                        }
                    }
                });

            }
        });

    }
    private  void SaveInfoToDatabase()
    {
        HashMap<String , Object>productMap = new HashMap<>();
        productMap.put("pid",RandomKey);
        productMap.put("Date",savecurrentdate);
        productMap.put("Time",savecurrenttime);
        productMap.put("Description",feedescription);
        productMap.put("Category",CategoryName);
        productMap.put("Image",downloadImageUrl);
        productMap.put("Price",price);
        //productMap.put("Cast Name",castname);
        ProductRefference.child(RandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful())
                      {
                          startActivity(new Intent(AdminAddNewPaymentActivity.this,AdminCatagoriesActivity.class));
                          loading.dismiss();
                          Toast.makeText(AdminAddNewPaymentActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                      }
                      else {
                          loading.dismiss();
                          String message = task.getException().toString();
                          Toast.makeText(AdminAddNewPaymentActivity.this,"Error:" +message,Toast.LENGTH_SHORT).show();
                      }
                    }
                });
        }
}
