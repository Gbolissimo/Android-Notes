package com.gbolissimo.androidnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    //Buttons
    private Button button_save;
    private DatabaseReference l5;
    //ImageView
    private DatabaseReference mNotes;
    private EditText title_edit;
    private EditText content_edit;
    private String idd;
    private String pix;
    private String star;

    //firebase storage reference
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //getting views from layout
        button_save = (Button) findViewById(R.id.buttonUpload2);
        title_edit=(EditText)findViewById(R.id.cc22);
        content_edit=(EditText)findViewById(R.id.ccz2);

        final Intent intent=getIntent();
        String tym= intent.getStringExtra("tym");
        final String topic= intent.getStringExtra("topic");
        final String content= intent.getStringExtra("content");
         pix= intent.getStringExtra("pix");
         idd= intent.getStringExtra("id");
         star= intent.getStringExtra("star");

        title_edit.setText(topic);
        content_edit.setText(content);

        //getting firebase storage reference
        storageReference = FirebaseStorage.getInstance().getReference();

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String it111m = "https://android-notes-1d7ac.firebaseio.com/"+user.getUid();
                mNotes = FirebaseDatabase.getInstance().getReferenceFromUrl(it111m);

                if (content_edit.getText().toString().isEmpty()) {
                    Toast.makeText(EditActivity.this, " Content cannot be empty ", Toast.LENGTH_LONG).show();
                }
                else {
                    final Long timestamp = System.currentTimeMillis();


                    Map<String, Object> map22 = new HashMap<String, Object>();
                    map22.put("topic", title_edit.getText().toString()); // The authentication method used
                    map22.put("content", content_edit.getText().toString());
                    map22.put("pix", "hdgdhhfhdh");
                    map22.put("author", user.getUid().toString());
                    map22.put("star", star);
                    map22.put("id", idd);
                    map22.put("tym", timestamp.toString());
                    mNotes.child(idd).setValue(map22);
                    Toast.makeText(EditActivity.this, " Saved ", Toast.LENGTH_LONG).show();


                    Intent intentw = new Intent(EditActivity.this, MainActivity.class);
                    intentw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentw);
                }

            }
        });


    }
    }

