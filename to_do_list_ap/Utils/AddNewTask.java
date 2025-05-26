package com.example.to_do_list_ap.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_do_list_ap.Adapter.ToDoAdapter;
import com.example.to_do_list_ap.Model.ToDoModel;
import com.example.to_do_list_ap.OnDialogCloseListener;
import com.example.to_do_list_ap.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";
    private EditText mEditeText;
    private Button mSaveButton;
    private DataBaseHelper myDB;

    public static  AddNewTask newInstance(){

        return new AddNewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_task,container,false);
        return v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);

        mEditeText = view.findViewById(R.id.editText);
        mSaveButton = view.findViewById(R.id.addButton);
        myDB = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();

        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            mEditeText.setText(task);

            if(task.length() > 0){

                mSaveButton.setEnabled(false);
            }

        }

        mEditeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){

                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);

                }else{
                    mSaveButton.setEnabled(true);


                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditeText.getText().toString();
                if(finalIsUpdate){

                    myDB.updateTask(bundle.getInt("Id"),text);
                }else
                {

                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);

                    myDB.insertTask(item);

                }

                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();

        if(activity instanceof OnDialogCloseListener){

            ((OnDialogCloseListener)activity).onDialogClose(dialog);


        }
    }
}
