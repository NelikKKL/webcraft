package net.minecraft.client;

import net.minecraft.client.web.Mp;
import net.minecraft.client.web.RtcSocket;

/**
 * Экран "Multiplayer" (было: ввод IP сервера). Теперь подключение идёт через WebRTC
 * без серверов:
 *   1. Игрок жмёт "Paste host's code" и вставляет код приглашения хоста.
 *   2. Игра показывает код-ответ — его нужно отправить хосту (кнопка Copy).
 *   3. Когда хост вставит ответ, канал открывается и начинается вход в мир.
 * Подробности про коды — web/extras.js (раздел "Мультиплеер").
 */
public class hd extends bp {

    private static final int INPUT = 0, WORKING = 1, REPLY = 2;

    private final bp parent;
    private int state = INPUT;
    private int tick = 0;
    private int copiedUntil = -1;
    private boolean waitingPaste = false;
    private String error = null;
    private String reply = null;
    private RtcSocket sock = null;
    private volatile boolean opened = false;
    private volatile boolean closed = false;
    private boolean joined = false;

    public hd(bp parent) {
        this.parent = parent;
    }

    @Override
    public void a() {
        layout();
    }

    private void layout() {
        this.e.clear();
        int x = this.c / 2 - 100;
        int y = this.d / 2 + 30;
        if (state == INPUT) {
            this.e.add(new gh(1, x, y, 200, 20, "Paste host's code"));
        } else if (state == REPLY) {
            this.e.add(new gh(2, x, y, 200, 20, tick < copiedUntil ? "Copied!" : "Copy reply code"));
        }
        this.e.add(new gh(0, x, y + 24, 200, 20, "Cancel"));
    }

    private void leave() {
        if (!joined && sock != null) sock.close();
        this.b.a(this.parent);
    }

    @Override
    protected void a(gh gh2) {
        if (!gh2.g) return;
        if (gh2.f == 0) {
            leave();
        } else if (gh2.f == 1) {
            error = null;
            waitingPaste = true;
            Mp.requestPaste();
        } else if (gh2.f == 2 && reply != null) {
            Mp.copy(reply);
            copiedUntil = tick + 50;
            layout();
        }
    }

    @Override
    protected void a(char c2, int n2) {
        if (n2 == 1) {
            leave();
        } else if (c2 == '\u0016' && state == INPUT) {      // Ctrl+V
            error = null;
            waitingPaste = true;
            Mp.requestPaste();
        }
    }

    private void start(String code) {
        state = WORKING;
        error = null;
        reply = null;
        opened = false;
        closed = false;
        int link = Mp.newLink(null);
        sock = new RtcSocket(link, false);
        Mp.registerListener(link, sock);
        sock.setExtra(new Mp.Listener() {
            public void onOpen() { opened = true; }
            public void onMessage(byte[] data) { }
            public void onClose() { closed = true; }
            public void onCode(String c, String err) {
                if (err != null && err.length() > 0) {
                    error = err;
                    state = INPUT;
                } else {
                    reply = c;
                    state = REPLY;
                }
            }
            public void onAccepted(String err) { }
        });
        Mp.acceptInvite(link, code);
        layout();
    }

    @Override
    public void g() {
        super.g();
        ++tick;
        int before = state;
        if (waitingPaste) {
            String t = Mp.consumePasted();
            if (t != null) {
                waitingPaste = false;
                if (t.trim().length() > 0) start(t.trim());
            }
        }
        if (copiedUntil == tick) layout();
        if (state != before) layout();
        if (state == REPLY && opened && !joined) {
            joined = true;
            try {
                ib handler = new ib(this.b, sock);
                handler.a((gk)new hw(this.b.i.b));
                this.b.a(new og(this.b, handler));
            } catch (Exception ex) {
                ex.printStackTrace();
                joined = false;
                error = "Could not join: " + ex;
                state = INPUT;
                layout();
            }
        } else if (state == REPLY && closed && !joined) {
            error = "Connection failed (the host may be behind a strict NAT).";
            state = INPUT;
            if (sock != null) sock.close();
            layout();
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        int cx = this.c / 2;
        this.a(this.g, "Play Multiplayer", cx, this.d / 2 - 92, 0xFFFFFF);
        if (state == INPUT) {
            this.a(this.g, "Ask the host to type /start-server and send you the code.", cx, this.d / 2 - 66, 0xA0A0A0);
            this.a(this.g, "Copy it, then press the button below (or Ctrl+V).", cx, this.d / 2 - 54, 0xA0A0A0);
            if (waitingPaste) {
                this.a(this.g, "Waiting for paste (allow clipboard access)...", cx, this.d / 2 - 20, 0xE0E0E0);
            }
            if (error != null) {
                this.a(this.g, error, cx, this.d / 2 - 20, 0xFF6060);
            }
        } else if (state == WORKING) {
            this.a(this.g, "Creating the reply code...", cx, this.d / 2 - 40, 0xE0E0E0);
        } else {
            this.a(this.g, "Send this reply code to the host:", cx, this.d / 2 - 76, 0xA0A0A0);
            int bx = cx - 100, by = this.d / 2 - 62, bw = 200, bh = 56;
            this.a(bx - 1, by - 1, bx + bw + 1, by + bh + 1, -6250336);
            this.a(bx, by, bx + bw, by + bh, -16777216);
            if (reply != null) {
                int i = 0, line = 0;
                while (i < reply.length() && line < 5) {
                    int j = Math.min(reply.length(), i + 32);
                    this.a(this.g, reply.substring(i, j), cx, by + 4 + line * 10, 0xE0E0E0);
                    i = j;
                    line++;
                }
            }
            this.a(this.g, "Waiting for the host to accept it...", cx, this.d / 2 + 4, 0xE0E0E0);
        }
        super.a(n2, n3, f2);
    }
}
