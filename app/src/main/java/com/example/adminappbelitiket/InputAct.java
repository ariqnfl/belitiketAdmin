package com.example.adminappbelitiket;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class InputAct extends AppCompatActivity {
    EditText etName,etDesc,etPrice;
    Uri photo_location;
    ImageView pic_photo_menu;
    Integer photo_max = 1;
    Button btn_upload,btn_add_pic_menu;

    DatabaseReference reference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etName = findViewById(R.id.name_menu);
        etDesc = findViewById(R.id.desc);
        etPrice = findViewById(R.id.harga);
        pic_photo_menu = findViewById(R.id.pic_photo_menu);
        btn_upload = findViewById(R.id.btn_upload);
        btn_add_pic_menu = findViewById(R.id.btn_add_menu_photo);

        btn_add_pic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ubah state menjadi loading
                btn_upload.setEnabled(false);
                btn_upload.setText("Loading ...");

                // menyimpan kepada firebase
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Menu").child(etName.getText().toString());
                storage = FirebaseStorage.getInstance().getReference().child("Photomenu").child(etName.getText().toString());

                // validasi untuk file (apakah ada?)
                if (photo_location != null) {
                    StorageReference storageReference1 =
                            storage.child(System.currentTimeMillis() + "." +
                                    getFileExtension(photo_location));

                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task =taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_menu").setValue(uri_photo);

                                        }
                                    });
                                    reference.getRef().child("nama_makanan").setValue(etName.getText().toString());
                                    reference.getRef().child("deskripsi").setValue(etDesc.getText().toString());
                                    reference.getRef().child("harga").setValue(etPrice.getText().toString());
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            // berpindah activity
                            Intent gotosuccess = new Intent(InputAct.this, HomeAct.class);
                            startActivity(gotosuccess);
                        }
                    });
                }

            }
        });
    }

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null) {

            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_menu);

        }

    }


}
