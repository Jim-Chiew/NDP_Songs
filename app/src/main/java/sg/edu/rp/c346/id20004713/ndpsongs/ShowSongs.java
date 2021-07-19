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

public class ShowSongs extends AppCompatActivity {
    Button btn5star;
    ListView lv;
    ArrayList<Song> al = new ArrayList<>();
    ArrayList<Song> al5Star = new ArrayList<>();
    ArrayAdapter adapter;
    boolean show5Star = false;
    Spinner spnYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        btn5star = findViewById(R.id.btn5Star);
        lv = findViewById(R.id.lv);

        spnYear = findViewById(R.id.spnYear);
        registerForContextMenu(spnYear);

        DBHelper dbh = new DBHelper(ShowSongs.this);
        al.addAll(dbh.getAllSongs());

        adapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al);
        ArrayAdapter adapter5star = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al5Star);
        lv.setAdapter(adapter);

        btn5star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show5Star){
                    show5Star = false;
                    lv.setAdapter(adapter);
                    btn5star.setText(R.string.show_all_songs_with_5_stars);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0,0,0,"Show All");
        menu.add(1, 1, 1, "Show NONE");

        //will continue tomorrow. most likely. maybe.
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(ShowSongs.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}