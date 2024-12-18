package org.example;

public class Main {
    public static void main(String[] args) {
        DbManager dbManager = new DbManager();
        dbManager.CreateDb();
        dbManager.UpdateDb();
        dbManager.ShowDbData();
    }
}