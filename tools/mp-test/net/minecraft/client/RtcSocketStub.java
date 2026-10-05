package net.minecraft.client;
import java.util.*;
import net.minecraft.client.web.RtcSocket;
/** Fake data channel: no WebRTC, no JS. */
public class RtcSocketStub extends RtcSocket {
  boolean open = false, closedFlag = false;
  final List<byte[]> inbox = new ArrayList<>();
  final List<byte[]> sent = new ArrayList<>();
  public RtcSocketStub() { super(-1, false); }
  void fireOpen() { onOpen(); }
  void fireClose() { closedFlag = true; open = false; onClose(); }
  void inject(byte[] m) { inbox.add(m); }
  @Override public boolean isOpen() { return open && !closedFlag; }
  @Override public boolean isClosed() { return closedFlag; }
  @Override public void sendPacket(byte[] data, int len) { sent.add(Arrays.copyOf(data, len)); }
  @Override public byte[] poll() { return inbox.isEmpty() ? null : inbox.remove(0); }
  @Override public void close() { closedFlag = true; open = false; }
  List<gk> drain() throws Exception { List<gk> r = new ArrayList<>(); for (byte[] b : sent) r.add(MpIntegration.parse(b)); sent.clear(); return r; }
}
