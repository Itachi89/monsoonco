package com.example.test.app.monsoonco.androidchallenge;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements MyDialog.OnCompleteListener{
    public List<Task> myList = new ArrayList<Task>();
    public static String fileName = "mylist.ser";

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView edit = (ImageView) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MyDialog testDialog = new MyDialog();
                testDialog.setRetainInstance(true);
                testDialog.show(fm, "my_dialog");
            }
        });
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void addTask(){
        Log.d(this.getClass().toString(), ">>>>>> afterAdd: ");

        myList.add(new Task("A demo task. You can delete this and add new tasks.", 0xff00ee00, 0xff007900));


        populateListView(myList);
    }

    public void populateListView(List<Task> myList){
        List<Task> list = myList;
        adapter = new MyAdapter(this, list);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public static class MyAdapter extends ArrayAdapter<Task> {
        Context context;
        List<Task> myList;
        public MyAdapter(Context context, List<Task> myList) {
            super(context,R.layout.list_item,myList);
            Log.d(this.getClass().toString(), ">>>>>> Inside MyAdapter: ");
            this.context = context;
            this.myList = myList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = convertView;


            MyViewHolder holder = null;
            if (row == null) {

                Log.d(this.getClass().toString(), ">>>>>>>>>>>> Inside Get View");
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(R.layout.list_item, parent, false);
                holder = new MyViewHolder();
                holder.tasks=(TextView) row.findViewById(R.id.tasks);
                holder.remove=(ImageView) row.findViewById(R.id.ic_remove);
                row.setTag(holder);
            }else {
                holder = (MyViewHolder) row.getTag();
            }


            final Task currentTask = myList.get(position);
            row.setBackgroundColor(currentTask.getBgColor());
            holder.tasks.setText(currentTask.getDesc());
            holder.tasks.setTextColor(currentTask.getTxtColor());
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myList.remove(currentTask);
                    notifyDataSetChanged();

                }
            });
            return row;

        }

        static class MyViewHolder{
            TextView tasks;
            ImageView remove;
        }
    }

    public void onComplete(String task,int bgColor,int txtColor ) throws IOException {
       // Toast.makeText(this, "Hi, ", Toast.LENGTH_SHORT).show();
        Log.i(".....OnComptask",task);
        if(myList.isEmpty()){
        myList.add(new Task(task,bgColor,txtColor));
        populateListView(myList);
        }else{
        myList.add(new Task(task,bgColor,txtColor));
        adapter.notifyDataSetChanged();}
    }

    public void saveData(Context context,List<Task> myList) throws IOException {
        Log.i(".....save","data");
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(myList);
        oos.close();
        fos.close();
    }

    public void loadData() throws IOException,ClassNotFoundException {
            Log.i(".....load","data");
            FileInputStream fis = null;
            try {
                fis = openFileInput(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Task> newList = null;
                newList = (ArrayList<Task>) ois.readObject();

                ois.close();
                fis.close();
                if(newList !=null){ populateListView(newList);}
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                addTask();
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Log.i(".....onDestroy","data");
            saveData(getApplicationContext(),myList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
