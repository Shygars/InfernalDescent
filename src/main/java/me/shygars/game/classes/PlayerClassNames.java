package me.shygars.game.classes;

public class PlayerClassNames {
    public static String getClassName(Integer var) {
        if(var == 0) {
            return "Demon Hunter";
        }
        else if(var == 1) {
            return "Paladin of Light";
        }
        else if(var == 2) {
            return "Shadow Knight";
        }
        else if(var == 3) {
            return "Ethereal Mage";
        }
        else return "Hermit";
    }
}
