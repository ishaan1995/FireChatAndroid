package com.ishaan.firetest.Helpers.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ishaan.firetest.GlobalConstants.ChatConstants;
import com.ishaan.firetest.GlobalConstants.Constants;
import com.ishaan.firetest.Models.User;
import com.ishaan.firetest.R;
import com.ishaan.firetest.Views.ViewHolders.MessageViewHolder;
import java.util.List;

/**
 * Created by ishaan on 29/8/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HOLDER_TEXT = 201;

    private static int sTxtMaxWidth;

    private List<User> mUserMessages;
    private Context mContext;

    public ChatAdapter(Context mContext, List<User> mUserMessages){

        this.mUserMessages = mUserMessages;
        this.mContext = mContext;
        setAttachmentDimens();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case HOLDER_TEXT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
                return new MessageViewHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
                return new MessageViewHolder(v);
        }
    }

    private void setAttachmentDimens() {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int deviceHeight = metrics.heightPixels;
        int deviceWidth = metrics.widthPixels;
        int sAttachmentHeight = (int) (metrics.heightPixels * .3f);
        int sAttachmentWidth = (int) (metrics.widthPixels * .5f);
        sTxtMaxWidth = (int) (metrics.widthPixels * .75f);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Invalid
        if (position < 0)
            return;

        switch (mUserMessages.get(position).getMessageType()){
            case ChatConstants.TYPE_TEXT:
                MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
                boolean isUserMessage = mUserMessages.get(position).getSenderId().equals(ChatConstants.userId);
                messageViewHolder.resetView();
                messageViewHolder.setMaxWidth(sTxtMaxWidth);
                messageViewHolder.setView(isUserMessage, mUserMessages.get(position).getMessage());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mUserMessages.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (mUserMessages.get(position).getMessageType()){
            case ChatConstants.TYPE_TEXT:
                return HOLDER_TEXT;
            default:
                return HOLDER_TEXT;
        }
    }

}
