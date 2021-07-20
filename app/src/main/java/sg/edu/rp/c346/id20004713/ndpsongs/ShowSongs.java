package sg.edu.rp.c346.id20004713.ndpsongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowSongs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn5star;
    ListView lv;
    ArrayList<Song> al = new ArrayList<>();
    ArrayList<Song> al5Star = new ArrayList<>();
    ArrayList<Year> alYear = new ArrayList<>();
    ArrayList<Song> alFilterByYear = new ArrayList<>();
    ArrayAdapter adapter, adapter5star, adapterYear, adapterFilterByYear;
    boolean show5Star = false;
    Spinner spnYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        btn5star = findViewById(R.id.btn5Star);
        lv = findViewById(R.id.lv);
        spnYear = findViewById(R.id.spnYear);

        DBHelper dbh = new DBHelper(ShowSongs.this);

        al.addAll(dbh.getAllSongs());
        alYear.addAll(dbh.getDistinctYear());

        adapter5star = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al5Star);
        adapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al);
        adapterYear = new ArrayAdapter<Year>(this, android.R.layout.simple_spinner_dropdown_item, alYear);
        adapterFilterByYear = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alFilterByYear);

        spnYear.setAdapter(adapterYear);
        spnYear.setOnItemSelectedListener(this);

        btn5star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show5Star){
                    show5Star = false;
                    lv.setAdapter(adapter);
                    btn5star.setText(R.string.show_all_songs_with_5_stars);
                    spnYear.setSelection(0);

                } else {
                    show5Star = true;
                    al5Star.clear();

                    for (Song data : al){
                        if(data.getStars() == 5){
                            al5Star.add(data);
                        }
                    }

                    lv.setAdapter(adapter5star);
                    btn5star.setText(R.string.show_all_songs);
                    spnYear.setSelection(0);
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(show5Star){
                    Toast.makeText(ShowSongs.this, "Switch to show all to Edit item",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Song data = al.get(position);
                    Intent i = new Intent(ShowSongs.this,
                            Modifier.class);
                    i.putExtra("data", data);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(ShowSongs.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        lv.setAdapter(adapter);

        alYear.clear();
        alYear.addAll(dbh.getDistinctYear());
        spnYear.setSelection(0);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object data = parent.getItemAtPosition(position);
        String year = data.toString();

        if (year.length() != 0){
            DBHelper dbh = new DBHelper(ShowSongs.this);
            alFilterByYear.clear();
            alFilterByYear.addAll(dbh.getAllSongsByYear(year));
            lv.setAdapter(adapterFilterByYear);

            show5Star = true;
            btn5star.setText(R.string.show_all_songs);
        } else {
            show5Star = false;
            lv.setAdapter(adapter);
            btn5star.setText(R.string.show_all_songs_with_5_stars);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}