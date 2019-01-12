package enemy;

import framework.Character;

public abstract class Enemy extends Character{

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
