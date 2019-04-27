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

public class InputArticleAct extends AppCompatActivity {
    EditText etName,etDesc;
    Uri photo_location;
    ImageView pic_photo_article;
    Integer photo_max = 1;
    Button btn_upload,btn_add_pic_article;
    DatabaseReference reference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_article);

        etName = findViewById(R.id.name_article);
        etDesc = findViewById(R.id.desc_article);
        pic_photo_article = findViewById(R.id.pic_photo_article);
        btn_upload = findViewById(R.id.btn_upload_article);
        btn_add_pic_article = findViewById(R.id.btn_add_article_photo);

        btn_add_pic_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_upload.setEnabled(false);
                btn_upload.setText("Loading ...");

                // menyimpan kepada firebase
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Article").child(etName.getText().toString());
                storage = FirebaseStorage.getInstance().getReference().child("Photoarticle").child(etName.getText().toString());
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
                                            reference.getRef().child("url_photo_artikel").setValue(uri_photo);

                                        }
                                    });
                                    reference.getRef().child("nama_artikel").setValue(etName.getText().toString());
                                    reference.getRef().child("desc_artikel").setValue(etDesc.getText().toString());
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            // berpindah activity
                            Intent gotosuccess = new Intent(InputArticleAct.this, AddArticleAct.class);
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_article);

        }

    }
}
