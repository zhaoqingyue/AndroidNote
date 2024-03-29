package com.study.commonlib.ui.fragmentation;

import android.support.annotation.IntDef;

import com.study.commonlib.ui.fragmentation.helper.ExceptionHandler;
import com.study.commonlib.ui.fragmentation.helper.internal.InstanceException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by YoKey on 17/2/5.
 */
public class Fragmentation {
    /**
     * Dont display stack view.
     */
    public static final int NONE = 0;
    /**
     * Shake it to display stack view.
     */
    public static final int SHAKE = 1;
    /**
     * As a bubble display stack view.
     */
    public static final int BUBBLE = 2;

    static volatile Fragmentation INSTANCE;

    private boolean debug;
    private int mode;
    private ExceptionHandler handler;

    @IntDef({NONE, SHAKE, BUBBLE})
    @Retention(RetentionPolicy.SOURCE)
    @interface StackViewMode {
    }

    static Fragmentation getDefault() {
        if (INSTANCE == null) {
            synchronized (Fragmentation.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Fragmentation(new FragmentationBuilder());
                }
            }
        }
        return INSTANCE;
    }

    Fragmentation(FragmentationBuilder builder) {
        debug = builder.debug;
        mode = builder.mode;
        handler = builder.handler;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public ExceptionHandler getHandler() {
        return handler;
    }

    public void setHandler(ExceptionHandler handler) {
        this.handler = handler;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(@StackViewMode int mode) {
        this.mode = mode;
    }

    public static FragmentationBuilder builder() {
        return new FragmentationBuilder();
    }

    public static class FragmentationBuilder {
        private boolean debug;
        private int mode;
        private ExceptionHandler handler;

        /**
         * @param debug Suppressed Exception("Can not perform this action after onSaveInstanceState!") when debug=false
         */
        public FragmentationBuilder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        /**
         * Sets the mode to display the stack view
         */
        public FragmentationBuilder stackViewMode(@StackViewMode int mode) {
            this.mode = mode;
            return this;
        }

        /**
         * @param handler Handled Exception("Can not perform this action after onSaveInstanceState!") when debug=false.
         */
        public FragmentationBuilder handleException(ExceptionHandler handler) {
            this.handler = handler;
            return this;
        }

        public Fragmentation install() {
            synchronized (Fragmentation.class) {
                if (Fragmentation.INSTANCE != null) {
                    throw new InstanceException("Default instance already exists." +
                            " It may be only set once before it's used the first time to ensure consistent behavior.");
                }
                Fragmentation.INSTANCE = new Fragmentation(this);
                return Fragmentation.INSTANCE;
            }
        }
    }
}
