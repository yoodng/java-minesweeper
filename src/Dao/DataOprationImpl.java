package Dao;

import jav.GameData;
import util.SqliteJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataOprationImpl implements DataOperation {

    private Connection c = null;
    private PreparedStatement stmt = null;

    @Override
    public int insertData(GameData gameData) {
        int i = 0;
        c = (Connection) SqliteJDBC.getSQLITECON();
        try {
            String sql = "insert into t_GameData values(?,?,?);";
            stmt = (PreparedStatement) c.prepareStatement(sql);
            stmt.setInt(1, gameData.getLevel());
            stmt.setInt(2, gameData.getTime());
            stmt.setString(3, gameData.getDate());
            i = stmt.executeUpdate();
            if (i == 1) {
                System.out.println("gagging");
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }

    @Override
    public List<GameData> getGameData() {
        ArrayList<GameData> gameDataList = new ArrayList<>();
        c = (Connection) SqliteJDBC.getSQLITECON();

        try {
            String sql = "select * from t_GameData order by level desc ,Time asc;";
            stmt = (PreparedStatement) c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GameData gameData = new GameData();
                gameData.setLevel(rs.getInt(1));
                gameData.setTime(rs.getInt(2));
                gameData.setDate(rs.getString(3));
                gameDataList.add(gameData);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameDataList;
    }


}
