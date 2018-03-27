package ir.apend.searchbox.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import ir.apend.searchbox.model.HistoryModel;

/**
 * Created by Fatemeh on 3/27/2018.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "searchbox";
    private static final int DATABASE_VERSION = 1;

    private static DataBaseHelper instance;

    private RuntimeExceptionDao<HistoryModel, Integer> historyDao;


  private DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    /**
     * What to do when your database needs to be created. Usually this entails creating the tables and loading any
     * initial data.
     * <p>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being created.
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,HistoryModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * What to do when your database needs to be updated. This could mean careful migration of old data to new data.
     * Maybe adding or deleting database columns, etc..
     * <p>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    private RuntimeExceptionDao<HistoryModel, Integer> getHistoryDao() {
        if (historyDao == null)
            historyDao = getRuntimeExceptionDao(HistoryModel.class);
        return historyDao;
    }

    public void insertHistory(final String search){
        final HistoryModel historyModel=new HistoryModel();
        if(!checkHistory(search)) {
            historyModel.setTextHistory(search);
            getHistoryDao().callBatchTasks(new Callable<HistoryModel>() {
                @Override
                public HistoryModel call() throws Exception {
                    getHistoryDao().createOrUpdate(historyModel);
                    return null;
                }
            });
        }
    }

    public int getCountOfHistory(){return (int) getHistoryDao().countOf();}

    public void deleteHistory(int id){
        getHistoryDao().deleteById(id);
    }

    public List<HistoryModel> getAllHistory(){
        return getHistoryDao().queryForAll();
    }
    public List<HistoryModel> getAllOrderHistory(){
        try {
            return getHistoryDao().queryBuilder().orderBy("id",false).distinct().query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkHistory(String search){
        try {
            HistoryModel historyModel= getHistoryDao().queryBuilder().where().eq("textHistory",search).queryForFirst();
            if (historyModel!=null)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
