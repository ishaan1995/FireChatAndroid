package com.ishaan.firetest.Interfaces;

import com.ishaan.firetest.Models.User;

import java.util.List;

/**
 * Created by ishaan on 29/8/16.
 */
public interface ChatViewInterface {

    /**
     * reset message et state after message is uploaded
     */
    void resetMessageEt();

    void updateChat(List<User> T);

    void onMessageLoad(User T);

    void onRefreshComplete();


}
