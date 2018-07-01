package com.gbolissimo.androidnotes;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity  {

    private Button buttonSave;
    private DatabaseReference mNotes;
    private EditText tp;
    private EditText co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        tp=(EditText)findViewById(R.id.cc);
        co=(EditText)findViewById(R.id.ccz);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String it111m = "https://android-notes-1d7ac.firebaseio.com/"+user.getUid();
                mNotes = FirebaseDatabase.getInstance().getReferenceFromUrl(it111m);

                if (co.getText().toString().isEmpty()) {
                    Toast.makeText(WriteActivity.this, " Content cannot be empty ", Toast.LENGTH_LONG).show();
                }
                else {
                    final Long timestamp = System.currentTimeMillis();
                    String id = user.getUid() + timestamp.toString();
                    Map<String, Object> map22 = new HashMap<String, Object>();
                    map22.put("topic", tp.getText().toString());
                    map22.put("content", co.getText().toString());
                    map22.put("pix", id);
                    map22.put("author", user.getUid().toString());
                    map22.put("star", "no");
                    map22.put("id", id);
                    map22.put("tym", timestamp.toString());
                    mNotes.child(id).setValue(map22);
                    Toast.makeText(WriteActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    Intent intentw = new Intent(WriteActivity.this, MainActivity.class);
                    intentw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentw);
                }

                }
                 });

        }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (co.getText().toString().isEmpty()) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                  .setTitle("Discard").setMessage("Discard Note?")
                  .setNegativeButton(android.R.string.no,null)
               .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface arg0, int arg1) {

                       Intent intentwz = new Intent(WriteActivity.this, EndActivity.class);
                       startActivity(intentwz);

                       System.exit(0);

                 }}).create().show();

        }
    }
    }

