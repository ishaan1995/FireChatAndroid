package com.ishaan.firetest;

import com.ishaan.firetest.GlobalConstants.ChatConstants;
import com.ishaan.firetest.Interfaces.ChatViewInterface;
import com.ishaan.firetest.Models.User;
import com.ishaan.firetest.Presenters.ChatPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    MockClass mockClassView;
    ChatPresenter chatPresenter;
    List<User> mGivenUsers = new ArrayList<>();

    @Test
    public void testDataFlow() throws Exception{
        //Arrange
        mGivenUsers = giveList(mGivenUsers);

        //Act
        chatPresenter.updateChatList(mGivenUsers);

        //Assert
        assetEqauals(mGivenUsers, mockClassView.mUserMessages);

    }

    @Test
    public void Test2() throws Exception{
        //Arrange
        mGivenUsers.add(new User("blah blah rocks" + 10 , ChatConstants.userId, ChatConstants.TYPE_TEXT) );

        //Act
        mockClassView.updateChat(mGivenUsers);

        //Assert
        assetEqauals(mGivenUsers, mockClassView.mUserMessages);
    }

    public void assetEqauals(List<User> mExpected, List<User> mOutput){
        assertEquals(mExpected.size(), mOutput.size());
        for (int i=0; i<mOutput.size(); i++){
            assertEquals(mExpected.get(i), mOutput.get(i));
        }
    }

    public List<User> giveList(List<User> userList){
        for (int i=0; i<10 ; i++){
            User user = new User("blah " + i , ChatConstants.userId, ChatConstants.TYPE_TEXT);
            userList.add(user);
        }
        return userList;
    }

    @Before
    public void setUpViews() throws Exception{
        mockClassView = new MockClass();
        chatPresenter = new ChatPresenter(mockClassView);
    }

    public class MockClass implements ChatViewInterface{

        List<User> mUserMessages;

        @Override
        public void resetMessageEt() {
            //done
        }

        @Override
        public void updateChat(List<User> T) {
            //done
            this.mUserMessages = T;
        }

        @Override
        public void onMessageLoad(User T) {

        }

        @Override
        public void onRefreshComplete() {

        }

        public void onMessageLoad(List<User> T) {
            //done something
            //this.mUserMessages = T;
        }
    }
}
