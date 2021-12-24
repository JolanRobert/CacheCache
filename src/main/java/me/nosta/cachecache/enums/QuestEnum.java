package me.nosta.cachecache.enums;

public enum QuestEnum {
    QUEST1("Quest1","Desc Quest1"),
    QUEST2("Quest2","Desc Quest2"),
    QUEST3("Quest3","Desc Quest3"),
    QUEST4("Quest4","Desc Quest4"),
    QUEST5("Quest5","Desc Quest5");

    private String name;
    private String description;

    private QuestEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {return this.name;}
    public String getDescription() {return this.description;}
}
