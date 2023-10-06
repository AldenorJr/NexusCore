package br.com.nexus.main.core.object;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity("Players")
public class PlayerModel {

    @Id
    private String ID;

    private String name;
    private ArrayList<TagsModel> tagsModelsList;

    public PlayerModel(String name, ArrayList<TagsModel> tagsModelsList) {
        this.name = name;
        this.tagsModelsList = tagsModelsList;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TagsModel> getTagsModelsList() {
        return tagsModelsList;
    }

    public void setTagsModelsList(ArrayList<TagsModel> tagsModelsList) {
        this.tagsModelsList = tagsModelsList;
    }

}
