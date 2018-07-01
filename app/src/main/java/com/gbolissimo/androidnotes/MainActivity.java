package com.gbolissimo.androidnotes;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.gbolissimo.androidnotes.Adapter.NoteAdapter;
import com.gbolissimo.androidnotes.model.Notes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private RecyclerView notes_recycler;
    private View progress_bar;
    private String mCurrentUserUid;
    private DatabaseReference mNotes;
    private DatabaseReference mRequest;
    private FirebaseAuth mAuth;
    private NoteAdapter note_adapter;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView empty= (TextView) findViewById(R.id.empty);
        mAuth = FirebaseAuth.getInstance();
        sv= (SearchView) findViewById(R.id.mSearch);

                //Firebase Authentication
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mCurrentUserUid=user.getUid();
                    //load all entries
                    Query();

                    String it111rqx = "https://android-notes-1d7ac.firebaseio.com/"+mCurrentUserUid;
                    mRequest = FirebaseDatabase.getInstance().getReferenceFromUrl(it111rqx);
                    mRequest.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long numFollowers = dataSnapshot.getChildrenCount();
                            if(numFollowers>0){}
                            else { empty.setVisibility(View.VISIBLE);
                            hideProgressBarForUsers();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                        }
                    });

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    navigateToLogin();        }
            }
        };

        //Firebase Authentication Listener
        mAuth.addAuthStateListener(mAuthListener);
            //Search Notes
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                note_adapter.getFilter().filter(query);
                return false;
            }
        });
                //Floating Action Button to create new entry
        FloatingActionButton  fab = (FloatingActionButton) findViewById(R.id.fabb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPostIntentqw = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(newPostIntentqw);
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
       note_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
            MainActivity.this.finish();
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
    private void showProgressBarForUsers() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBarForUsers() {
        if (progress_bar.getVisibility() == View.VISIBLE) {
            progress_bar.setVisibility(View.GONE);
        }
    }

    private void navigateToLogin() {
        // Go to LogIn screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
        startActivity(intent);
    }

    private void Query() {
        // Get a reference to recyclerView
        notes_recycler = (RecyclerView) findViewById(R.id.notes_recycler);
        String it111= "https://android-notes-1d7ac.firebaseio.com/"+mCurrentUserUid;
        mNotes = FirebaseDatabase.getInstance().getReferenceFromUrl(it111);
        // Get a reference to progress bar
        progress_bar = findViewById(R.id.progress_bar);
        // Initialize adapter
        List<Notes> emptyListChat=new ArrayList<Notes>();
        note_adapter =new NoteAdapter(MainActivity.this,emptyListChat);
        clearData();

        LinearLayoutManager mLayoutManager= new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        notes_recycler.setLayoutManager(mLayoutManager);
        notes_recycler.setAdapter(note_adapter);
        showProgressBarForUsers();

        mNotes.orderByChild("tym").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e(TAG, "inside onChildAdded");
                //Hide progress bar
                hideProgressBarForUsers();
                Notes cm = dataSnapshot.getValue(Notes.class);
                note_adapter.refill(cm);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Notes cm = dataSnapshot.getValue(Notes.class);
                note_adapter.refill(cm);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });

    }

    private void clearData(){
        notes_recycler.setAdapter(null);
    }
}
