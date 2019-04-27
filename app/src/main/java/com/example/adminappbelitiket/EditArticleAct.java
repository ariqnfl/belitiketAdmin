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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditArticleAct extends AppCompatActivity {
    EditText etName,etDesc;
    Button btn_back,btn_edit,btn_add_photo;
    Integer photo_max = 1;
    Uri photo_location;

    DatabaseReference reference, reference2;
    StorageReference storage;

    ImageView edit_pic_photo_artikel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
        etName = findViewById(R.id.edit_name_article);
        etDesc = findViewById(R.id.edit_desc_article);
        edit_pic_photo_artikel = findViewById(R.id.edit_pic_photo_article);
        btn_add_photo = findViewById(R.id.btn_edit_article_photo);
        btn_edit = findViewById(R.id.btn_edit_article);
        btn_back = findViewById(R.id.btn_back_article);

        Bundle bundle = getIntent().getExtras();
        final String nama_artikel = bundle.getString("nama_artikel");

        reference = FirebaseDatabase.getInstance().getReference().child("Article").child(nama_artikel);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                etName.setText(dataSnapshot.child("nama_artikel").getValue().toString());
                etDesc.setText(dataSnapshot.child("desc_artikel").getValue().toString());
                Picasso.with(EditArticleAct.this).load(dataSnapshot.child("url_photo_artikel").getValue().toString()).centerCrop().fit().into(edit_pic_photo_artikel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setEnabled(false);
                btn_edit.setText("EDITING...");

                reference2 = FirebaseDatabase.getInstance().getReference().child("Article").child(nama_artikel);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_artikel").setValue(etName.getText().toString());
                        dataSnapshot.getRef().child("desc_artikel").setValue(etDesc.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (photo_location != null){
                    StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_artikel").setValue(uri_photo);
                                        }
                                    });
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            startActivity(new Intent(EditArticleAct.this,AddArticleAct.class));
                        }
                    });
                }
            }
        });
        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditArticleAct.this,AddArticleAct.class));
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(edit_pic_photo_artikel);

        }

    }
}
