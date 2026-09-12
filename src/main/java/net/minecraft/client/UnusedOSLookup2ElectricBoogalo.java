/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class UnusedOSLookup2ElectricBoogalo {
    public static final int[] a;

    static {
        // notch i fucking hate you for this code
        a = new int[jp.values().length];
        try {
            UnusedOSLookup2ElectricBoogalo.a[jp.linux.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UnusedOSLookup2ElectricBoogalo.a[jp.solaris.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UnusedOSLookup2ElectricBoogalo.a[jp.windows.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UnusedOSLookup2ElectricBoogalo.a[jp.macos.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

