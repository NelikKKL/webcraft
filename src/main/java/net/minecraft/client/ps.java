/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ps
extends Player {
    private int b;
    private double c;
    private double d;
    private double bx;
    private double by;
    private double bz;
    float a = 0.0f;
    /**
     * true — это сущность гостя в мире ХОСТА (HostServer): она получает урон от мобов и падений.
     * false — обычный удалённый игрок на клиенте (неуязвимый, как в оригинале).
     */
    public boolean takesDamage = false;
    /** Скин этого игрока (из пакета 200): 64x64-раскладка и тонкие руки — для модели; сам файл — в этом пути. */
    public boolean skinModern = false;
    public boolean skinSlim = false;

    public ps(Session cy2, String string) {
        super(cy2);
        this.name = string;
        this.aO = 0.0f;
        this.aZ = 0.0f;
        // Скины minecraft.net в браузере недоступны — скин приходит пакетом 200 (SkinPacket), путь в this.z.
        this.bl = null;
        this.ba = true;
        this.ao = 10.0;
    }

    @Override
    public boolean a(lw lw2, int n2) {
        return this.takesDamage ? super.a(lw2, n2) : true;
    }

    @Override
    public void a(double d2, double d3, double d4, float f2, float f3, int n2) {
        this.aO = 0.0f;
        this.c = d2;
        this.d = d3;
        this.bx = d4;
        this.by = f2;
        this.bz = f3;
        this.b = n2;
    }

    @Override
    public void e_() {
        super.e_();
        this.V = this.W;
        double d2 = this.aw - this.at;
        double d3 = this.ay - this.av;
        float f2 = TrigLookup.a(d2 * d2 + d3 * d3) * 4.0f;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        this.W += (f2 - this.W) * 0.4f;
        this.X += this.W;
    }

    /**
     * Смещение "глаз" для стрел/снежков (Player.v() = 0.12 отсчитывается от высоты ГЛАЗ, а у ps позиция —
     * на уровне ног). Для сущности гостя в мире хоста добавляем рост игрока, иначе стрела вылетала бы из ног.
     * (i_() не трогаем: он нужен для высоты тени.)
     */
    @Override
    public float v() {
        return this.takesDamage ? 1.62f + 0.12f : super.v();
    }

    @Override
    public void k() {
        super.b_();
        if (this.b > 0) {
            double d2;
            double d3 = this.aw + (this.c - this.aw) / (double)this.b;
            double d4 = this.ax + (this.d - this.ax) / (double)this.b;
            double d5 = this.ay + (this.bx - this.ay) / (double)this.b;
            for (d2 = this.by - (double)this.aC; d2 < -180.0; d2 += 360.0) {
            }
            while (d2 >= 180.0) {
                d2 -= 360.0;
            }
            this.aC = (float)((double)this.aC + d2 / (double)this.b);
            this.aD = (float)((double)this.aD + (this.bz - (double)this.aD) / (double)this.b);
            --this.b;
            this.b(d3, d4, d5);
            this.c(this.aC, this.aD);
        }
        this.h = this.i;
        float f2 = TrigLookup.a(this.az * this.az + this.aB * this.aB);
        float f3 = (float)Math.atan(-this.aA * (double)0.2f) * 15.0f;
        if (f2 > 0.1f) {
            f2 = 0.1f;
        }
        if (!this.aH || this.J <= 0) {
            f2 = 0.0f;
        }
        if (this.aH || this.J <= 0) {
            f3 = 0.0f;
        }
        this.i += (f2 - this.i) * 0.4f;
        this.R += (f3 - this.R) * 0.8f;
    }

    @Override
    public boolean p() {
        return this.bw;
    }
}

