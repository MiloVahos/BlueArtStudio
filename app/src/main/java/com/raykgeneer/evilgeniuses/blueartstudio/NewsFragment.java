package com.raykgeneer.evilgeniuses.blueartstudio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class NewsFragment extends Fragment {

    //DECLARACIÓN DE ELEMENTOS
    private ImageButton IBNewPost;
    private EditText ETitle;
    private EditText EDescription;
    private Button BSubmit;

    //DECLARACIÓN DE VARIABLES
    private String Title;
    private String Description;
    private Uri imageUri = null;
    private String uniqueID;


    private StorageReference mStorageRef;
    private ProgressDialog mProgress;//Progress dialog bar

    private DatabaseReference mRef;

    private static final int GALLERY_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference().child("News"); //Reference to the firebase carpet

        IBNewPost = (ImageButton) v.findViewById(R.id.IBNewPost);
        ETitle = (EditText) v.findViewById(R.id.ETitle);
        EDescription = (EditText) v.findViewById(R.id.EDescription);
        BSubmit = (Button) v.findViewById(R.id.BSubmit);

        mProgress = new ProgressDialog(v.getContext());


        //New post image intent
        IBNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent =  new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        BSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

        return v;
    }

    private void startPosting() {

        mProgress.setMessage("Posting on App...");
        mProgress.show();

        Title = ETitle.getText().toString().trim();
        Description = EDescription.getText().toString().trim();
        //There is going to be two types of post, one with image and other without image
        if(!TextUtils.isEmpty(Title) && !TextUtils.isEmpty(Description) &&  (imageUri != null)){
            //In this case there is going to be text and image
            uniqueID = UUID.randomUUID().toString(); //UNIQUE ID OF THE POST
            StorageReference filepath =  mStorageRef.child("Posts").child(uniqueID);
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    mRef.child(uniqueID).child("Titulo").setValue(Title);
                    mRef.child(uniqueID).child("Descripcion").setValue(Description);
                    mRef.child(uniqueID).child("ImageURL").setValue(downloadUri.toString());
                    mProgress.dismiss();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            });
        }else if(!TextUtils.isEmpty(Title) && !TextUtils.isEmpty(Description) &&  (imageUri == null)){
            uniqueID = UUID.randomUUID().toString(); //UNIQUE ID OF THE POST
            mRef.child(uniqueID).child("Titulo").setValue(Title);
            mRef.child(uniqueID).child("Descripcion").setValue(Description);
            mRef.child(uniqueID).child("ImageURL").setValue("null");
            mProgress.dismiss();
            Toast.makeText(getActivity(),"Tu Post ha cargado con exito",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }else{
            Toast.makeText(getActivity(),"Debes llenar los campos de texto",Toast.LENGTH_SHORT).show();
            mProgress.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST &&  resultCode == getActivity().RESULT_OK){
            imageUri = data.getData();
            IBNewPost.setImageURI(imageUri);
        }
    }

}
