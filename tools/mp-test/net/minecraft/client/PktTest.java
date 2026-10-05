package net.minecraft.client;
import java.io.*;
import java.util.*;
import net.minecraft.client.web.Zlib;

public class PktTest {
  static gk roundTrip(gk p) throws Exception {
    ByteArrayOutputStream bos=new ByteArrayOutputStream(); DataOutputStream dos=new DataOutputStream(bos);
    gk.a(p,dos); dos.flush(); byte[] b=bos.toByteArray();
    DataInputStream dis=new DataInputStream(new ByteArrayInputStream(b));
    gk q=gk.b(dis);
    if (dis.available()!=0) throw new RuntimeException("leftover bytes after "+p.getClass().getSimpleName()+": "+dis.available());
    return q;
  }
  static int fails=0;
  static void check(boolean ok,String what){ if(!ok){fails++;System.out.println("FAIL: "+what);} }
  public static void main(String[] a) throws Exception {
    // login reply
    iu l=new iu(); l.a=77; l.b=""; l.c=""; l.d=123456789L; l.e=(byte)0;
    iu l2=(iu)roundTrip(l); check(l2.a==77&&l2.d==123456789L&&l2.b.equals(""),"login");
    // handshake
    hw h=(hw)roundTrip(new hw("-")); check(h.a.equals("-"),"handshake");
    kv sp=new kv(); sp.a=10; sp.b=64; sp.c=-20; kv sp2=(kv)roundTrip(sp); check(sp2.a==10&&sp2.b==64&&sp2.c==-20,"spawn");
    ek tm=new ek(); tm.a=987654L; check(((ek)roundTrip(tm)).a==987654L,"time");
    // inventory
    InventoryItem[] inv=new InventoryItem[37]; inv[0]=new InventoryItem(257,1,0); inv[4]=new InventoryItem(50,64,0);
    p pi=(p)roundTrip(new p(-1,inv)); check(pi.a==-1&&pi.b.length==37&&pi.b[0].id==257&&pi.b[4].count==64&&pi.b[1]==null,"inventory");
    // position (server->client)
    cr pos=(cr)roundTrip(new cr(1.5,65.62,64.0,-3.25,90f,10f,true));
    check(pos.a==1.5&&pos.b==65.62&&pos.c==-3.25&&pos.d==64.0&&pos.e==90f&&pos.f==10f&&pos.g,"position");
    // spawn player
    hs s=new hs(); s.a=5; s.b="Steve"; s.c=100; s.d=2048; s.e=-300; s.f=(byte)64; s.g=(byte)-10; s.h=0;
    hs s2=(hs)roundTrip(s); check(s2.a==5&&s2.b.equals("Steve")&&s2.c==100&&s2.d==2048&&s2.e==-300&&s2.f==64&&s2.g==-10,"namedspawn");
    // prechunk + teleport + block change + destroy + chat + kick + collect + inventory-add
    lq pre=new lq(); pre.a=3; pre.b=-4; pre.c=true; lq pre2=(lq)roundTrip(pre); check(pre2.a==3&&pre2.b==-4&&pre2.c,"prechunk");
    ky t=new ky(); t.a=9; t.b=1; t.c=2; t.d=3; t.e=(byte)4; t.f=(byte)5; ky t2=(ky)roundTrip(t); check(t2.a==9&&t2.d==3&&t2.f==5,"teleport");
    mx m=new mx(); m.a=-5; m.b=70; m.c=22; m.d=1; m.e=3; mx m2=(mx)roundTrip(m); check(m2.a==-5&&m2.b==70&&m2.c==22&&m2.d==1&&m2.e==3,"blockchange");
    li d=new li(); d.a=12; check(((li)roundTrip(d)).a==12,"destroy");
    check(((jr)roundTrip(new jr("<A> hi"))).a.equals("<A> hi"),"chat");
    check(((qi)roundTrip(new qi("bye"))).a.equals("bye"),"kick");
    bu col=new bu(); col.a=4; col.b=6; bu col2=(bu)roundTrip(col); check(col2.a==4&&col2.b==6,"collect");
    mt give=new mt(); give.a=264; give.b=3; give.c=0; mt g2=(mt)roundTrip(give); check(g2.a==264&&g2.b==3,"giveitem");
    check(roundTrip(new hl()) instanceof hl,"keepalive");
    // THE chunk packet: server-built zlib payload must be decoded by the client's own ci.a(DataInputStream)
    byte[] raw=new byte[81920]; Random r=new Random(3);
    for(int i=0;i<32768;i++) raw[i]=(byte)((i%128)<60?(i%128<4?7:1):0);          // blocks
    for(int i=32768;i<49152;i++) raw[i]=(byte)(i%97==0?r.nextInt(256):0);         // metadata
    for(int i=49152;i<65536;i++) raw[i]=0;                                       // block light
    for(int i=65536;i<81920;i++) raw[i]=(byte)0xFF;                              // sky light
    byte[] z=Zlib.compress(raw,raw.length);
    ci c=new ci(); c.a=32; c.b=0; c.c=-48; c.d=16; c.e=128; c.f=16; c.setCompressed(z);
    ci c2=(ci)roundTrip(c);
    check(c2.a==32&&c2.b==0&&c2.c==-48&&c2.d==16&&c2.e==128&&c2.f==16,"chunk header");
    check(Arrays.equals(Arrays.copyOf(c2.g,81920),raw),"chunk payload decoded by game's Inflater == original");
    System.out.println("chunk "+raw.length+" -> "+z.length+" bytes on the wire");
    // client->server packets as sent by the real client classes
    t3(new cr(1,2,3,4,5f,6f,false)); 
    System.out.println(fails==0?"ALL PACKETS OK":"FAILURES: "+fails);
  }
  static void t3(cr p) throws Exception { cr q=(cr)roundTrip(p); check(q.a==1&&q.b==2&&q.d==3&&q.c==4&&q.e==5f&&q.f==6f,"cr client->server field order x,y,stance,z"); }
}
