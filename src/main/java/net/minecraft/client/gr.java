/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Comparator;

class gr
implements Comparator {
    final RecipeRegistry a;

    gr(RecipeRegistry en2) {
        this.a = en2;
    }

    public int a(Recipe cd2, Recipe cd3) {
        if (cd3.a() < cd2.a()) {
            return -1;
        }
        if (cd3.a() > cd2.a()) {
            return 1;
        }
        return 0;
    }

    public int compare(Object object, Object object2) {
        return this.a((Recipe)object, (Recipe)object2);
    }
}

