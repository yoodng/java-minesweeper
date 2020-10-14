package Dao;

import jav.GameData;

import java.util.List;

public interface DataOperation {
    int insertData(GameData gameData);

    List<GameData> getGameData();


}
