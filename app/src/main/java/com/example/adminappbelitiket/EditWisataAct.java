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

public class EditWisataAct extends AppCompatActivity {
    EditText etName, etDesc, etPrice, etLocation, etPhotospot, etWifi, etFestival, etKetentuan, etDate, etTime;
    Button btn_edit, btn_back, btn_edit_photo_wisata;
    Integer photo_max = 1;
    Uri photo_location;

    DatabaseReference reference, reference2;
    StorageReference storage;

    ImageView edit_pic_photo_wisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wisata);

        etName = findViewById(R.id.edit_name_wisata);
        etDesc = findViewById(R.id.edit_desc);
        etPrice = findViewById(R.id.edit_harga);
        etLocation = findViewById(R.id.edit_lokasi);
        etPhotospot = findViewById(R.id.edit_photospot);
        etWifi = findViewById(R.id.edit_wifi);
        etFestival = findViewById(R.id.edit_festival);
        etKetentuan = findViewById(R.id.edit_ketentuan);
        etDate = findViewById(R.id.edit_date_wisata);
        etTime = findViewById(R.id.edit_waktu_wisata);
        btn_edit = findViewById(R.id.btn_edit);
        btn_back = findViewById(R.id.btn_back);
        edit_pic_photo_wisata = findViewById(R.id.edit_pic_photo_wisata);
        btn_edit_photo_wisata = findViewById(R.id.btn_edit_photo_wisata);

        Bundle bundle = getIntent().getExtras();
        final String nama_wisata = bundle.getString("nama_wisata");

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                etName.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                etDesc.setText(dataSnapshot.child("short_desc").getValue().toString());
                etPrice.setText(dataSnapshot.child("harga_tiket").getValue().toString());
                etLocation.setText(dataSnapshot.child("lokasi").getValue().toString());
                etPhotospot.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                etWifi.setText(dataSnapshot.child("is_wifi").getValue().toString());
                etFestival.setText(dataSnapshot.child("is_festival").getValue().toString());
                etKetentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                etDate.setText(dataSnapshot.child("date_wisata").getValue().toString());
                etTime.setText(dataSnapshot.child("time_wisata").getValue().toString());
                Picasso.with(EditWisataAct.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(edit_pic_photo_wisata);
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

                reference2 = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_wisata").setValue(etName.getText().toString());
                        dataSnapshot.getRef().child("short_desc").setValue(etDesc.getText().toString());
                        dataSnapshot.getRef().child("harga_tiket").setValue(etPrice.getText());
                        dataSnapshot.getRef().child("lokasi").setValue(etLocation.getText().toString());
                        dataSnapshot.getRef().child("is_photo_spot").setValue(etPhotospot.getText().toString());
                        dataSnapshot.getRef().child("is_wifi").setValue(etWifi.getText().toString());
                        dataSnapshot.getRef().child("is_festival").setValue(etFestival.getText().toString());
                        dataSnapshot.getRef().child("ketentuan").setValue(etKetentuan.getText().toString());
                        dataSnapshot.getRef().child("date_wisata").setValue(etDate.getText().toString());
                        dataSnapshot.getRef().child("time_wisata").setValue(etTime.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (photo_location != null) {
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
                                            reference.getRef().child("url_thumbnail").setValue(uri_photo);
                                        }
                                    });
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            startActivity(new Intent(EditWisataAct.this, HomeAct.class));
                        }
                    });
                }
            }
        });
        btn_edit_photo_wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditWisataAct.this, HomeAct.class));
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(edit_pic_photo_wisata);

        }

    }
}
