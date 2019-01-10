package framework;

import framework.geom.Vector2D;

public abstract class Article {

    private String name;
    private Vector2D position = new Vector2D();
    private Vector2D speed = new Vector2D();
    private boolean invulnerable;
    // a direction variable
    // a hitbox variable
    // quad tree variable


    public Article(){
        this.invulnerable = false;

    }
}
