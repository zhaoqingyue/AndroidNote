package com.study.commonlib.ui.fragmentation.helper.internal;

import android.view.View;

import java.util.ArrayList;

/**
 * @Hide
 * Created by YoKey on 16/11/25.
 */
public final class TransactionRecord {

    public String tag;
    public Integer requestCode;
    public Integer launchMode;
    public Boolean withPop;
    public ArrayList<SharedElement> sharedElementList;

    public static class SharedElement {
        public View sharedElement;
        public String sharedName;

        public SharedElement(View sharedElement, String sharedName) {
            this.sharedElement = sharedElement;
            this.sharedName = sharedName;
        }
    }
}
