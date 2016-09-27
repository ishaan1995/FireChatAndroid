package com.ishaan.firetest.Views.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ishaan.firetest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ishaan on 29/8/16.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.rl_chat_msg_received) RelativeLayout mMessageRecRl;
    @BindView(R.id.rl_chat_msg_sent) RelativeLayout mMessageSentRl;
    @BindView(R.id.tv_chat_msg_received) TextView mMessageRecTv;
    @BindView(R.id.tv_chat_msg_sent) TextView mMessageSentTv;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void resetView(){
        mMessageRecRl.setVisibility(View.GONE);
        mMessageSentRl.setVisibility(View.GONE);
    }

    public void setMaxWidth(int maxWidth){
        mMessageRecTv.setMaxWidth(maxWidth);
        mMessageSentTv.setMaxWidth(maxWidth);
    }

    public void setView(boolean isUserMessage, String message){
        if (isUserMessage){
            mMessageSentRl.setVisibility(View.VISIBLE);
            mMessageSentTv.setText(message);
        }else {
            mMessageRecRl.setVisibility(View.VISIBLE);
            mMessageRecTv.setText(message);
        }
    }
}
