/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class UnusedOSLookup {
    static final int[] a;

    static {
        a = new int[OperatingSystems.values().length];
        try {
            UnusedOSLookup.a[OperatingSystems.linux.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UnusedOSLookup.a[OperatingSystems.solaris.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UnusedOSLookup.a[OperatingSystems.windows.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UnusedOSLookup.a[OperatingSystems.macos.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

