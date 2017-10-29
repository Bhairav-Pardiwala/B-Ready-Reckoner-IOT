package com.psyzone.bhairavpardiwala.hellonotes;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.psyzone.bhairavpardiwala.common.Note;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String GPIOR_NAME = "BCM17";
    private static final String GPIOG_NAME = "BCM5";
    ListView ls;

    ListAdaptor adp;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile" };
    ArrayList<Note> list ;
    EditText tv;
    ListAdaptor adapter;
    NoteManager manager;
    Button add,rem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=(Button)findViewById(R.id.add);
        rem=(Button)findViewById(R.id.remove);
        list= new ArrayList<Note>();
        add.setOnClickListener(this);
        rem.setOnClickListener(this);

        ls=(ListView) findViewById(R.id.list);
        tv=(EditText) findViewById(R.id.txtenter);

        manager=new NoteManager();
        manager.context=this;
        manager.notes=list;

      //  manager.onoroff=this;

//       adapter = new ArrayAdapter<Note>(this,
//                android.R.layout.simple_list_item_1, list);

        adapter = new ListAdaptor(this,list);
        ls.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        ls.setAdapter(adapter);
        manager.noteAdaptor=adapter;



        MyBroadcastReceiver reciever=new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.psyzone.bhairavpardiwala.hellonotes.MyBroadcastReceiver.MY_NOTIFICATION");
        // intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
          this.registerReceiver(reciever, filter);
       // Log.d(TAG, "Available GPIO: " + Pmanager.getGpioList());

//registerReceiver(reciever,)

    }

    @Override
    public void onClick(View view) {
        if(view==add)
        {
            manager.AddNote(tv.getText().toString());
            // adapter.notifyDataSetChanged();
        }


    }


}
