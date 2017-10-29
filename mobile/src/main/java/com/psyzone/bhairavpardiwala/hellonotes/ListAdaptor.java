package com.psyzone.bhairavpardiwala.hellonotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.psyzone.bhairavpardiwala.common.Note;

import java.util.ArrayList;

/**
 * Created by Bhairav Pardiwala on 28-10-2017.
 */


public class ListAdaptor extends ArrayAdapter<Note>
{
    private final Context context;
    private final ArrayList<Note> values;

    public ListAdaptor(Context context, ArrayList<Note> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.ls, parent, false);
        CheckBox checkView = (CheckBox) rowView.findViewById(R.id.checkBox);
        TextView text=(TextView)rowView.findViewById(R.id.id);
        checkView.setText(values.get( position).note);
        Button rem=(Button) rowView.findViewById(R.id.remove);
        rem.setTag(new Long(position));
        checkView.setTag(new Long(position));
        checkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                NoteManager.setDone((Long)compoundButton.getTag(),b);
            }
        });
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteManager.DeleteNote((Long)view.getTag());

            }
        });

        // change the icon for Windows and iPhone
        checkView.setChecked(values.get(position).done);
        text.setText(String.valueOf(values.get(position).id));

        return rowView;
    }

}
