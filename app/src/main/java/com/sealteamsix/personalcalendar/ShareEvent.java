package com.sealteamsix.personalcalendar;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShareEvent extends AppCompatActivity {

    ListView listView;
    TextView textView_title;
    String subject, mail_body;
    String participants;
    String[] email_add;
    List<String> selections = new ArrayList();
    ArrayAdapter<String> adapter;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_event);

        listView = (ListView) findViewById(R.id.emailList_view);
        textView_title = (TextView) findViewById(R.id.participants_title);

        // Getting mail details
        Intent myIntent = getIntent();
        participants = myIntent.getStringExtra("participants");
        subject = myIntent.getStringExtra("subject");
        mail_body = myIntent.getStringExtra("mail_body");

        // Parsing String to StringArrayList
        final List<String> participantsList = Arrays.asList(participants.split(","));

        // List of participants
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,participantsList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selections.add(participantsList.get(position));
                    listView.getChildAt(position).setBackgroundColor(Color.parseColor("#984666F8"));
                    count++;
                    mode.setTitle(count + " Participants Selected");
                }
                else {
                    selections.remove(participantsList.get(position));
                    listView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    count--;
                    mode.setTitle(count + " Participants Selected");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.share_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.id_email) {
                    email_add = selections.toArray(new String[0]);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, email_add);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, mail_body);
                    startActivity(Intent.createChooser(intent, ""));

                    adapter.notifyDataSetChanged();
                    mode.finish();
                    
                    setResult(RESULT_OK, null);
                    finish();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                count = 0;
                selections.clear();
            }
        });
    }
}
