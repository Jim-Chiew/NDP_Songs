package sg.edu.rp.c346.id20004713.ndpsongs;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Song implements Serializable {

    private int _id;
    private String title;
    private String singers;
    private int year;
    private int stars;

    public Song(String title, String singers, int year, int stars) {
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.stars = stars;
    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getSingers() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @NonNull
    @Override
    public String toString() {
        String out = title + "\n" + singers + " - " + year + "\n";

        for(int num = 0 ; num < stars ; num++){
            out += "*";
        }
        return out;
    }
}
