package com.ishaan.firetest.Views.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ishaan.firetest.GlobalConstants.ChatConstants;
import com.ishaan.firetest.Helpers.Adapters.ChatAdapter;
import com.ishaan.firetest.Interfaces.ChatViewInterface;
import com.ishaan.firetest.Models.User;
import com.ishaan.firetest.Presenters.ChatPresenter;
import com.ishaan.firetest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements ChatViewInterface{

    @BindView(R.id.et_chat_message) EditText mChatMessageEt;
    @BindView(R.id.ib_chat_send) ImageButton mChatSendIb;
    @BindView(R.id.rv_chat) RecyclerView mChatRv;
    @BindView(R.id.srl_chat) SwipeRefreshLayout mSwipeRefreshLayout;

    private ChatPresenter mChatPresenter;
    private ChatAdapter mChatAdapter;
    private List<User> mUserMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initViewProps();
    }

    public void initViewProps(){
        if (mChatPresenter == null) mChatPresenter = new ChatPresenter(ChatActivity.this);
        mChatPresenter.loadChatMessages();
        //mChatPresenter.loadMessages();
        initListeners();
    }

    @Override
    public void onMessageLoad(User mUserMessage){
        if (mUserMessages == null){
            mUserMessages = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mChatAdapter = new ChatAdapter(ChatActivity.this, this.mUserMessages);
            mChatRv.setLayoutManager(linearLayoutManager);
            mChatRv.setAdapter(mChatAdapter);
        }
        mUserMessages.add(mUserMessage);
        mChatAdapter.notifyItemChanged(mUserMessages.size() - 1);
    }

    @Override
    public void onRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void initListeners(){
        mChatSendIb.setOnClickListener(onClickListener);
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeRefreshLayout.setRefreshing(true);
            mChatPresenter.refreshChat();
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ib_chat_send:
                    if (mChatMessageEt.getText().toString().length()!=0) mChatPresenter.
                            sendMessage(mChatMessageEt.getText().toString());
                    break;
            }
        }
    };

    @Override
    public void resetMessageEt(){
        mChatMessageEt.setText(ChatConstants.BLANK);
    }

    @Override
    public void updateChat(List<User> mMessages) {

        if (mChatAdapter==null){
            this.mUserMessages = mMessages;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mChatAdapter = new ChatAdapter(ChatActivity.this, this.mUserMessages);
            mChatRv.setLayoutManager(linearLayoutManager);
            mChatRv.setAdapter(mChatAdapter);
            mChatAdapter.notifyDataSetChanged();
        }else {
            Collections.reverse(mUserMessages);
            mUserMessages.addAll(mMessages);
            Collections.reverse(mUserMessages);
            mChatAdapter.notifyDataSetChanged();
        }
    }

}
