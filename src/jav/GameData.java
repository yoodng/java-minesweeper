package jav;

import Dao.DataOprationImpl;
import org.omg.CORBA.StringHolder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameData {
    private static String path = System.getProperty("user.dir") + "/data/" + "gameData.txt";
    private int level;
    private int stepNumber;
    private int time;
    private String date;

    public GameData(int level) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(new Date());
        stepNumber = 0;
        this.level = level;
    }

    public GameData() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void addStepNumber() {
        this.stepNumber++;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String time = this.time / 100 + "分" + this.time % 100 + "秒";
        return date +
                " || 难度：【" + level + "】 " + "用时：【" + time + "】\n";
    }


    public void saveGameData() {
        new DataOprationImpl().insertData(this);
    }

    public static List<String> readGameData() {
        List<GameData> strings = new ArrayList<>();
        strings = new DataOprationImpl().getGameData();
        ArrayList<String> strings1 = new ArrayList<>();
        for (GameData string : strings) {
            strings1.add(string.toString());
        }
        return strings1;
    }
//    public void saveGameData() {
//        try {
//            File file = new File(path);
//            FileWriter fileWriter = new FileWriter(file, true);
//            fileWriter.write(this.toString());
//            System.out.println(this.toString() + "保存成功！");
//            fileWriter.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public  static List<String> readGameData() {
//        List<String> strList = new ArrayList<>();
//        try {
//            File file = new File(path);
//            //FileReader fileReader = new FileReader(file);
//            if (file.isFile()) {
//                //判断文件是否存在
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineText = null;
//                while ((lineText = bufferedReader.readLine()) != null) {
//                    strList.add(lineText);
//                }
//                read.close();
//            } else {
//                System.out.println("找不到指定的文件");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//
//        return strList;
//    }
}