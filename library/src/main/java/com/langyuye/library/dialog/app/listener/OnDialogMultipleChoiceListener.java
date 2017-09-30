package com.langyuye.library.dialog.app.listener;

import android.util.SparseBooleanArray;

import com.langyuye.library.dialog.app.MaterialDialog;

/**
 * Created by langyuye on 17-9-17.
 */

public interface OnDialogMultipleChoiceListener {
    boolean onMultipleChoice(MaterialDialog dialog,int position,SparseBooleanArray isCheckArray);
}
