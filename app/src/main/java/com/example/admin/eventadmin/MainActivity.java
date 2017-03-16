package com.example.admin.eventadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView r_event_list;
    private DatabaseReference mDatabaseRef;

    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Event");

        r_event_list = (RecyclerView) findViewById(R.id.request_EventList);
        r_event_list.setHasFixedSize(true);
        r_event_list.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               //refreshItems();
            }
        });
    }
    public void refreshItems(){
        //loadItems();
    }
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Event, RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, RequestViewHolder>(
                Event.class,
                R.layout.event_list_row,
                RequestViewHolder.class,
                mDatabaseRef
        ) {
            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, Event model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setStart_date(model.getStart_date());
                viewHolder.setEnd_date(model.getEnd_date());
                viewHolder.setStart_time(model.getStart_time());
                viewHolder.setEnd_time(model.getEnd_time());
                viewHolder.setClub(model.getClub());
                viewHolder.setFblink(model.getFblink());
                viewHolder.setWeblink(model.getWeblink());
                viewHolder.setContact(model.getContact());
                viewHolder.setImageUrl(getApplicationContext(), model.getImageUrl());

                swipeRefreshLayout.setRefreshing(false);

                final String desc = model.getDesc();
                final String imageurl = model.getImageUrl();
                final String start_date = model.getStart_date();
                final String end_date = model.getEnd_date();
                final String start_time = model.getStart_time();
                final String end_time = model.getEnd_time();
                final String club = model.getClub();
                final String category = model.getCategory();
                final String price = model.getPrice();
                final String title = model.getTitle();
                final String location = model.getLocation();
                final String fblink = model.getFblink();
                final String weblink = model.getWeblink();
                final String contact = model.getContact();


                final DatabaseReference aRef = FirebaseDatabase.getInstance().getReference();

                viewHolder.verify_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.setMessage("Approving Event");
                        progressDialog.show();
                        String key = aRef.child("Approved Events").push().getKey();
                        Event event = new Event(title, desc, location, category, price,
                                imageurl, start_date, end_date, start_time, end_time,
                                fblink, weblink, contact, club);

                        Map<String, Object> eventValues = event.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/ApprovedEvents/" + key, eventValues);

                        aRef.updateChildren(childUpdates);
                        progressDialog.dismiss();
                    }
                });
            }
        };
        r_event_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public Button verify_button;


        public RequestViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            verify_button = (Button) mView.findViewById(R.id.approve_button);

        }

        public void setTitle(String title) {
            TextView request_title = (TextView) mView.findViewById(R.id.request_title);
            request_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView request_desc = (TextView) mView.findViewById(R.id.request_desc);
            request_desc.setText(desc);
        }

        public void setLocation(String location) {
            TextView request_desc = (TextView) mView.findViewById(R.id.request_location);
            request_desc.setText(location);
        }

        public void setCategory(String category) {
            TextView request_category = (TextView) mView.findViewById(R.id.request_category);
            request_category.setText(category);
        }
        public void setPrice(String price) {
            TextView request_price = (TextView) mView.findViewById(R.id.request_price);
            request_price.setText(price);
        }

        public void setImageUrl(Context ctx, String imageUrl) {
            ImageView request_image = (ImageView) mView.findViewById(R.id.request_image);
            Picasso.with(ctx).load(imageUrl).into(request_image);
        }

        public void setStart_date(String start_date) {
            TextView request_start_date = (TextView) mView.findViewById(R.id.start_date);
            request_start_date.setText(start_date);
        }

        public void setEnd_date(String end_date) {
            TextView request_end_date = (TextView) mView.findViewById(R.id.end_date);
            request_end_date.setText(end_date);
        }

        public void setStart_time(String start_time) {
            TextView request_start_time = (TextView) mView.findViewById(R.id.start_time);
            request_start_time.setText(start_time);
        }

        public void setEnd_time(String end_time) {
            TextView request_end_time = (TextView) mView.findViewById(R.id.end_time);
            request_end_time.setText(end_time);
        }

        public void setClub(String club) {
            TextView request_club = (TextView) mView.findViewById(R.id.request_club);
            request_club.setText(club);
        }

        public void setFblink(String fblink) {
            TextView request_fblink = (TextView) mView.findViewById(R.id.request_fblink);
            request_fblink.setText(fblink);
        }
        public void setWeblink(String weblink){
            TextView request_weblink = (TextView) mView.findViewById(R.id.request_weblink);
            request_weblink.setText(weblink);
        }
        public void setContact(String contact){
            TextView request_contact = (TextView) mView.findViewById(R.id.request_contact);
            request_contact.setText(contact);
        }
    }
}