package net.minecraft.client.web;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.netshim.Socket;

/**
 * "Сокет" поверх WebRTC DataChannel для сетевого менеджера игры (jq.java).
 *
 * Не TCP-поток, а очередь сообщений: одно сообщение DataChannel = один пакет
 * игры. Поэтому jq в этом режиме не использует потоки чтения/записи (см. jq.java):
 * входящие пакеты забираются через {@link #poll()} в игровом тике, исходящие
 * уходят через {@link #sendPacket(byte[], int)}.
 */
public class RtcSocket extends Socket implements Mp.Listener {

    private final int linkId;
    private final List<byte[]> inbox = new ArrayList<>();
    private boolean open;
    private boolean closed;
    private Mp.Listener extra;

    /** Создаёт сокет вокруг уже существующей ссылки; Mp-слушатель берёт на себя. */
    public RtcSocket(int linkId, boolean alreadyOpen) {
        this.linkId = linkId;
        this.open = alreadyOpen;
    }

    public int linkId() { return linkId; }

    /** Дополнительный слушатель (экран подключения хочет знать про onOpen/onClose/onCode). */
    public void setExtra(Mp.Listener l) { this.extra = l; }

    public boolean isOpen() { return open && !closed; }
    public boolean isClosed() { return closed; }

    public void sendPacket(byte[] data, int len) {
        if (closed) return;
        Mp.send(linkId, data, len);
    }

    /** Следующий принятый пакет или null. */
    public byte[] poll() {
        if (inbox.isEmpty()) return null;
        return inbox.remove(0);
    }

    @Override
    public void close() {
        if (closed) return;
        closed = true;
        open = false;
        Mp.close(linkId);
    }

    // ---- Mp.Listener
    @Override public void onOpen() { open = true; if (extra != null) extra.onOpen(); }
    @Override public void onMessage(byte[] data) { inbox.add(data); }
    @Override public void onClose() { open = false; closed = true; if (extra != null) extra.onClose(); }
    @Override public void onCode(String code, String error) { if (extra != null) extra.onCode(code, error); }
    @Override public void onAccepted(String error) { if (extra != null) extra.onAccepted(error); }
}
