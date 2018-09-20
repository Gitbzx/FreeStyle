package com.bzx.vmovie.microfilm.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bzx.vmovie.microfilm.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Describe:自定义键盘工具类
 * Created by bzx on 2018/8/24/024
 * Email:seeyou_x@126.com
 */

public class KeyboardUtil {
    private Activity mActivity;
    @SuppressWarnings("unused")
    private Context mContext;
    @SuppressWarnings("unused")
    private KeyboardView keyboardView;
    private Keyboard keyAlp;
    private Keyboard keyDig;
    private Keyboard keyFuhao;
    public boolean isnun = false;
    public boolean isupper = false;
    public boolean isfuhao = false;

    private EditText ed;

    public KeyboardUtil(Activity mActivity,Context mContext, EditText edit) {
        this.mActivity=mActivity;
        this.mContext = mContext;
        this.ed = edit;
        keyAlp = new Keyboard(mContext, R.xml.qwerty);
        keyDig = new Keyboard(mContext, R.xml.symbols);
        keyFuhao = new Keyboard(mContext, R.xml.fuhao);
        keyboardView = (KeyboardView) mActivity.findViewById(R.id.keyboard_view);
        randomalpkey();
        //keyboardView.setKeyboard(keyAlp);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (!TextUtils.isEmpty(editable)) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
                changeKey();
                keyboardView.setKeyboard(keyAlp);

            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
                if (isnun) {
                    isnun = false;
                    randomalpkey();
                } else {
                    isnun = true;
                    randomdigkey();
                }
            }else if(primaryCode == -2018){
                if (isfuhao) {
                    isfuhao = false;
                    randomalpkey();
                } else {
                    isfuhao = true;
                    keyboardView.setKeyboard(keyFuhao);
                }
            }else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    };


    private void changeKey() {
        List<Keyboard.Key> keylist = keyAlp.getKeys();
        if (isupper) {
            isupper = false;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {
            isupper = true;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isNumber(String str) {
        String wordstr = "0123456789";
        if (wordstr.indexOf(str) > -1) {
            return true;
        }
        return false;
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }
    private void randomdigkey(){
        List<Keyboard.Key> keyList = keyDig.getKeys();
        List<Keyboard.Key> newkeyList = new ArrayList<Keyboard.Key>();
        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i).label != null
                    && isNumber(keyList.get(i).label.toString())) {
                newkeyList.add(keyList.get(i));
            }
        }
        int count = newkeyList.size();
        List<KeyModel> resultList = new ArrayList<KeyModel>();
        LinkedList<KeyModel> temp = new LinkedList<KeyModel>();
        for (int i = 0; i < count; i++) {
            temp.add(new KeyModel(48 + i, i + ""));
        }
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            resultList.add(new KeyModel(temp.get(num).getCode(),
                    temp.get(num).getLable()));
            temp.remove(num);
        }
        for (int i = 0; i < newkeyList.size(); i++) {
            newkeyList.get(i).label = resultList.get(i).getLable();
            newkeyList.get(i).codes[0] = resultList.get(i)
                    .getCode();
        }

        keyboardView.setKeyboard(keyDig);
    }
    private void randomalpkey(){
        List<Keyboard.Key> keyList = keyAlp.getKeys();
        List<Keyboard.Key> newkeyList = new ArrayList<Keyboard.Key>();
        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i).label != null
                    && isword(keyList.get(i).label.toString())) {
                newkeyList.add(keyList.get(i));
            }
        }
        int count = newkeyList.size();
        List<KeyModel> resultList = new ArrayList<KeyModel>();
        LinkedList<KeyModel> temp = new LinkedList<KeyModel>();
        for (int i = 0; i < count; i++) {
            temp.add(new KeyModel(97 + i, "" + (char) (97 + i)));
        }
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            resultList.add(new KeyModel(temp.get(num).getCode(),
                    temp.get(num).getLable()));
            temp.remove(num);
        }
        for (int i = 0; i < newkeyList.size(); i++) {
            newkeyList.get(i).label = resultList.get(i).getLable();
            newkeyList.get(i).codes[0] = resultList.get(i)
                    .getCode();
        }

        keyboardView.setKeyboard(keyAlp);
    }
}
