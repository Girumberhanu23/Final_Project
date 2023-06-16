package com.example.balageru_user_app.Product;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class ProductDatabaseActivity {
    public interface UploadCallback {
        void onProgress(int progress);
        void onSuccess(String downloadUrl);
        void onFailure(String message);

    }


    public static void uploadImage(Uri selectedImageUri, UploadCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("productImages/" + UUID.randomUUID().toString());
        UploadTask uploadTask = storageRef.putFile(selectedImageUri);
        uploadTask.addOnProgressListener(snapshot -> {
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            if (callback != null) {
                callback.onProgress((int) progress);
            }
        }).addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                if (callback != null) {
                    callback.onSuccess(downloadUri.toString());
                }
            });
        }).addOnFailureListener(e -> {
            if (callback != null) {
                callback.onFailure(e.getMessage());
            }
        });
    }
    public static void postProduct(HashMap<String,Object> product, Context context) {
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        database.collection("Product").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Sucesssssssss", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
