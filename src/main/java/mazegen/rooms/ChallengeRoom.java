package src.main.java.mazegen.rooms;

import src.main.java.entity.monster.Monster;
import src.main.java.item.Item;
import src.main.java.item.potion.AttackPotion;
import src.main.java.item.potion.HealthPotion;
import src.main.java.item.weapon.SuperSayanBow;

public class ChallengeRoom extends Room {
    private Monster[] monsters;
    private Item[] drops;

    public ChallengeRoom(int[] coords) {
        super(coords);
        monsters = new Monster[3];
        for (int i = 0; i < 3; i++) {
            monsters[i] = Monster.randomMonster();
            monsters[i].setGoldDrop(0);
            monsters[i].setWeaponDrop(0);
        }
        super.setMonster(null);
        drops = new Item[3];
        drops[0] = new HealthPotion(50);
        drops[1] = new AttackPotion(30);
        drops[2] = new SuperSayanBow();
    }

    public String toString() {
        return "C";
    }

    public Monster[] getMonsters() {
        return monsters;
    }
    public void setMonster(Monster monster, int i) {
        monsters[i] = monster;
    }

    public Item[] getDrops() {
        return drops;
    }

    public void setDrops(Item item, int i) {
        drops[i] = item;
    }

    public boolean allMonstersDead() {
        for (Monster m: monsters) {
            if (m != null) {
                return false;
            }
        }
        return true;
    }
}
