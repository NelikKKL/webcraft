/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class es {
    private Random c = new Random();
    private Map d = new HashMap();
    private List e = new ArrayList();
    public int a = 0;
    public boolean b = true;

    public al a(String string, File file) {
        try {
            String string2 = string;
            string = string.substring(0, string.indexOf("."));
            if (this.b) {
                while (Character.isDigit(string.charAt(string.length() - 1))) {
                    string = string.substring(0, string.length() - 1);
                }
            }
            if (!this.d.containsKey(string = string.replaceAll("/", "."))) {
                this.d.put(string, new ArrayList());
            }
            al al2 = new al(string2, file.toURI().toURL());
            ((List)this.d.get(string)).add(al2);
            this.e.add(al2);
            ++this.a;
            return al2;
        }
        catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
            throw new RuntimeException(malformedURLException);
        }
    }

    public al a(String string) {
        List list = (List)this.d.get(string);
        if (list == null) {
            return null;
        }
        return (al)list.get(this.c.nextInt(list.size()));
    }

    public al a() {
        if (this.e.size() == 0) {
            return null;
        }
        return (al)this.e.get(this.c.nextInt(this.e.size()));
    }
}

