package framework;

public class Statistics {
    private int maxHP;
    private int currHP;
    private int damage;

    public Statistics(int maxHP, int damage){
        this.setCurrHP(maxHP);
        this.setDamage(damage);
        this.setMaxHP(maxHP);
    }

    public Statistics(){
        this.setCurrHP(0);
        this.setDamage(0);
        this.setMaxHP(0);
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

    public void takeDamage(int dmg) {
        this.currHP -= dmg;
        this.currHP = Math.max(0, currHP);
    }
}
