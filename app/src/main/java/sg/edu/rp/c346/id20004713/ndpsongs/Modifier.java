package sg.edu.rp.c346.id20004713.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Modifier extends AppCompatActivity {
    Button btnUpdate, btnDelete, btnCancel;
    EditText etID, etTitle, etSinger, etYear;
    RadioGroup rg;

    DBHelper db = new DBHelper(Modifier.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.rg);

        Intent i = getIntent();
        Song data = (Song) i.getSerializableExtra("data");

        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(("" + data.getYear()));
        etID.setText(("" + data.get_id()));

        if(data.getStars() == 5){
            rg.check(R.id.rb5);
        } else if(data.getStars() == 2){
            rg.check(R.id.rb2);
        }else if(data.getStars() == 3){
            rg.check(R.id.rb3);
        }else if(data.getStars() == 4){
            rg.check(R.id.rb4);
        }else{
            rg.check(R.id.rb1);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteNote(data.get_id());
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
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

                Song updated = new Song(data.get_id(), title, singer, year, star);

                int inserted_id = db.updateSong(updated);

                if (inserted_id != -1){
                    Toast.makeText(Modifier.this, "Update successful",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Modifier.this, "Update FAILED",
                            Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}