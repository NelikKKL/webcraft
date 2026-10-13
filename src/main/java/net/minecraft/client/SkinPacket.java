package net.minecraft.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Пакет 200 (расширение этого порта; в протоколе Alpha 1.2.6 такого нет): скин игрока.
 *
 * Alpha брала скины с сервера minecraft.net по нику, чего в браузере нет, поэтому игроки обмениваются
 * скинами сами: каждый клиент после входа отправляет хосту свой PNG, хост применяет его к сущности гостя
 * в своём мире и рассылает остальным; хост отправляет свой скин гостям при входе.
 *
 *   a — id сущности владельца; b — 1, если у скина тонкие руки (Alex); c — файл PNG как есть (до 200 КБ).
 */
public class SkinPacket extends gk {

    public static final int MAX_BYTES = 200000;

    public int a;
    public byte b;
    public byte[] c;

    public SkinPacket() {
    }

    public SkinPacket(int entityId, boolean slim, byte[] png) {
        this.a = entityId;
        this.b = (byte)(slim ? 1 : 0);
        this.c = png;
    }

    @Override
    public void a(DataInputStream in) {
        try {
            this.a = in.readInt();
            this.b = in.readByte();
            int n = in.readInt();
            if (n < 0 || n > MAX_BYTES) {
                this.c = null;
                in.skipBytes(Math.max(n, 0));
                return;
            }
            this.c = new byte[n];
            in.readFully(this.c);
        } catch (IOException e) {
            this.c = null;
        }
    }

    @Override
    public void a(DataOutputStream out) {
        try {
            out.writeInt(this.a);
            out.writeByte(this.b);
            int n = this.c == null ? 0 : this.c.length;
            out.writeInt(n);
            if (n > 0) {
                out.write(this.c, 0, n);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void a(mo handler) {
        handler.a(this);
    }

    @Override
    public int a() {
        return 9 + (this.c == null ? 0 : this.c.length);
    }
}
