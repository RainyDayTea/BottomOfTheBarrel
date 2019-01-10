package framework;

public class Statistics {
    private int maxHP;
    private int currHP;
    private int damage;
    private int speed;

    public Statistics(int maxHP, int currHP, int damage, int speed){
        this.setCurrHP(currHP);
        this.setDamage(damage);
        this.setMaxHP(maxHP);
        this.setSpeed(speed);
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
