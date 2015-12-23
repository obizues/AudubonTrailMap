package com.lightningboltstudios.audubontrailmap;

public class MoveDataProvider {
    private int move_poster_resource;
    private String move_title;
    private  String move_rating;

    public String getMove_rating() {
        return move_rating;

    }
    public MoveDataProvider(int move_poster_resource,String move_title,String move_rating){


        this.setMove_poster_resource(move_poster_resource);
        this.setMove_title(move_title);
        this.setMove_rating(move_rating);
    }

    public void setMove_rating(String move_rating) {
        this.move_rating = move_rating;
    }

    public String getMove_title() {
        return move_title;
    }

    public void setMove_title(String move_title) {
        this.move_title = move_title;
    }

    public int getMove_poster_resource() {
        return move_poster_resource;
    }

    public void setMove_poster_resource(int move_poster_resource) {
        this.move_poster_resource = move_poster_resource;
    }
}