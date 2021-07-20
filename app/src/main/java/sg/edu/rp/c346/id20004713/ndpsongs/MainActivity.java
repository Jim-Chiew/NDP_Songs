package sg.edu.rp.c346.id20004713.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnShowList;
    EditText etSongTitle, etSinger, etYear;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnShowList = findViewById(R.id.btnShow);
        etSinger = findViewById(R.id.etSinger);
        etSongTitle = findViewById(R.id.etSongTitle);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.rg);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                String title = etSongTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                int star;

                int radioID = rg.getCheckedRadioButtonId();
                if(radioID == R.id.rb2){
                    star = 2;
                } else if(radioID == R.id.rb3){
                    star = 3;
                } else if(radioID == R.id.rb4){
                    star = 4;
                }else if(radioID == R.id.rb5){
                    star = 5;
                } else{
                    star = 1;
                }

                long inserted_id = db.insertSong(new Song(title, singer, year, star));

                if (inserted_id != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Insert FAILED",
                            Toast.LENGTH_SHORT).show();
                }

                etSinger.setText("");
                etSongTitle.setText("");
                etYear.setText("");
                rg.clearCheck();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowSongs.class);
                startActivity(intent);
            }
        });
    }
}