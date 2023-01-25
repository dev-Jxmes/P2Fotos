package ao.uan.p2foto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddImage extends AppCompatActivity {
    MaterialButton add_image, upload_image,  go_back, select_album;
    ImageView imageView;
    String album_name = "";

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;

    FirebaseStorage storage;
    StorageReference reference;

    TextView filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        add_image = findViewById(R.id.choose_image_btn);
        upload_image = findViewById(R.id.upload_btn);
        select_album = findViewById(R.id.select_album_btn);
        go_back = findViewById(R.id.go_back_btn);
        imageView = findViewById(R.id.image_view);
        filename = findViewById(R.id.file_name);


        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        add_image.setOnClickListener(v -> adicionarImagem());
        upload_image.setOnClickListener(v -> uploadImage());
        select_album.setOnClickListener(v -> select_album());
        go_back.setOnClickListener(v -> voltar());
    }

    private void select_album() {

    }

    private void uploadImage() {
        final String random_key = UUID.randomUUID().toString();

        StorageReference ref = reference.child("images/"+album_name+"/"+random_key);
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Por Favor Aguarde...");
        pd.show();

        if (album_name.equals("")) {
            Support.printToast(AddImage.this, "PLEASE SELECT AN ALBUM.");
            return;
        }

        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Support.printToast(AddImage.this, "UPLOAD SUCCESSFUL");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Support.printToast(AddImage.this, "UPLOAD SUCCESSFUL");
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double percentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentagem: "+ (int) percentage + "%");
                    }
                });
    }

    void adicionarImagem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
        // https://youtu.be/CQ5qcJetYAI?t=261

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!= null) {
            uri = data.getData();
            imageView.setImageURI(uri);
            filename.setText(get_filename(uri, getApplicationContext())); // Image title
            Support.showButton(upload_image, true);

        } else {
            Support.printToast(AddImage.this, "ERROR");
        }
    }

    @SuppressLint("Range")
    String get_filename(Uri uri, Context context) {
        String result = "";

        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
            if (result.equals("")) {
                result = uri.getPath();
                int cutt = result.lastIndexOf('/');
                if (cutt != -1 ) {
                    result = result.substring(cutt +1);
                }
            }
        }
        return result;
    }

    void voltar(){
        startActivity(new Intent(AddImage.this, MainActivity.class));
    }
}