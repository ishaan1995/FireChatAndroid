package com.ishaan.firetest.Presenters;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ishaan.firetest.GlobalConstants.ChatConstants;
import com.ishaan.firetest.GlobalConstants.Constants;
import com.ishaan.firetest.Interfaces.ChatViewInterface;
import com.ishaan.firetest.Models.User;
import com.ishaan.firetest.R;
import com.ishaan.firetest.Views.Activities.ChatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindString;

/**
 * Created by ishaan on 29/8/16.
 */
public class ChatPresenter {

    ChatViewInterface mChatView;
    private boolean firstLoad = true;
    private Gson gson;
    private List<User> mUserMesssages = new ArrayList<>();
    public static final String TAG = "ChatPresenter";
    public long mLastMessageTimeStamp = -1;
    public boolean isFirstMessage = false;
    private List<User> mInitMessages;

    FirebaseDatabase database;
    DatabaseReference myMessageRef, myChatRef;

    public ChatPresenter(ChatViewInterface mChatView){
        this.mChatView = mChatView;
        gson = new Gson();
        //setupFirebase();
    }

    public void sendMessage(String message){
        User user = new User(message, ChatConstants.userId, ChatConstants.TYPE_TEXT);
        DatabaseReference data = myChatRef.push();
        data.setValue(user);
        mChatView.resetMessageEt();
    }

    public void loadMessages(){
        myChatRef.addChildEventListener(childEventListener);
        myChatRef.addValueEventListener(valueEventListener);
    }

    public void addChildListener(){
        //myChatRef.addChildEventListener(childEventListener);
    }

    public void loadChatMessages(){
        if (mLastMessageTimeStamp == -1){
            addInitMessages();
            //addChildListener();
        }
    }

    public void addInitMessages(){
        mInitMessages = new ArrayList<>();
        Query mMessages = myChatRef.limitToLast(5);
        mMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User message = dataSnapshot.getValue(User.class);
                mUserMesssages.add(message);
                mChatView.onMessageLoad(message);
                //mChatView.updateChat(mUserMesssages);
                if (!isFirstMessage){
                    isFirstMessage = true;
                    mLastMessageTimeStamp = message.getTimeStamp();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*mMessages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                mInitMessages = getMoreMessages(iterator);
                mUserMesssages.addAll(mInitMessages);
                updateChatList(mUserMesssages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void setupFirebase() {

        database= FirebaseDatabase.getInstance();
        myMessageRef = database.getReference(ChatConstants.MESSAGE);
        myChatRef = myMessageRef.child(ChatConstants.MESSAGE_CHILD);
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (!firstLoad){
                User message = dataSnapshot.getValue(User.class);
                mUserMesssages.add(message);
                mChatView.updateChat(mUserMesssages);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (firstLoad){
                Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                getMessages(iterator);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void getMessages(Iterable<DataSnapshot> iterator) {
        while (iterator.iterator().hasNext()){
            User message = iterator.iterator().next().getValue(User.class);
            mUserMesssages.add(message);
            Log.d(TAG, "Message " + gson.toJson(message, User.class));
        }
        updateChatList(mUserMesssages);
    }

    public List<User> getMoreMessages(Iterable<DataSnapshot> iterable){
        List<User> mMsg = new ArrayList<>();
        while (iterable.iterator().hasNext()){
            User message = iterable.iterator().next().getValue(User.class);
            mMsg.add(message);
            mLastMessageTimeStamp = message.getTimeStamp();
            Log.d(TAG, "Message " + gson.toJson(message, User.class));
        }
        return mMsg;
    }

    public void updateChatList(List<User> mUserMesssages){
        firstLoad = false;
        mChatView.updateChat(mUserMesssages);
    }

    public void refreshChat(){
        //isFirstMessage = false;
        mInitMessages = new ArrayList<>();

        Query mMessages = myChatRef.orderByChild(ChatConstants.timeStamp).
                startAt((double) mLastMessageTimeStamp).limitToFirst(5);

        mMessages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Data snap : " +  dataSnapshot.toString());
                Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                if (iterator.iterator().hasNext()) iterator.iterator().next();
                mInitMessages = getMoreMessages(iterator);
                Log.d(TAG, "new messages size : " +  mInitMessages.size());
                mChatView.onRefreshComplete();
                mChatView.updateChat(mInitMessages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
