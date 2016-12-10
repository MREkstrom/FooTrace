package com.example.michael.footrace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.example.michael.footrace.MainActivity.button_sound;

public class MultiPlayerHost extends AppCompatActivity {

    private ArrayList<String> traceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_host);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.mpDrawButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }
        TextView[] views = {(TextView) findViewById(R.id.mpTitle)};
        for (TextView tv : views) {
            tv.setTypeface(tf);
            tv.setTextSize(30f);
        }

        /*Displays all created traces in a list which can be selected by user*/
        ListView lv = (ListView) findViewById(R.id.mpDesignList);
        displayTraces(MainActivity.userTraces);
        lv.setAdapter(new ListAdapter(this, R.layout.path_list_item, traceList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*When path is chosen, start the game*/
                button_sound.start();
                broadcastGame(traceList.get(position));
            }
        });
    }

    public void newDesign(View v){
        button_sound.start();
        Intent designIntent = new Intent(MultiPlayerHost.this, NewDesign.class);
        startActivityForResult(designIntent, MainActivity.REQUEST_NEW_DESIGN);
    }

    /*Parses through list of traces and adds them to arraylist for listview*/
    private void displayTraces(HashMap<String, UserTrace> traces) {
        Set<String> paths = traces.keySet();

        for(String name : paths){
            traceList.add(name);
            //traces.get(name).getPath();
        }
    }

    /*Helps implement the list view class*/
    private class ListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> traces;

        private ListAdapter(Context context, int the_layout, List<String> designs) {
            super(context, the_layout, designs);
            traces = designs;
            layout = the_layout;
        }

        /*sets up list of traces*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Row rowHandler = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                Row row = new Row();
                row.pathImage = (ImageView) convertView.findViewById(R.id.pathImage);
                row.pathName = (TextView) convertView.findViewById(R.id.pathName);
                Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
                row.pathName.setTypeface(tf);

                //Draw the path to the ImageView
                Path path = MainActivity.userTraces.get(getItem(position)).getPath();
                Paint paint = MainActivity.userTraces.get(getItem(position)).getPaint();

                Bitmap tempBmp = Bitmap.createBitmap(950, 950, Bitmap.Config.RGB_565);
                Canvas tempCan = new Canvas(tempBmp);
                tempCan.drawARGB(255, 191, 228, 179);
                tempCan.drawPath(path, paint);
                row.pathImage.setImageBitmap(tempBmp);


                convertView.setTag(row);
            }
            rowHandler = (Row) convertView.getTag();

            rowHandler.pathName.setText(getItem(position));

            return convertView;
        }
    }
    /*Contains definition for row*/
    public class Row {
        ImageView pathImage;
        TextView pathName;
    }

    public void broadcastGame(String chosenPath){
        /*Store path name and the mode (Single/multiplayer)*/
        Intent playIntent = new Intent(MultiPlayerHost.this, BroadcastGame.class);
        playIntent.putExtra("PathName", chosenPath);
        startActivityForResult(playIntent, MainActivity.REQUEST_BROADCAST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MainActivity.REQUEST_NEW_DESIGN){
            /*Refresh activity to load in new traces*/
            finish();
            startActivity(getIntent());
        } else if (requestCode == MainActivity.REQUEST_BROADCAST && resultCode == RESULT_OK && null != data) {
            // TODO- handle return of play results
        }
    }
}
