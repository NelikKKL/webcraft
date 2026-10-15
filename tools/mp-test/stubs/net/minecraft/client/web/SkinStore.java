package net.minecraft.client.web;
public final class SkinStore {
  public static final String PATH="/skin/player.png", DEFAULT_PATH="/mob/char.png";
  public static String texturePath(){return DEFAULT_PATH;}
  public static boolean isModern(){return false;} public static boolean isSlim(){return false;} public static boolean hasSkin(){return false;}
  public static String nick(){return "Player";}
  public static int localVersion(){return 0;}
  public static byte[] localPng(){return null;}
  public static void queueRemote(int id, boolean slim, byte[] png){}
  public static void queueRemoteDefault(int id){ known.remove(id); }
  public static final class Remote { public final int entityId; public final String path; public final boolean modern, slim; public Remote(int i,String p,boolean m,boolean s){entityId=i;path=p;modern=m;slim=s;} }
  private static final java.util.HashMap<Integer,Remote> known=new java.util.HashMap<>();
  public static Remote known(int id){ return known.get(id); }
  public static void clearKnown(){ known.clear(); }
  public static void testPutKnown(int id,String path,boolean modern,boolean slim){ known.put(id,new Remote(id,path,modern,slim)); }
}
