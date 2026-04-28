package me.shygars.game.classes;

import com.hypixel.hytale.server.core.inventory.ItemStack;

public class ClassItems {
    public static ItemStack[][][] classItemUpgrades = {
            // Rows = Player's Class | Columns = Upgrade Type | Planes = Upgrade Tier (+1 as 0 is the base items and there aren't any upgrade items for that)
            // Demon Hunter's Upgrades
            {
                    // Demon Hunter's Main Weapon Upgrades
                    {
                            new ItemStack("Shop_DH_Main_Weapon1"),
                            new ItemStack("Shop_DH_Main_Weapon2"),
                            new ItemStack("Shop_DH_Main_Weapon3")
                    },
                    // Demon Hunter's Secondary Weapon Upgrades
                    {
                            new ItemStack("Shop_DH_Sec_Weapon1"),
                            new ItemStack("Shop_DH_Sec_Weapon2"),
                            new ItemStack("Shop_DH_Sec_Weapon3")
                    },
                    // Demon Hunter's Armor Upgrades
                    {
                            new ItemStack("Shop_DH_Armor_Set1"),
                            new ItemStack("Shop_DH_Armor_Set2"),
                            new ItemStack("Shop_DH_Armor_Set3")
                    }
            },
            // Paladin of Light's Upgrades
            {
                    // Paladin of Light's Main Weapon Upgrades
                    {
                            new ItemStack("Shop_PL_Main_Weapon1"),
                            new ItemStack("Shop_PL_Main_Weapon2"),
                            new ItemStack("Shop_PL_Main_Weapon3")
                    },
                    // Paladin of Light's Secondary Weapon Upgrades
                    {
                            new ItemStack("Shop_PL_Sec_Weapon1"),
                            new ItemStack("Shop_PL_Sec_Weapon2"),
                            new ItemStack("Shop_PL_Sec_Weapon3")
                    },
                    // Paladin of Light's Armor Upgrades
                    {
                            new ItemStack("Shop_PL_Armor_Set1"),
                            new ItemStack("Shop_PL_Armor_Set2"),
                            new ItemStack("Shop_PL_Armor_Set3")
                    }
            },
            // Shadow Knight's Upgrades
            {
                    // Shadow Knight's Main Weapon Upgrades
                    {
                            new ItemStack("Shop_SK_Main_Weapon1"),
                            new ItemStack("Shop_SK_Main_Weapon2"),
                            new ItemStack("Shop_SK_Main_Weapon3")
                    },
                    // Shadow Knight's Secondary Weapon Upgrades
                    {
                            new ItemStack("Shop_SK_Sec_Weapon1"),
                            new ItemStack("Shop_SK_Sec_Weapon2"),
                            new ItemStack("Shop_SK_Sec_Weapon3")
                    },
                    // Shadow Knight's Armor Upgrades
                    {
                            new ItemStack("Shop_SK_Armor_Set1"),
                            new ItemStack("Shop_SK_Armor_Set2"),
                            new ItemStack("Shop_SK_Armor_Set3")
                    }
            },
            // Ethereal Mage's Upgrades
            {
                    // Ethereal Mage's Main Weapon Upgrades
                    {
                            new ItemStack("Shop_EM_Main_Weapon1"),
                            new ItemStack("Shop_EM_Main_Weapon2"),
                            new ItemStack("Shop_EM_Main_Weapon3")
                    },
                    // Ethereal Mage's Secondary Weapon Upgrades
                    {
                            new ItemStack("Shop_EM_Sec_Weapon1"),
                            new ItemStack("Shop_EM_Sec_Weapon2"),
                            new ItemStack("Shop_EM_Sec_Weapon3")
                    },
                    // Ethereal Mage's Armor Upgrades
                    {
                            new ItemStack("Shop_EM_Armor_Set1"),
                            new ItemStack("Shop_EM_Armor_Set2"),
                            new ItemStack("Shop_EM_Armor_Set3")
                    }
            },
    };

    public static  ItemStack[][][][] classItems = {
            // Rows = Player's Class | Columns = Item Type | Planes = Item Upgrade Tier | Hyperplane = The Actual Item with the rest of the Armor Pieces if needed
            // Demon Hunter's Items
            {
                    // Demon Hunter's Main Weapons
                    {
                            {new ItemStack("Demon_Hunter_Main_Weapon"), null},
                            {new ItemStack("Demon_Hunter_Main_Weapon_Upgrade1"), null},
                            {new ItemStack("Demon_Hunter_Main_Weapon_Upgrade2"), null},
                            {new ItemStack("Demon_Hunter_Main_Weapon_Upgrade3"), null}
                    },
                    // Demon Hunter's Secondary Weapons
                    {
                            {new ItemStack("Demon_Hunter_Secondary_Weapon"), null},
                            {new ItemStack("Demon_Hunter_Secondary_Weapon_Upgrade1"), null},
                            {new ItemStack("Demon_Hunter_Secondary_Weapon_Upgrade2"), null},
                            {new ItemStack("Demon_Hunter_Secondary_Weapon_Upgrade3"), null}
                    },
                    // Demon Hunter's Armors
                    {
                            {
                                    new ItemStack("Demon_Hunter_Armor_Head"),
                                    new ItemStack("Demon_Hunter_Armor_Chest"),
                                    new ItemStack("Demon_Hunter_Armor_Hands"),
                                    new ItemStack("Demon_Hunter_Armor_Legs")
                            },
                            {
                                    new ItemStack("Demon_Hunter_Armor_Head_Upgrade1"),
                                    new ItemStack("Demon_Hunter_Armor_Chest_Upgrade1"),
                                    new ItemStack("Demon_Hunter_Armor_Hands_Upgrade1"),
                                    new ItemStack("Demon_Hunter_Armor_Legs_Upgrade1")
                            },
                            {
                                    new ItemStack("Demon_Hunter_Armor_Head_Upgrade2"),
                                    new ItemStack("Demon_Hunter_Armor_Chest_Upgrade2"),
                                    new ItemStack("Demon_Hunter_Armor_Hands_Upgrade2"),
                                    new ItemStack("Demon_Hunter_Armor_Legs_Upgrade2")
                            },
                            {
                                    new ItemStack("Demon_Hunter_Armor_Head_Upgrade3"),
                                    new ItemStack("Demon_Hunter_Armor_Chest_Upgrade3"),
                                    new ItemStack("Demon_Hunter_Armor_Hands_Upgrade3"),
                                    new ItemStack("Demon_Hunter_Armor_Legs_Upgrade3")
                            }
                    },
            },
            // Paladin of Light's Items
            {
                    // Paladin of Light's Main Weapons
                    {
                            {new ItemStack("Paladin_Of_Light_Main_Weapon"), null},
                            {new ItemStack("Paladin_Of_Light_Main_Weapon_Upgrade1"), null},
                            {new ItemStack("Paladin_Of_Light_Main_Weapon_Upgrade2"), null},
                            {new ItemStack("Paladin_Of_Light_Main_Weapon_Upgrade3"), null}
                    },
                    // Paladin of Light's Secondary Weapons (The Paladin do not have a secondary weapon)
                    {
                            {new ItemStack("Blocker"), },
                            {new ItemStack("Blocker"), },
                            {new ItemStack("Blocker"), },
                            {new ItemStack("Blocker"), }
                    },
                    // Paladin of Light's Armors
                    {
                            {
                                    new ItemStack("Paladin_Of_Light_Armor_Head"),
                                    new ItemStack("Paladin_Of_Light_Armor_Chest"),
                                    new ItemStack("Paladin_Of_Light_Armor_Hands"),
                                    new ItemStack("Paladin_Of_Light_Armor_Legs")
                            },
                            {
                                    new ItemStack("Paladin_Of_Light_Armor_Head_Upgrade1"),
                                    new ItemStack("Paladin_Of_Light_Armor_Chest_Upgrade1"),
                                    new ItemStack("Paladin_Of_Light_Armor_Hands_Upgrade1"),
                                    new ItemStack("Paladin_Of_Light_Armor_Legs_Upgrade1")
                            },
                            {
                                    new ItemStack("Paladin_Of_Light_Armor_Head_Upgrade2"),
                                    new ItemStack("Paladin_Of_Light_Armor_Chest_Upgrade2"),
                                    new ItemStack("Paladin_Of_Light_Armor_Hands_Upgrade2"),
                                    new ItemStack("Paladin_Of_Light_Armor_Legs_Upgrade2")
                            },
                            {
                                    new ItemStack("Paladin_Of_Light_Armor_Head_Upgrade3"),
                                    new ItemStack("Paladin_Of_Light_Armor_Chest_Upgrade3"),
                                    new ItemStack("Paladin_Of_Light_Armor_Hands_Upgrade3"),
                                    new ItemStack("Paladin_Of_Light_Armor_Legs_Upgrade3")
                            }
                    },
            },
            // Shadow Knight's Items
            {
                    // Shadow Knight's Main Weapons
                    {
                            {new ItemStack("Shadow_Knight_Main_Weapon"), null},
                            {new ItemStack("Shadow_Knight_Main_Weapon_Upgrade1"), null},
                            {new ItemStack("Shadow_Knight_Main_Weapon_Upgrade2"), null},
                            {new ItemStack("Shadow_Knight_Main_Weapon_Upgrade3"), null}
                    },
                    // Shadow Knight's Secondary Weapons
                    {
                            {new ItemStack("Shadow_Knight_Secondary_Weapon"), null},
                            {new ItemStack("Shadow_Knight_Secondary_Weapon_Upgrade1"), null},
                            {new ItemStack("Shadow_Knight_Secondary_Weapon_Upgrade2"), null},
                            {new ItemStack("Shadow_Knight_Secondary_Weapon_Upgrade3"), null}
                    },
                    // Shadow Knight's Armors
                    {
                            {
                                    new ItemStack("Shadow_Knight_Armor_Head"),
                                    new ItemStack("Shadow_Knight_Armor_Chest"),
                                    new ItemStack("Shadow_Knight_Armor_Hands"),
                                    new ItemStack("Shadow_Knight_Armor_Legs")
                            },
                            {
                                    new ItemStack("Shadow_Knight_Armor_Head_Upgrade1"),
                                    new ItemStack("Shadow_Knight_Armor_Chest_Upgrade1"),
                                    new ItemStack("Shadow_Knight_Armor_Hands_Upgrade1"),
                                    new ItemStack("Shadow_Knight_Armor_Legs_Upgrade1")
                            },
                            {
                                    new ItemStack("Shadow_Knight_Armor_Head_Upgrade2"),
                                    new ItemStack("Shadow_Knight_Armor_Chest_Upgrade2"),
                                    new ItemStack("Shadow_Knight_Armor_Hands_Upgrade2"),
                                    new ItemStack("Shadow_Knight_Armor_Legs_Upgrade2")
                            },
                            {
                                    new ItemStack("Shadow_Knight_Armor_Head_Upgrade3"),
                                    new ItemStack("Shadow_Knight_Armor_Chest_Upgrade3"),
                                    new ItemStack("Shadow_Knight_Armor_Hands_Upgrade3"),
                                    new ItemStack("Shadow_Knight_Armor_Legs_Upgrade3")
                            }
                    },
            },
            // Ethereal Mage's Items
            {
                    // Ethereal Mage's Main Weapons
                    {
                            {new ItemStack("Ethereal_Mage_Main_Weapon"), null},
                            {new ItemStack("Ethereal_Mage_Main_Weapon_Upgrade1"), null},
                            {new ItemStack("Ethereal_Mage_Main_Weapon_Upgrade2"), null},
                            {new ItemStack("Ethereal_Mage_Main_Weapon_Upgrade3"), null}
                    },
                    // Ethereal Mage's Secondary Weapons
                    {
                            {new ItemStack("Ethereal_Mage_Secondary_Weapon"), null},
                            {new ItemStack("Ethereal_Mage_Secondary_Weapon_Upgrade1"), null},
                            {new ItemStack("Ethereal_Mage_Secondary_Weapon_Upgrade2"), null},
                            {new ItemStack("Ethereal_Mage_Secondary_Weapon_Upgrade3"), null}
                    },
                    // Ethereal Mage's Armors
                    {
                            {
                                    new ItemStack("Ethereal_Mage_Armor_Head"),
                                    new ItemStack("Ethereal_Mage_Armor_Chest"),
                                    new ItemStack("Ethereal_Mage_Armor_Hands"),
                                    new ItemStack("Ethereal_Mage_Armor_Legs")
                            },
                            {
                                    new ItemStack("Ethereal_Mage_Armor_Head_Upgrade1"),
                                    new ItemStack("Ethereal_Mage_Armor_Chest_Upgrade1"),
                                    new ItemStack("Ethereal_Mage_Armor_Hands_Upgrade1"),
                                    new ItemStack("Ethereal_Mage_Armor_Legs_Upgrade1")
                            },
                            {
                                    new ItemStack("Ethereal_Mage_Armor_Head_Upgrade2"),
                                    new ItemStack("Ethereal_Mage_Armor_Chest_Upgrade2"),
                                    new ItemStack("Ethereal_Mage_Armor_Hands_Upgrade2"),
                                    new ItemStack("Ethereal_Mage_Armor_Legs_Upgrade2")
                            },
                            {
                                    new ItemStack("Ethereal_Mage_Armor_Head_Upgrade3"),
                                    new ItemStack("Ethereal_Mage_Armor_Chest_Upgrade3"),
                                    new ItemStack("Ethereal_Mage_Armor_Hands_Upgrade3"),
                                    new ItemStack("Ethereal_Mage_Armor_Legs_Upgrade3")
                            }
                    },
            },
    };

    public static int getUpgradeTierIndex(ItemStack itemStack) {
        for (int row = 0; row < ClassItems.classItemUpgrades.length; row++) {
            for (int col = 0; col < ClassItems.classItemUpgrades[row].length; col++) {
                for (int pla = 0; pla < ClassItems.classItemUpgrades[row][col].length; pla++) {
                    if (itemStack.getItemId().equals(ClassItems.classItemUpgrades[row][col][pla].getItemId())) {
                        return pla + 1;
                    }
                }
            }
        }
        return 0;
    }
}
