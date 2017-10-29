package com.psyzone.bhairavpardiwala.hellonotes;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.psyzone.bhairavpardiwala.common.Note;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity implements View.OnClickListener,DisplayOnOrOff {

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

    private Gpio redGIO,greenGPIO;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef ;

    PeripheralManagerService Pmanager = new PeripheralManagerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=(Button)findViewById(R.id.add);
        //rem=(Button)findViewById(R.id.remove);
        list= new ArrayList<Note>();
        add.setOnClickListener(this);
       // rem.setOnClickListener(this);

        ls=(ListView) findViewById(R.id.list);
        tv=(EditText) findViewById(R.id.txtenter);

        manager=new NoteManager();
        manager.context=this;
        manager.notes=list;

manager.onoroff=this;

        myRef =  database. getReference("Notes");

//       adapter = new ArrayAdapter<Note>(this,
//                android.R.layout.simple_list_item_1, list);

        adapter = new ListAdaptor(this,list);
        ls.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        ls.setAdapter(adapter);
        manager.noteAdaptor=adapter;



        MyBroadcastReceiver reciever=new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.bhairavpardiwala.helloworldiot.MyBroadcastReceiver.MY_NOTIFICATION");
        // intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
      //  this.registerReceiver(reciever, filter);
        Log.d(TAG, "Available GPIO: " + Pmanager.getGpioList());
        try {
            redGIO = Pmanager.openGpio(GPIOR_NAME);
greenGPIO=Pmanager.openGpio(GPIOG_NAME);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try
                    {
                        GenericTypeIndicator<List<Note>> t = new GenericTypeIndicator<List<Note>>() {};

                        if (dataSnapshot.getValue() != null)
                        {
                            List<Note> Notes = dataSnapshot.getValue(t);

                            manager.notes.clear();
                            manager.notes.addAll(Notes);
                            manager.Display(true);
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            manager.notes.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    try
                    {
                        GenericTypeIndicator<List<Note>> t = new GenericTypeIndicator<List<Note>>() {};

                        if (dataSnapshot.getValue() != null)
                        {
                            List<Note> Notes = dataSnapshot.getValue(t);
                            manager.notes.clear();
                            manager.notes.addAll(Notes);
                            manager.Display(true);
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            manager.notes.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });


        //greenGPIO.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
//
//
//
//
//
 //      greenGPIO.close();
//            redGIO.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        } catch (IOException e) {
            e.printStackTrace();
        }
//registerReceiver(reciever,)

    }

    @Override
    public void onClick(View view) {
        if(view==add)
        {
            manager.AddNote(tv.getText().toString());
            tv.setText("");
            // adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void SetLedOROff(boolean tr,boolean cir) {
        try {
            if(!cir)
            {
                List<Note> llist=manager.notes;
                myRef.setValue(llist);
            }

            if(!tr)
            {
                try
                {
                    greenGPIO.close();
                }
                catch(Exception e)
                {

                }

                try {
                    redGIO = Pmanager.openGpio(GPIOR_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                redGIO.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);



            }
            else
            {
                try
                {
                    redGIO.close();
                }
                catch(Exception e)
                {

                }
                try {
                    greenGPIO = Pmanager.openGpio(GPIOG_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                greenGPIO.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);


            }

        } catch (Exception e) {
            Log.w(TAG, "Unable to access GPIO", e);
        }
    }
}
