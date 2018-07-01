package com.gbolissimo.androidnotes.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gbolissimo.androidnotes.MainActivity;
import com.gbolissimo.androidnotes.R;
import com.gbolissimo.androidnotes.ViewActivity;
import com.gbolissimo.androidnotes.model.Notes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GBOLAHAN on 6/25/2018.


 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolderPix> implements Filterable {

    public List<Notes> aFireNotes,filterList;
    private Context bContext;
    private StorageReference storageReference;
    private ImageView pimag;
    private  CustomFilter filter;
    public  String img;
    public  String id;
    DownloadManager downloadManager;
    private DatabaseReference mPics;
    public NoteAdapter(Context context, List<Notes> fireNotes) {
        aFireNotes=fireNotes;
        bContext=context;
        filterList=fireNotes;
    }

    @Override
    public ViewHolderPix onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout for each row
        return new ViewHolderPix(bContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolderPix holder, int position) {

        Notes fireNotes=aFireNotes.get(position);

        // Set avatar


        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference riversRef = storageReference.child("images/"+fireNotes.getPix()+".jpg");

      //  Glide.with(holder.getPix().getContext())
        //        .using(new FirebaseImageLoader())
          //      .load(riversRef)
            //    .placeholder(R.drawable.holder).error(R.drawable.holder).override(400,295).fitCenter().into(holder.getPix());



        id=fireNotes.getId();


        holder.getTym().setText(DateUtils.getRelativeTimeSpanString(Long.parseLong((String) fireNotes.getTym())));
        holder.getTopic().setText(fireNotes.getTopic());
        holder.getContent().setText(fireNotes.getContent());















    }

    @Override
    public int getItemCount() {
        return aFireNotes.size();
    }

    public void refill(Notes newpost3) {

        // Add each user and notify recyclerView about change
        aFireNotes.add(newpost3);
        notifyDataSetChanged();
    }

    public void changeUser(int index, Notes newpost3) {

        // Handle change on each user and notify change
        aFireNotes.set(index,newpost3);
        notifyDataSetChanged();
    }




    /* ViewHolder for RecyclerView */
    public class ViewHolderPix extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView topic; // User avatar
        private TextView tym; // User first name
        private TextView content; // User presence
        public ImageView lflow; // User avatar
        public ImageView pix; // User avatar
        private DatabaseReference mNotes;

        //
        private Context mContextViewHolder7;

        public ViewHolderPix(Context context7, View itemView7) {
            super(itemView7);
            tym=(TextView)itemView.findViewById(R.id.thumbnail);
            content=(TextView)itemView.findViewById(R.id.tag);
            topic=(TextView)itemView.findViewById(R.id.thumbnail1);
            lflow=(ImageView)itemView.findViewById(R.id.overflow);
            pix=(ImageView)itemView.findViewById(R.id.profilepicad);
            mContextViewHolder7=context7;



            // Attach a click listener to the entire row view

            lflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(lflow);
                }
            });



            itemView7.setOnClickListener(this);
        }

        public ImageView getPix() {
            return pix;
        }

        public TextView getTopic() {
            return topic;
        }
        public TextView getContent() {
            return content;
        }

        public TextView getTym() {
            return tym;
        }


        @Override
        public void onClick(View view) {

            // Handle click on each row

            int position=getLayoutPosition(); // Get row position

            Notes fireNotes=aFireNotes.get(position); // Get use object

            // Provide current user username and time created at


            // Create a chat activity
            Intent chatIntent55=new Intent(mContextViewHolder7, ViewActivity.class);

            // Attach data to activity as a parcelable object
            chatIntent55.putExtra("tym", fireNotes.getTym());
            chatIntent55.putExtra("topic", fireNotes.getTopic());
            chatIntent55.putExtra("content", fireNotes.getContent());
            chatIntent55.putExtra("pix", fireNotes.getPix());
            chatIntent55.putExtra("id", fireNotes.getId());
            chatIntent55.putExtra("star", fireNotes.getStar());



            // Start new activity
            mContextViewHolder7.startActivity(chatIntent55);

        }

        private void showPopupMenu(View view) {
            // inflate menu
            PopupMenu popup = new PopupMenu(mContextViewHolder7, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_album, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
            popup.show();
        }

        /**
         * Click listener for popup menu items
         */
        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

            public MyMenuItemClickListener() {
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                        String it111m = "https://android-notes-1d7ac.firebaseio.com/"+user.getUid()+"/"+id;
                        mNotes = FirebaseDatabase.getInstance().getReferenceFromUrl(it111m);
                        mNotes.removeValue();
                        Toast.makeText( mContextViewHolder7, " Deleted ", Toast.LENGTH_LONG).show();
                        Intent intentw = new Intent(     mContextViewHolder7, MainActivity.class);
                        intentw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intentw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContextViewHolder7.startActivity(intentw);


                        //  islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        //     @Override
                        //   public void onSuccess(Uri uril) {
                        // Log.e("Tuts+", "uri: " + uril.toString());
                        //Handle whatever you're going to do with the URL here

                        //        downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                        //    Uri uri=Uri.parse(uril.toString());
                        //  DownloadManager.Request request=new DownloadManager.Request(uri);
                        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        //Long reference =downloadManager.enqueue(request);

                        //  Toast.makeText(mContextViewHolder7, "Downloading...",
                        //       Toast.LENGTH_SHORT).show();


//                            }
                        //});



                        return true;
                    case R.id.star:
                        Toast.makeText(mContextViewHolder7, "Share", Toast.LENGTH_SHORT).show();

                        //  String it111= "https://picmeme-5f78d.firebaseio.com/pics/"+id;
                        //mPics = FirebaseDatabase.getInstance().getReferenceFromUrl(it111);

                        // mPics.removeValue();
                        return true;
                    default:
                }
                return false;
            }
        }

    }



    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter((ArrayList<Notes>) filterList,this);
        }

        return filter;
    }

}

