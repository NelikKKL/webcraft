package net.minecraft.client;

import net.minecraft.client.web.Mp;

/**
 * Окно хоста "Invite a player": код приглашения, кнопки Copy / Paste reply / Close.
 *
 * Порядок: хост копирует код и отправляет игроку; игрок вставляет его у себя
 * (Multiplayer), получает код-ответ и присылает хосту; хост жмёт "Paste reply".
 * После этого канал открывается и игрок входит в мир.
 */
public class InviteScreen extends bp {

    private final HostServer server;
    private final HostServer.Invite invite;
    private int tick = 0;
    private int copiedUntil = -1;
    private boolean waitingPaste = false;
    private String note = null;
    private boolean noteError = false;

    public InviteScreen(HostServer server) {
        this.server = server;
        this.invite = server.newInvite();
    }

    @Override
    public void a() {
        this.e.clear();
        int x = this.c / 2 - 100;
        this.e.add(new gh(1, x, this.d / 2 + 14, 98, 20, "Copy code"));
        this.e.add(new gh(2, x + 102, this.d / 2 + 14, 98, 20, "Paste reply"));
        this.e.add(new gh(0, x, this.d / 2 + 38, 200, 20, "Close"));
    }

    private void close() {
        if (!invite.connected && invite.playerName == null) invite.cancel();
        this.b.a((bp)null);
    }

    @Override
    protected void a(gh gh2) {
        if (!gh2.g) return;
        if (gh2.f == 0) {
            this.close();
        } else if (gh2.f == 1) {
            if (invite.code != null) {
                Mp.copy(invite.code);
                copiedUntil = tick + 50;
            }
        } else if (gh2.f == 2) {
            if (invite.code != null && !invite.connected) {
                waitingPaste = true;
                note = null;
                Mp.requestPaste();
            }
        }
    }

    @Override
    protected void a(char c2, int n2) {
        if (n2 == 1) this.close();
    }

    @Override
    public void g() {
        super.g();
        ++tick;
        if (waitingPaste) {
            String t = Mp.consumePasted();
            if (t != null) {
                waitingPaste = false;
                if (t.trim().length() > 0) {
                    server.acceptAnswer(invite, t.trim());
                    note = null;
                }
            }
        }
        // кнопки: доступность и подписи
        ((gh)this.e.get(0)).g = invite.code != null;
        ((gh)this.e.get(0)).e = tick < copiedUntil ? "Copied!" : "Copy code";
        ((gh)this.e.get(1)).g = invite.code != null && !invite.connected;
        ((gh)this.e.get(2)).e = invite.playerName != null ? "Done" : "Close";
    }

    private void wrap(String text, int width, java.util.List<String> out) {
        int i = 0;
        while (i < text.length()) {
            int j = Math.min(text.length(), i + width);
            out.add(text.substring(i, j));
            i = j;
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        int cx = this.c / 2;
        this.a(this.g, "Invite a player", cx, this.d / 2 - 92, 0xFFFFFF);

        String status;
        int color = 0xA0A0A0;
        if (invite.error != null) {
            status = invite.error;
            color = 0xFF6060;
        } else if (invite.playerName != null) {
            status = invite.playerName + " joined the game!";
            color = 0x60FF60;
        } else if (invite.connected) {
            status = "Connected, logging in...";
        } else if (invite.accepted) {
            status = "Reply accepted, connecting...";
        } else if (waitingPaste) {
            status = "Waiting for paste (allow clipboard access)...";
        } else if (invite.code == null) {
            status = "Creating the code...";
        } else {
            status = "1. Copy the code and send it to the player.";
        }
        this.a(this.g, status, cx, this.d / 2 - 76, color);
        if (invite.code != null && invite.playerName == null && !invite.connected && !invite.accepted) {
            this.a(this.g, "2. He sends back a reply code - press Paste reply.", cx, this.d / 2 - 64, 0xA0A0A0);
        }

        // Рамка с кодом
        int bx = cx - 100, by = this.d / 2 - 48, bw = 200, bh = 56;
        this.a(bx - 1, by - 1, bx + bw + 1, by + bh + 1, -6250336);
        this.a(bx, by, bx + bw, by + bh, -16777216);
        if (invite.code != null) {
            java.util.List<String> lines = new java.util.ArrayList<String>();
            wrap(invite.code, 32, lines);
            for (int i = 0; i < lines.size() && i < 5; i++) {
                this.a(this.g, lines.get(i), cx, by + 4 + i * 10, 0xE0E0E0);
            }
        } else {
            this.a(this.g, "...", cx, by + 24, 0x808080);
        }
        super.a(n2, n3, f2);
    }
}
