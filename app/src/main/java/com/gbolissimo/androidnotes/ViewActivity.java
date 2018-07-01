package com.gbolissimo.androidnotes;

        import android.content.ClipData;
        import android.content.ClipboardManager;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.format.DateUtils;
         import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;


public class ViewActivity extends AppCompatActivity {
    private static final String TAG =   ViewActivity.class.getSimpleName();

    /* Reference to firebase */
    private DatabaseReference mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
            //Views
        TextView a1 = (TextView) findViewById(R.id.a1);
        TextView a2 = (TextView) findViewById(R.id.a2);
        TextView a3 = (TextView) findViewById(R.id.a3);
        Button copy = (Button) findViewById(R.id.copy);
        Button edit = (Button) findViewById(R.id.edit);
        Button share = (Button) findViewById(R.id.share);
        Button delete = (Button) findViewById(R.id.delete);
        final Button delete2 = (Button) findViewById(R.id.delete2);
                //Get Data from previous Activity
        final Intent intent=getIntent();
        String tym= intent.getStringExtra("tym");
        final String topic= intent.getStringExtra("topic");
        final String content= intent.getStringExtra("content");
        final String pix= intent.getStringExtra("pix");
        final String id= intent.getStringExtra("id");
        final String star= intent.getStringExtra("star");

        a1.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong((String) tym)));
        a2.setText(topic);
        a3.setText(content);

        //Button OnClickListener
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clip= ClipData.newPlainText("label",content);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPostIntentqw = new Intent(ViewActivity.this, EditActivity.class);
                newPostIntentqw.putExtra("topic", topic);
                newPostIntentqw.putExtra("content", content);
                newPostIntentqw.putExtra("pix", pix);
                newPostIntentqw.putExtra("id", id);
                newPostIntentqw.putExtra("star", star);
                startActivity(newPostIntentqw);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             delete2.setVisibility(View.VISIBLE);
            }
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //Firebase User Reference
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String it111m = "https://android-notes-1d7ac.firebaseio.com/"+user.getUid()+"/"+id;
                mNotes = FirebaseDatabase.getInstance().getReferenceFromUrl(it111m);
                mNotes.removeValue();
                Toast.makeText(ViewActivity.this, " Deleted ", Toast.LENGTH_LONG).show();
                Intent intentw = new Intent(    ViewActivity.this, MainActivity.class);
                intentw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentw);
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
        //Log.e(TAG, "I am onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.e(TAG, "I am onStop");
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
    super.onBackPressed();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_logout:
                logout();
                return true;

            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    }