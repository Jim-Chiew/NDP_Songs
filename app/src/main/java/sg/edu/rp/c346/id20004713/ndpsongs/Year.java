package sg.edu.rp.c346.id20004713.ndpsongs;

import java.io.Serializable;

public class Year implements Serializable {
    private String year;

    public Year(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    @Override
    public String toString() {
        return ("" + year);
    }
}
