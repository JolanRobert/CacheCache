package me.nosta.cachecache.managers;

public class QuestManager {

    private static QuestManager instance;

    public static QuestManager getInstance() {
        if (instance == null) instance = new QuestManager();
        return instance;
    }
}
