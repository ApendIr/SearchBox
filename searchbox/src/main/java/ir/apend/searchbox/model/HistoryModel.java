package ir.apend.searchbox.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static ir.apend.searchbox.model.HistoryModel.TABLE_NAME;

/**
 * Created by Fatemeh on 2/25/2018.
 */
@DatabaseTable(tableName = TABLE_NAME)
public class HistoryModel {

    public static final String TABLE_NAME="history";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String textHistory;

    public void setTextHistory(String textHistory) {
        this.textHistory = textHistory;
    }

    public String getTextHistory() {
        return textHistory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
