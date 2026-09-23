/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {
    static private Map nameToClass = new HashMap();
    static private Map classToName = new HashMap();
    static private Map idToClass = new HashMap();
    static private Map classToid = new HashMap();

    private static void registerEntity(Class clazz, String string, int n2) {
        nameToClass.put(string, clazz);
        classToName.put(clazz, string);
        idToClass.put(n2, clazz);
        classToid.put(clazz, n2);
    }

    /**
     * ИСПРАВЛЕНО: раньше здесь была рефлексия
     * (clazz.getConstructor(Session.class).newInstance(cy2)) — она требует
     * org.teavm.platform.Platform, которого нет на WASM-GC таргете.
     * Явная фабрика покрывает все 18 зарегистрированных типов сущностей
     * (см. static-блок ниже) — конструктор Class(Session) есть у каждого.
     * package-private: используется также из bg.java (спавнер мобов),
     * который раньше делал точно такой же реflective-вызов сам.
     */
    static lw construct(Class clazz, Session cy2) {
        if (clazz == Arrow.class) return new Arrow(cy2);
        if (clazz == Snowball.class) return new Snowball(cy2);
        if (clazz == DroppedItem.class) return new DroppedItem(cy2);
        if (clazz == Painting.class) return new Painting(cy2);
        if (clazz == Mob.class) return new Mob(cy2);
        if (clazz == Monster.class) return new Monster(cy2);
        if (clazz == Creeper.class) return new Creeper(cy2);
        if (clazz == Skeleton.class) return new Skeleton(cy2);
        if (clazz == Spider.class) return new Spider(cy2);
        if (clazz == Giant.class) return new Giant(cy2);
        if (clazz == Zombie.class) return new Zombie(cy2);
        if (clazz == Slime.class) return new Slime(cy2);
        if (clazz == Ghast.class) return new Ghast(cy2);
        if (clazz == PigZombie.class) return new PigZombie(cy2);
        if (clazz == Pig.class) return new Pig(cy2);
        if (clazz == Sheep.class) return new Sheep(cy2);
        if (clazz == Cow.class) return new Cow(cy2);
        if (clazz == Chicken.class) return new Chicken(cy2);
        if (clazz == PrimedTNT.class) return new PrimedTNT(cy2);
        if (clazz == FallingSand.class) return new FallingSand(cy2);
        if (clazz == Minecart.class) return new Minecart(cy2);
        if (clazz == Boat.class) return new Boat(cy2);
        return null;
    }

    public static lw a(String string, Session cy2) {
        lw lw2 = null;
        try {
            Class clazz = (Class)nameToClass.get(string);
            if (clazz != null) {
                lw2 = construct(clazz, cy2);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return lw2;
    }

    public static lw a(NBTCompoundTag iq2, Session cy2) {
        lw lw2 = null;
        try {
            Class clazz = (Class)nameToClass.get(iq2.getString("id"));
            if (clazz != null) {
                lw2 = construct(clazz, cy2);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (lw2 != null) {
            lw2.e(iq2);
        } else {
            System.out.println("Skipping Entity with id " + iq2.getString("id"));
        }
        return lw2;
    }

    public static lw a(int n2, Session cy2) {
        lw lw2 = null;
        try {
            Class clazz = (Class)idToClass.get(n2);
            if (clazz != null) {
                lw2 = construct(clazz, cy2);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (lw2 == null) {
            System.out.println("Skipping Entity with id " + n2);
        }
        return lw2;
    }

    public static int a(lw lw2) {
        return (Integer)classToid.get(lw2.getClass());
    }

    public static String b(lw lw2) {
        return (String)classToName.get(lw2.getClass());
    }

    static {
        EntityRegistry.registerEntity(Arrow.class, "Arrow", 10);
        EntityRegistry.registerEntity(Snowball.class, "Snowball", 11);
        EntityRegistry.registerEntity(DroppedItem.class, "Item", 1);
        EntityRegistry.registerEntity(Painting.class, "Painting", 9);
        EntityRegistry.registerEntity(Mob.class, "Mob", 48);
        EntityRegistry.registerEntity(Monster.class, "Monster", 49);
        EntityRegistry.registerEntity(Creeper.class, "Creeper", 50);
        EntityRegistry.registerEntity(Skeleton.class, "Skeleton", 51);
        EntityRegistry.registerEntity(Spider.class, "Spider", 52);
        EntityRegistry.registerEntity(Giant.class, "Giant", 53);
        EntityRegistry.registerEntity(Zombie.class, "Zombie", 54);
        EntityRegistry.registerEntity(Slime.class, "Slime", 55);
        EntityRegistry.registerEntity(Ghast.class, "Ghast", 56);
        EntityRegistry.registerEntity(PigZombie.class, "PigZombie", 57);
        EntityRegistry.registerEntity(Pig.class, "Pig", 90);
        EntityRegistry.registerEntity(Sheep.class, "Sheep", 91);
        EntityRegistry.registerEntity(Cow.class, "Cow", 92);
        EntityRegistry.registerEntity(Chicken.class, "Chicken", 93);
        EntityRegistry.registerEntity(PrimedTNT.class, "PrimedTnt", 20);
        EntityRegistry.registerEntity(FallingSand.class, "FallingSand", 21);
        EntityRegistry.registerEntity(Minecart.class, "Minecart", 40);
        EntityRegistry.registerEntity(Boat.class, "Boat", 41);
    }
}

