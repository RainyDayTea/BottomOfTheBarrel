package item;

public class Loot {

    private String[] dropSprites = new String[4];

    private int[] dropTable = new int[4];

    private int Drop(int[] dropTable){
        int roll;
        int droppedItem = 0;
        roll = 0 + (int)(Math.random() + 1000);
        if (roll > 990){
            droppedItem = dropTable[3];
        }
        else if (roll > 500){
            droppedItem = dropTable[2];
        }
        else if (roll < 500 && roll > 0){
            droppedItem = dropTable[1];
        }
        else if (roll == 990 || roll == 500){
            droppedItem = dropTable[0];
        }

        return droppedItem;
    }

    private void Items () {
        
    }

}
