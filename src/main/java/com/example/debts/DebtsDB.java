package com.example.debts;

import java.sql.*;
import java.util.Arrays;

public class DebtsDB
{
    public static Connection conn;

    public static Connection conn() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:/Долги", "romankolin", "IT2017year");

        return conn;
    }

    public static String[][] SELECT(int tabl) throws Exception
    {
        int count = 0;

        conn();
        Statement stat = conn.createStatement();
        ResultSet resset;
        String[][] datarr = new String[0][0];
        switch (tabl)
        {
            case 1:
                resset = stat.executeQuery("SELECT COUNT(фам) FROM Преподаватель;");
                while (resset.next())
                    count = resset.getInt("COUNT(фам)");
                datarr = new String[count][7];

                resset = stat.executeQuery("SELECT фам, им, отч, адр, почт, тел, кат FROM Преподаватель ORDER BY фам;");
                count = 0;
                while (resset.next())
                {
                    String фам = resset.getString(1);
                    String им = resset.getString(2);
                    String отч = resset.getString(3);
                    String адр = resset.getString(4);
                    String почт = resset.getString(5);
                    String тел = resset.getString(6);
                    String кат = resset.getString(7);
                    datarr[count][0] = фам;
                    datarr[count][1] = им;
                    datarr[count][2] = отч;
                    datarr[count][3] = адр;
                    datarr[count][4] = почт;
                    datarr[count][5] = тел;
                    datarr[count][6] = кат;

                    count += 1;
                }
                break;
            case 2:
                resset = stat.executeQuery("SELECT COUNT(ном) FROM Группа;");
                while (resset.next())
                    count = resset.getInt("COUNT(ном)");
                datarr = new String[count][4];

                resset = stat.executeQuery("SELECT ном, спец, кол_студ, Преподаватель.фам FROM Группа JOIN Преподаватель ON Группа.курат=Преподаватель.пассп ORDER BY ном;");
                count = 0;
                while (resset.next())
                {
                    int ном = resset.getInt(1);
                    String спец = resset.getString(2);
                    int колстуд = resset.getInt(3);
                    String курат = resset.getString(4);
                    datarr[count][0] = String.valueOf(ном);
                    datarr[count][1] = спец;
                    datarr[count][2] = String.valueOf(колстуд);
                    datarr[count][3] = курат;

                    count += 1;
                }
                break;
            case 3:
                resset = stat.executeQuery("SELECT COUNT(групп_ном) FROM Студент;");
                while (resset.next())
                    count = resset.getInt("COUNT(групп_ном)");
                datarr = new String[count][8];

                resset = stat.executeQuery("SELECT фам, им, отч, дат_рожд, адр, почт, тел, групп FROM Студент ORDER BY фам;");
                count = 0;
                while (resset.next())
                {
                    String фам = resset.getString(1);
                    String им = resset.getString(2);
                    String отч = resset.getString(3);
                    String датрожд = resset.getString(4);
                    String адр = resset.getString(5);
                    String почт = resset.getString(6);
                    String тел = resset.getString(7);
                    int групп = resset.getInt(8);
                    datarr[count][0] = фам;
                    datarr[count][1] = им;
                    datarr[count][2] = отч;
                    datarr[count][3] = датрожд;
                    datarr[count][4] = адр;
                    datarr[count][5] = почт;
                    datarr[count][6] = тел;
                    datarr[count][7] = String.valueOf(групп);

                    count += 1;
                }
                break;
            case 4:
                resset = stat.executeQuery("SELECT COUNT(назв) FROM Предмет;");
                while (resset.next())
                    count = resset.getInt("COUNT(назв)");
                datarr = new String[count][3];

                resset = stat.executeQuery("SELECT назв, кол_задан, Преподаватель.фам FROM Предмет JOIN `Предмет преподавателя` ON Предмет.назв=`Предмет преподавателя`.предм JOIN Преподаватель ON `Предмет преподавателя`.препод=Преподаватель.пассп ORDER BY назв;");
                count = 0;
                while (resset.next())
                {
                    String назв = resset.getString(1);
                    int колзадан = resset.getInt(2);
                    String препод = resset.getString(3);
                    datarr[count][0] = назв;
                    datarr[count][1] = String.valueOf(колзадан);
                    datarr[count][2] = препод;

                    count += 1;
                }
                break;
            case 5:
                resset = stat.executeQuery("SELECT COUNT(ном_задан) FROM `Задание по предмету` LEFT JOIN `Долг студента` ON (`Задание по предмету`.предм_задан=`Долг студента`.предм AND `Задание по предмету`.ном_задан=`Долг студента`.задан) LEFT JOIN Студент ON Студент.групп_ном=`Долг студента`.групп_ном;");
                while (resset.next())
                    count = resset.getInt("COUNT(ном_задан)");
                datarr = new String[count][4];

                resset = stat.executeQuery("SELECT предм_задан, ном_задан, Студент.фам, Студент.групп FROM `Задание по предмету` LEFT JOIN `Долг студента` ON (`Задание по предмету`.предм_задан=`Долг студента`.предм AND `Задание по предмету`.ном_задан=`Долг студента`.задан) LEFT JOIN Студент ON Студент.групп_ном=`Долг студента`.групп_ном ORDER BY предм_задан, ном_задан, фам;");
                count = 0;
                while (resset.next())
                {
                    String предм = resset.getString(1);
                    int задан = resset.getInt(2);
                    String студ = resset.getString(3);
                    int групп = resset.getInt(4);
                    datarr[count][0] = предм;
                    datarr[count][1] = String.valueOf(задан);
                    datarr[count][2] = студ;
                    if (групп == 0)
                        datarr[count][3] = null;
                    else
                        datarr[count][3] = String.valueOf(групп);

                    count += 1;
                }
                break;
        }
        conn.close();

        return datarr;
    }

    public static void PreparedStatement(PreparedStatement pstat, String[] datarr, int itim) throws Exception
    {
        for (int i = 1; i <= itim; i++)
            pstat.setString(i, Arrays.asList(datarr).get(i - 1));
    }

    public static int row;

    public static String INSERT(String[] datarr) throws Exception
    {
        conn();
        PreparedStatement pstat;
        pstat = conn.prepareStatement("INSERT INTO `Долг студента` VALUES((SELECT групп_ном FROM Студент WHERE фам=? AND групп=?), ?, ?);");
        PreparedStatement(pstat, datarr, 4);
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Данные сохранены";
        else
            return "Данные не сохранены";
    }

    public static String DELETE(String[] datarr) throws Exception
    {
        conn();
        PreparedStatement pstat;
        pstat = conn.prepareStatement("DELETE FROM `Долг студента` WHERE групп_ном=(SELECT групп_ном FROM Студент WHERE фам=? AND групп=?) AND задан=? AND предм=?;");
        PreparedStatement(pstat, datarr, 4);
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Данные удалены";
        else
            return "Данные не удалены";
    }
}