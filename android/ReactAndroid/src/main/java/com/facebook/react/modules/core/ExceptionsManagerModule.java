/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.facebook.react.modules.core;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.JavascriptException;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.util.JSStackTrace;

@ReactModule(name = ExceptionsManagerModule.NAME)
public class ExceptionsManagerModule extends BaseJavaModule {

    public static final String NAME = "ExceptionsManager";

    public final DevSupportManager mDevSupportManager;

    public ExceptionsManagerModule(DevSupportManager devSupportManager) {
        mDevSupportManager = devSupportManager;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void reportFatalException(String title, ReadableArray details, int exceptionId) {
        if (mDevSupportManager.getDevSupportEnabled()) {
            {
                showOrThrowError(title, details, exceptionId);
            }
        } else {
            {
                try {
                    Class.forName("host.exp.exponent.ReactNativeStaticHelpers").getMethod("handleReactNativeError", Throwable.class, String.class, Object.class, Integer.class, Boolean.class).invoke(null, null, title, details, exceptionId, true);
                } catch (Exception expoHandleErrorException) {
                    expoHandleErrorException.printStackTrace();
                }
            }
        }
    }

    @ReactMethod
    public void reportSoftException(String title, ReadableArray details, int exceptionId) {
        if (mDevSupportManager.getDevSupportEnabled()) {
            {
                if (mDevSupportManager.getDevSupportEnabled()) {
                    mDevSupportManager.showNewJSError(title, details, exceptionId);
                } else {
                    FLog.e(ReactConstants.TAG, JSStackTrace.format(title, details));
                }
            }
        } else {
            {
                try {
                    Class.forName("host.exp.exponent.ReactNativeStaticHelpers").getMethod("handleReactNativeError", Throwable.class, String.class, Object.class, Integer.class, Boolean.class).invoke(null, null, title, details, exceptionId, false);
                } catch (Exception expoHandleErrorException) {
                    expoHandleErrorException.printStackTrace();
                }
            }
        }
    }

    private void showOrThrowError(String title, ReadableArray details, int exceptionId) {
        if (mDevSupportManager.getDevSupportEnabled()) {
            mDevSupportManager.showNewJSError(title, details, exceptionId);
        } else {
            throw new JavascriptException(JSStackTrace.format(title, details));
        }
    }

    @ReactMethod
    public void updateExceptionMessage(String title, ReadableArray details, int exceptionId) {
        if (mDevSupportManager.getDevSupportEnabled()) {
            {
                if (mDevSupportManager.getDevSupportEnabled()) {
                    mDevSupportManager.updateJSError(title, details, exceptionId);
                }
            }
        } else {
            {
                try {
                    Class.forName("host.exp.exponent.ReactNativeStaticHelpers").getMethod("handleReactNativeError", Throwable.class, String.class, Object.class, Integer.class, Boolean.class).invoke(null, null, title, details, exceptionId, false);
                } catch (Exception expoHandleErrorException) {
                    expoHandleErrorException.printStackTrace();
                }
            }
        }
    }

    @ReactMethod
    public void dismissRedbox() {
        if (mDevSupportManager.getDevSupportEnabled()) {
            mDevSupportManager.hideRedboxDialog();
        }
    }
}
