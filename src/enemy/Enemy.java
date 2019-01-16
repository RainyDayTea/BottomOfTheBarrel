package enemy;

import framework.Character;
import framework.geom.Circle;


public abstract class Enemy extends Character{

    private Circle hitbox = new Circle(getPosition());
    public Enemy() {

    }

    public void isDead() {
        if (this.getStats().getCurrHP() <= 0){
        // remove this object from the game
        }
    }

    public void calcDamage(){

    }

}
