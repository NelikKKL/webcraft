package net.minecraft.client.web;
public final class SkinStore {
  public static final String PATH="/skin/player.png", DEFAULT_PATH="/mob/char.png";
  public static String texturePath(){return DEFAULT_PATH;}
  public static boolean isModern(){return false;} public static boolean isSlim(){return false;} public static boolean hasSkin(){return false;}
  public static String nick(){return "Player";}
}
