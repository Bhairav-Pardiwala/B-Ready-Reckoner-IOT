package com.psyzone.bhairavpardiwala.hellonotes;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import com.psyzone.bhairavpardiwala.common.Note;

import java.util.ArrayList;

/**
 * Created by Bhairav Pardiwala on 28-10-2017.
 */


public  class NoteManager {


    static ArrayList<Note> notes;
    static ListAdaptor  noteAdaptor;
    static Context context;

    //static DisplayOnOrOff onoroff;

    public static Note AddNote(String note)
    {
        Note noteTmp=new Note();
        noteTmp.note=note;

        notes.add(noteTmp);
        SortIds();
        noteAdaptor.notifyDataSetChanged();
        Display();
        return noteTmp;

    }
    public static Note DeleteNote(long pos)
    {
        Note tmp=notes.get((int)pos);
        notes.remove(tmp);
        SortIds();
        noteAdaptor.notifyDataSetChanged();
        Display();
        return  tmp;
    }

    public static void SortIds()
    {
        for(int i=0;i<notes.size();i++)
        {
            notes.get(i).id=i;
        }


    }

    public static void setDone(long pos,boolean dn)
    {
        Note tmp=notes.get((int)pos);
        tmp.done=dn;

//        Intent intent = new Intent();
//        intent.setAction("com.example.bhairavpardiwala.helloworldiot.com.psyzone.bhairavpardiwala.hellonotes.MyBroadcastReceiver.MY_NOTIFICATION");
//        intent.putExtra("allOk",isAllDone());
//
//        context.sendBroadcast(intent);

        Display();

    }

    private static void Display() {

        Intent intent = new Intent();
        intent.setAction(".com.psyzone.bhairavpardiwala.hellonotes.MyBroadcastReceiver.MY_NOTIFICATION");
        intent.putExtra("allOk",isAllDone());

        context.sendBroadcast(intent);
    }

    public static boolean isAllDone()
    {
        boolean isDone=true;
        for(int i=0;i<notes.size();i++)
        {
            if(!notes.get(i).done)
            {
                isDone=notes.get(i).done;
            }


        }
        return  isDone;
    }



}