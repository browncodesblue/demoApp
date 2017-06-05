package com.ibm.browna.grit3_android.Models;

import android.view.View;

/**
 * Created by browna on 2/7/2017.
 */

public class Tumbler {

    /**
     * This model represent any of the visual data that has words that are being cycled
     * In the first instance of this the word picker required a variety of tumblers and warrented
     * a class that had text with arrows on each side to be turned into its own object
     *
     */

    private int mLeftArrow;
    private int mRightArrow;
    private int mWordsTV;
    private String[] mWordArray;
    private View mParent;
    private int mId;
    private int mCurrentWord;

    public Tumbler(int mId) {
        this.mId = mId;
    }

    public Tumbler(int mLeftArrow, int mRightArrow, int mWords, String[] mWordArray,int i) {
        this.mLeftArrow = mLeftArrow;
        this.mRightArrow = mRightArrow;
        this.mWordArray = mWordArray;
        this.mWordsTV = mWords;
        this.mId = i;
        mCurrentWord = 0;

    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmLeftArrow() {
        return mLeftArrow;
    }

    public void setmLeftArrow(int mLeftArrow) {
        this.mLeftArrow = mLeftArrow;
    }

    public int getmRightArrow() {
        return mRightArrow;
    }

    public void setmRightArrow(int mRightArrow) {
        this.mRightArrow = mRightArrow;
    }

    public String[] getmWordArray() {
        return mWordArray;
    }

    public int getmWordsTV() {
        return mWordsTV;
    }

    public void setmWordsTV(int mWordsTV) {
        this.mWordsTV = mWordsTV;
    }

    public void setmCurrentWord(int mCurrentWord) {
        this.mCurrentWord = mCurrentWord;
    }

    public int getmCurrentWord() {
        return mCurrentWord;
    }

    public int getmId() {
        return mId;
    }
}
