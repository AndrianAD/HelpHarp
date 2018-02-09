package com.example.user.helpharp;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static com.example.user.helpharp.MainActivity.calculate;
import static com.example.user.helpharp.MainActivity.userHarp;
import static com.example.user.helpharp.MainActivity.needHarp;

public class CustomKeyboard {
    /**
     * A link to the KeyboardView that is used to render this CustomKeyboard.
     */
    private KeyboardView mKeyboardView;
    /**
     * A link to the activity that hosts the {@link #mKeyboardView}.
     */
    private Activity mHostActivity;
    /**
     * The key (code) handler.
     */
    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        public final static int CodeDelete = -5; // Keyboard.KEYCODE_DELETE
        public final static int CodeCancel = -3; // Keyboard.KEYCODE_CANCEL
        public final static int Ten = 55005;

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this input_list in primaryCode
            // Get the EditText and its Editable
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            if (focusCurrent == null) return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            // Apply the key to the edittext
            if (primaryCode == CodeCancel) {
                hideCustomKeyboard();
            } else if (primaryCode == CodeDelete) {
                if (editable != null && start > 0) editable.delete(start - 1, start);
                calculate(userHarp, needHarp);

            } else if (primaryCode == Ten) {
                editable.insert(start, "10");
                calculate(userHarp, needHarp);

            } else { // insert character
                editable.insert(start, Character.toString((char) primaryCode));
                calculate(userHarp, needHarp);

            }
        }

        @Override
        public void onPress(int arg0) {
        }
        @Override
        public void onRelease(int primaryCode) {
        }
        @Override
        public void onText(CharSequence text) {
        }
        @Override
        public void swipeDown() {
        }
        @Override
        public void swipeLeft() {
        }
        @Override
        public void swipeRight() {
        }
        @Override
        public void swipeUp() {
        }
    };


    public CustomKeyboard(Activity host, int viewid, int layoutid) {
        mHostActivity = host;
        mKeyboardView = (KeyboardView) mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    /**
     * Returns whether the CustomKeyboard is visible.
     */
    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }
    /**
     * Make the CustomKeyboard visible, and hide the system keyboard for view v.
     */
    public void showCustomKeyboard(View v) {
        if (v != null)
            ((InputMethodManager) mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
    }

    /**
     * Make the CustomKeyboard invisible.
     */
    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }
    /**
     * Register <var>EditText<var> with resource id <var>resid</var> (on the hosting activity) for using this custom keyboard.
     *
     * @param resid The resource id of the EditText that registers to the custom keyboard.
     */
    public void registerEditText(int resid) {
        // Find the EditText 'resid'
        EditText edittext = (EditText) mHostActivity.findViewById(resid);
//        edittext.setCursorVisible(true);
        edittext.setOnClickListener(new OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override
            public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
    }
}
