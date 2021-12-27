package me.nosta.cachecache.enums;

public enum QuestEnum {
    QUEST1("Quest1", QuestType.SHORT,"Desc Quest1"),
    QUEST2("Quest2", QuestType.SHORT,"Desc Quest2"),
    QUEST3("Quest3", QuestType.SHORT,"Desc Quest3"),
    QUEST4("Quest4", QuestType.SHORT,"Desc Quest4"),
    QUEST5("Quest5", QuestType.SHORT,"Desc Quest5");

    private String name;
    private QuestType questType;
    private String description;


    private QuestEnum(String name, QuestType questType, String description) {
        this.name = name;
        this.questType = questType;
        this.description = description;
    }

    public String getName() {return this.name;}
    public QuestType getQuestType() {return this.questType;}
    public String getDescription() {return this.description;}

    public enum QuestType {
        SHORT,
        COMMON,
        LONG;
    }
}


