/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RecipeRegistry {
    static private final RecipeRegistry a = new RecipeRegistry();
    private List recipes = new ArrayList();

    public static final RecipeRegistry a() {
        return a;
    }

    private RecipeRegistry() {
        new eg().a(this);
        new pa().a(this);
        new mv().a(this);
        new iw().a(this);
        new gf().a(this);
        new o().a(this);
        this.a(new InventoryItem(Item.aI, 3), "###", Character.valueOf('#'), Item.aH);
        this.a(new InventoryItem(Item.aJ, 1), "#", "#", "#", Character.valueOf('#'), Item.aI);
        this.a(new InventoryItem(Block.aZ, 2), "###", "###", Character.valueOf('#'), Item.B);
        this.a(new InventoryItem(Block.aY, 1), "###", "#X#", "###", Character.valueOf('#'), Block.x, Character.valueOf('X'), Item.l);
        this.a(new InventoryItem(Block.an, 1), "###", "XXX", "###", Character.valueOf('#'), Block.x, Character.valueOf('X'), Item.aJ);
        this.a(new InventoryItem(Block.aU, 1), "##", "##", Character.valueOf('#'), Item.aB);
        this.a(new InventoryItem(Block.aW, 1), "##", "##", Character.valueOf('#'), Item.aG);
        this.a(new InventoryItem(Block.al, 1), "##", "##", Character.valueOf('#'), Item.aF);
        this.a(new InventoryItem(Block.bd, 1), "###", "###", "###", Character.valueOf('#'), Item.aR);
        this.a(new InventoryItem(Block.ab, 1), "###", "###", "###", Character.valueOf('#'), Item.I);
        this.a(new InventoryItem(Block.am, 1), "X#X", "#X#", "X#X", Character.valueOf('X'), Item.K, Character.valueOf('#'), Block.E);
        this.a(new InventoryItem(Block.ak, 3), "###", Character.valueOf('#'), Block.w);
        this.a(new InventoryItem(Block.aF, 1), "# #", "###", "# #", Character.valueOf('#'), Item.B);
        this.a(new InventoryItem(Item.at, 1), "##", "##", "##", Character.valueOf('#'), Block.x);
        this.a(new InventoryItem(Item.az, 1), "##", "##", "##", Character.valueOf('#'), Item.m);
        this.a(new InventoryItem(Item.as, 1), "###", "###", " X ", Character.valueOf('#'), Block.x, Character.valueOf('X'), Item.B);
        this.a(new InventoryItem(Block.x, 4), "#", Character.valueOf('#'), Block.J);
        this.a(new InventoryItem(Item.B, 4), "#", "#", Character.valueOf('#'), Block.x);
        this.a(new InventoryItem(Block.aq, 4), "X", "#", Character.valueOf('X'), Item.k, Character.valueOf('#'), Item.B);
        this.a(new InventoryItem(Item.C, 4), "# #", " # ", Character.valueOf('#'), Block.x);
        this.a(new InventoryItem(Block.aG, 16), "X X", "X#X", "X X", Character.valueOf('X'), Item.m, Character.valueOf('#'), Item.B);
        this.a(new InventoryItem(Item.ax, 1), "# #", "###", Character.valueOf('#'), Item.m);
        this.a(new InventoryItem(Block.bf, 1), "A", "B", Character.valueOf('A'), Block.ba, Character.valueOf('B'), Block.aq);
        this.a(new InventoryItem(Item.aL, 1), "A", "B", Character.valueOf('A'), Block.au, Character.valueOf('B'), Item.ax);
        this.a(new InventoryItem(Item.aM, 1), "A", "B", Character.valueOf('A'), Block.aB, Character.valueOf('B'), Item.ax);
        this.a(new InventoryItem(Item.aC, 1), "# #", "###", Character.valueOf('#'), Block.x);
        this.a(new InventoryItem(Item.au, 1), "# #", " # ", Character.valueOf('#'), Item.m);
        this.a(new InventoryItem(Item.g, 1), "A ", " B", Character.valueOf('A'), Item.m, Character.valueOf('B'), Item.an);
        this.a(new InventoryItem(Item.S, 1), "###", Character.valueOf('#'), Item.R);
        this.a(new InventoryItem(Block.at, 4), "#  ", "## ", "###", Character.valueOf('#'), Block.x);
        this.a(new InventoryItem(Item.aP, 1), "  #", " #X", "# X", Character.valueOf('#'), Item.B, Character.valueOf('X'), Item.I);
        this.a(new InventoryItem(Block.aH, 4), "#  ", "## ", "###", Character.valueOf('#'), Block.w);
        this.a(new InventoryItem(Item.aq, 1), "###", "#X#", "###", Character.valueOf('#'), Item.B, Character.valueOf('X'), Block.ab);
        this.a(new InventoryItem(Item.ar, 1), "###", "#X#", "###", Character.valueOf('#'), Block.ah, Character.valueOf('X'), Item.h);
        this.a(new InventoryItem(Block.aJ, 1), "X", "#", Character.valueOf('#'), Block.w, Character.valueOf('X'), Item.B);
        this.a(new InventoryItem(Block.aQ, 1), "X", "#", Character.valueOf('#'), Item.B, Character.valueOf('X'), Item.aA);
        this.a(new InventoryItem(Item.aQ, 1), " # ", "#X#", " # ", Character.valueOf('#'), Item.n, Character.valueOf('X'), Item.aA);
        this.a(new InventoryItem(Item.aO, 1), " # ", "#X#", " # ", Character.valueOf('#'), Item.m, Character.valueOf('X'), Item.aA);
        this.a(new InventoryItem(Block.aR, 1), "#", "#", Character.valueOf('#'), Block.t);
        this.a(new InventoryItem(Block.aK, 1), "###", Character.valueOf('#'), Block.t);
        this.a(new InventoryItem(Block.aM, 1), "###", Character.valueOf('#'), Block.x);
        Collections.sort(this.recipes, new gr(this));
        System.out.println(this.recipes.size() + " recipes");
    }

    void a(InventoryItem fp2, Object ... objectArray) {
        Object object;
        String string = "";
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        if (objectArray[n2] instanceof String[]) {
            object = (String[])objectArray[n2++];
            for (int i2 = 0; i2 < ((String[])object).length; ++i2) {
                Object object2 = ((String[])object)[i2];
                ++n4;
                n3 = ((String)object2).length();
                string = string + (String)object2;
            }
        } else {
            while (objectArray[n2] instanceof String) {
                object = (String)objectArray[n2++];
                ++n4;
                n3 = ((String)object).length();
                string = string + (String)object;
            }
        }
        object = new HashMap();
        while (n2 < objectArray.length) {
            Character c2 = (Character)objectArray[n2];
            int n5 = 0;
            if (objectArray[n2 + 1] instanceof Item) {
                n5 = ((Item)objectArray[n2 + 1]).id;
            } else if (objectArray[n2 + 1] instanceof Block) {
                n5 = ((Block)objectArray[n2 + 1]).bh;
            }
            HashMap _object = (HashMap)object;
            _object.put(c2, n5);
            n2 += 2;
        }
        int[] nArray = new int[n3 * n4];
        for (int i3 = 0; i3 < n3 * n4; ++i3) {
            char c3 = string.charAt(i3);
            HashMap _object = (HashMap)object;
            nArray[i3] = _object.containsKey(Character.valueOf(c3)) ? (Integer)_object.get(Character.valueOf(c3)) : -1;
        }
        this.recipes.add(new Recipe(n3, n4, nArray, fp2));
    }

    public InventoryItem a(int[] nArray) {
        for (int i2 = 0; i2 < this.recipes.size(); ++i2) {
            Recipe cd2 = (Recipe)this.recipes.get(i2);
            if (!cd2.a(nArray)) continue;
            return cd2.b(nArray);
        }
        return null;
    }
}

