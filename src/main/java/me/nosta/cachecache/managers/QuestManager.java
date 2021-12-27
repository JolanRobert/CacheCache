package me.nosta.cachecache.managers;

public class QuestManager {

    private static QuestManager instance;

    public QuestManager getInstance() {
        if (instance == null) instance = new QuestManager();
        return instance;
    }
}
