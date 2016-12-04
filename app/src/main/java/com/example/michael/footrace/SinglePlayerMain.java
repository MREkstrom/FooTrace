package com.example.michael.footrace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SinglePlayerMain extends AppCompatActivity {

    private ArrayList<String> traceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_main);

        Typeface tf = Typeface.createFromAsset(getAssets(), "mvboli.ttf");
        Button[] buttons = {(Button) findViewById(R.id.spDrawButton)};
        for (Button b : buttons) {
            b.setTypeface(tf);
        }

       /*Displays all created traces in a list which can be selected by user*/
        ListView lv = (ListView) findViewById(R.id.spDesignList);
        displayTraces(MainActivity.traces);
        lv.setAdapter(new ListAdapter(this, R.layout.path_list_item, traceList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*When path is chosen, start the game*/
                playGame(traceList.get(position));
            }
        });
    }

    /*Parses through list of traces and adds them to arraylist for listview*/
    private void displayTraces(HashMap<String, Path> traces) {
        Set<String> paths = traces.keySet();

        for(String name : paths){
            traceList.add(name);
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

    /*Allows user to create design from the single player screen*/
    public void newDesign(View v){
        Intent designIntent = new Intent(SinglePlayerMain.this, NewDesign.class);
        startActivityForResult(designIntent, MainActivity.REQUEST_NEW_DESIGN);
    }

    /*Allows user to start playing game upon path selection*/
    public void playGame(String chosenPath){
        /*Store path name and the mode (Single/multiplayer)*/
        Intent playIntent = new Intent(SinglePlayerMain.this, PlayGame.class);
        playIntent.putExtra("Mode","SP"); //SP for singleplayer, MP for multiplayer
        playIntent.putExtra("PathName", chosenPath);
        startActivity(playIntent);
        startActivityForResult(playIntent, MainActivity.REQUEST_PLAY_GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MainActivity.REQUEST_NEW_DESIGN){
            /*Refresh activity to load in new traces*/
            finish();
            startActivity(getIntent());
        } else if (requestCode == MainActivity.REQUEST_PLAY_GAME && resultCode == RESULT_OK && null != data) {
            // TODO- handle return of play results
        }
    }
}