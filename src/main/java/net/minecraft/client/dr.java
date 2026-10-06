/*
 * Decompiled with CFR 0.152.
 *
 * ПАТЧЕНО для web-порта (мультиплеер): команды чата хоста (/start-server, /invite,
 * /stop-server, /help), полупрозрачная подсказка команды (Tab — принять), повторное
 * нажатие клавиши чата при пустой строке закрывает чат, сообщения хоста уходят гостям.
 */
package net.minecraft.client;
import org.lwjgl.input.Keyboard;

public class dr
extends bp {
    private String a = "";
    private int h = 0;

    /** Команда, которую подсказываем в пустой строке (только в своём мире, не у гостя). */
    private String hint() {
        if (this.b.j()) {
            return null;
        }
        return HostServer.isActive() ? "/invite" : "/start-server";
    }

    @Override
    public void a() {
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    public void h() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void g() {
        ++this.h;
    }

    private void runCommand(String line) {
        String[] parts = line.split("\\s+");
        String cmd = parts[0].toLowerCase();
        if (cmd.equals("/start-server") || cmd.equals("/invite")) {
            if (this.b.e == null || this.b.j()) {
                this.b.u.a("\u00a7cYou can host only from your own world.");
                this.b.a((bp)null);
                return;
            }
            HostServer server = HostServer.start(this.b);
            // ИСПРАВЛЕНО (в окне приглашения не было курсора): раньше чат сначала закрывался
            // (a(null) -> захват мыши), и сразу открывалось окно (снятие захвата), пока запрос
            // pointer lock ещё не выполнился — браузер потом всё равно захватывал курсор.
            // Теперь экран просто заменяется другим экраном, без промежуточного захвата.
            if (server != null) {
                this.b.a(new InviteScreen(server));
            } else {
                this.b.a((bp)null);
            }
        } else if (cmd.equals("/stop-server")) {
            if (HostServer.isActive()) {
                HostServer.stop();
            } else {
                this.b.u.a("\u00a7cThe server is not running.");
            }
            this.b.a((bp)null);
        } else if (cmd.equals("/help")) {
            this.b.u.a("\u00a7e/start-server\u00a7f - start hosting and invite a player");
            this.b.u.a("\u00a7e/invite\u00a7f - invite one more player");
            this.b.u.a("\u00a7e/stop-server\u00a7f - stop hosting");
            this.b.a((bp)null);
        } else {
            this.b.u.a("\u00a7cUnknown command. Type /help");
            this.b.a((bp)null);
        }
    }

    @Override
    protected void a(char c2, int n2) {
        if (n2 == 1) {
            this.b.a((bp)null);
            return;
        }
        // Повторное нажатие клавиши чата при пустой строке закрывает чат
        // (Shift+T печатает заглавную "T"; не срабатывает на автоповторе сразу после открытия).
        if (n2 == this.b.y.r.b && this.a.length() == 0 && c2 != Character.toUpperCase(c2) && this.h > 8) {
            this.b.a((bp)null);
            return;
        }
        if (n2 == 28) {
            String string = this.a.trim();
            if (string.length() > 0) {
                if (string.startsWith("/") && !this.b.j()) {
                    this.runCommand(string);
                    return;
                }
                HostServer server = HostServer.get();
                if (server != null && !this.b.j()) {
                    server.hostChat(string);
                } else if (!this.b.j()) {
                    // свой мир без сервера: просто показываем сообщение себе
                    this.b.u.a("<" + (this.b.i != null ? this.b.i.b : "Player") + "> " + string);
                } else {
                    this.b.g.a(string);
                }
            }
            this.b.a((bp)null);
            return;
        }
        if (n2 == 15 || n2 == 205) {                       // Tab / стрелка вправо — принять подсказку
            String hint = this.hint();
            if (hint != null && hint.startsWith(this.a) && this.a.length() < hint.length()) {
                this.a = hint;
            }
            return;
        }
        if (n2 == 14 && this.a.length() > 0) {
            this.a = this.a.substring(0, this.a.length() - 1);
        }
        if (" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(c2) >= 0 && this.a.length() < 100) {
            this.a = this.a + c2;
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.a(2, this.d - 14, this.c - 2, this.d - 2, Integer.MIN_VALUE);
        String typed = "> " + this.a;
        this.b(this.g, typed + (this.h / 6 % 2 == 0 ? "_" : ""), 4, this.d - 12, 0xE0E0E0);
        // Полупрозрачная подсказка: остаток команды, пока введённое — её начало.
        String hint = this.hint();
        if (hint != null && hint.startsWith(this.a) && this.a.length() < hint.length()) {
            int x = 4 + this.g.a(typed) + 2;
            this.b(this.g, hint.substring(this.a.length()) + "  [Tab]", x, this.d - 12, 0x60C0C0C0);
        }
    }

    @Override
    protected void a(int n2, int n3, int n4) {
        if (n4 == 0 && this.b.u.a != null) {
            if (this.a.length() > 0 && !this.a.endsWith(" ")) {
                this.a = this.a + " ";
            }
            this.a = this.a + this.b.u.a;
            int n5 = 100;
            if (this.a.length() > n5) {
                this.a = this.a.substring(0, n5);
            }
        }
    }
}
