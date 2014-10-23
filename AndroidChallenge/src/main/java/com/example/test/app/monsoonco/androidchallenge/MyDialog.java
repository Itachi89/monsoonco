package com.example.test.app.monsoonco.androidchallenge;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by varunsundaramoorthy on 10/21/14.
 */
public class MyDialog extends DialogFragment {

    public MyDialog(){

    }

    public interface OnCompleteListener {
         void onComplete(String task, int bgColor,int txtColor) throws IOException;
    }

    public OnCompleteListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container);
        final EditText inputText = (EditText) view.findViewById(R.id.inputText);
        Button submit = (Button) view.findViewById(R.id.submit);
        ImageView red = (ImageView) view.findViewById(R.id.red);
        ImageView blue = (ImageView) view.findViewById(R.id.blue);
        ImageView orange = (ImageView) view.findViewById(R.id.orange);
        ImageView green = (ImageView) view.findViewById(R.id.green);
        ImageView purple = (ImageView) view.findViewById(R.id.violet);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setTextColor(0Xffc20000);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setTextColor(0Xff1a547f);
            }
        });

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setTextColor(0Xffd1652e);
            }
        });

        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setTextColor(0Xff511692);
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setTextColor(0Xff007900);
            }
        });

        mListener =(OnCompleteListener) getActivity();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputText.getText().toString().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"please enter text",Toast.LENGTH_SHORT).show();
                    return;
                }
                String task =inputText.getText().toString();
                int txtColor = inputText.getCurrentTextColor();
                int bgColor=getBgColor(txtColor);
                try {
                    mListener.onComplete(task,bgColor,txtColor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getDialog().dismiss();

            }
        });
        return view;
    }

    public void onResume()
    {

        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = 300;
        params.gravity= Gravity.BOTTOM;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public int getBgColor(int color){
        int bgColor=0;


        switch (color){
            case 0Xffc20000:
                bgColor =0xffff524b;
                break;
            case 0Xff1a547f:
                bgColor =0xff1ea4e4;
                break;
            case 0Xffd1652e:
                bgColor =0Xffffb738;
                break;
            case 0Xff511692:
                bgColor =0Xffbc4eff;
                break;
            case 0Xff007900:
                bgColor =0xff00ee00;
                break;
            default:
                bgColor =0x80808080;
                break;
        }


        return bgColor;
    }
}
