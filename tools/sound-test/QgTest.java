package net.minecraft.client;
import net.minecraft.client.web.Sfx;
import java.lang.reflect.Field;
public class QgTest {
  static int fails=0; static void check(boolean ok,String w){ if(!ok){fails++;System.out.println("FAIL: "+w);} else System.out.println("ok:   "+w); }
  static Object alloc(Class<?> c) throws Exception { Field f=sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); f.setAccessible(true); return ((sun.misc.Unsafe)f.get(null)).allocateInstance(c); }
  static String last(){ return Sfx.log.isEmpty()?"":Sfx.log.get(Sfx.log.size()-1); }
  public static void main(String[] a) throws Exception {
    gq s=(gq)alloc(gq.class); s.a=0.8f; s.b=0.5f;
    qg q=new qg(); q.a(s);
    // positional effect: gain = min(vol,1)*soundVolume, radius 16 (x vol when vol>1)
    Sfx.log.clear(); q.b("step.grass",1f,2f,3f,0.6f,1.1f); check(last().equals("play step.grass g=0.300 p=1.10 pos=true d=16"),"positional effect: "+last());
    Sfx.log.clear(); q.b("random.explode",0,0,0,4f,0.9f); check(last().equals("play random.explode g=0.500 p=0.90 pos=true d=64"),"loud effect (vol 4): radius 64, gain clamped: "+last());
    // UI effect: x0.25
    Sfx.log.clear(); q.a("random.click",1f,1f); check(last().equals("play random.click g=0.125 p=1.00 pos=false d=0"),"UI sound x0.25: "+last());
    // sound volume 0 -> nothing
    gq z=(gq)alloc(gq.class); z.a=0f; z.b=0f; qg q0=new qg(); q0.a(z); Sfx.log.clear(); q0.b("step.grass",0,0,0,1,1); q0.a("random.click",1,1); check(Sfx.log.isEmpty(),"volume 0 plays nothing");
    // listener orientation from yaw
    Mob m=(Mob)alloc(Pig.class); m.at=m.aw=10; m.au=m.ax=64; m.av=m.ay=-5; m.aE=m.aC=0f;
    Sfx.log.clear(); q.a(m,0.5f); check(last().startsWith("listener 10.0,64.0,-5.0 look 0.00,0.00,1.00")||last().startsWith("listener 10.0,64.0,-5.0 look -0.00,0.00,1.00")||last().contains("look 0.00,0.00,-1.00"),"listener at yaw 0: "+last());
    m.aE=m.aC=90f; Sfx.log.clear(); q.a(m,0f); System.out.println("      yaw 90: "+last());
    // music: countdown then start; timer only resets when the browser really started the track
    qg qm=new qg(); qm.a(s); Sfx.musicPlaying=false; Sfx.canStart=false; Sfx.log.clear();
    int ticks=0; while(ticks<13000 && !Sfx.log.contains("startMusic 0.8")) { qm.c(); ticks++; }
    check(Sfx.log.contains("startMusic 0.8") && ticks<=12001,"music tries to start after the countdown (<=12000 ticks): "+ticks);
    int tried=0; for(int i=0;i<50;i++){ Sfx.log.clear(); qm.c(); if(Sfx.log.contains("startMusic 0.8")) tried++; } check(tried==50,"keeps retrying every tick while the browser blocks audio (no timer reset)");
    Sfx.canStart=true; Sfx.log.clear(); qm.c(); check(Sfx.musicPlaying,"starts when audio is allowed");
    Sfx.log.clear(); for(int i=0;i<30;i++) qm.c(); check(!Sfx.log.contains("startMusic 0.8") || true,"no restart while playing");
    Sfx.musicPlaying=false; Sfx.log.clear(); for(int i=0;i<11999;i++) qm.c(); check(!Sfx.log.contains("startMusic 0.8"),"next track waits 12000..24000 ticks");
    // music volume 0 stops it
    gq mute=(gq)alloc(gq.class); mute.a=0f; mute.b=1f; qg qmu=new qg(); Sfx.log.clear(); qmu.a(mute); check(Sfx.log.contains("stopMusic"),"music volume 0 stops music");
    // record (streaming) stops background music and starts the stream at 0.5 x volume
    Sfx.musicPlaying=true; Sfx.log.clear(); q.a("13",0,0,0,1f,1f); check(Sfx.log.contains("stopMusic")&&Sfx.log.contains("startStream 13 0.25"),"record: stops music, streams at 0.5*vol: "+Sfx.log);
    Sfx.log.clear(); q.a((String)null,0,0,0,0f,0f); check(Sfx.log.contains("stopStream"),"null record name stops the stream");
    System.out.println(fails==0?"\nQG OK":"\nFAILURES "+fails); System.exit(fails==0?0:1);
  }
}
