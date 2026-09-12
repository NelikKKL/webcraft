package net.minecraft.client.netshim;

import java.io.IOException;

/**
 * Замена java.net.ConnectException — см. PATCHES.md, почему не в пакете
 * java.net (тот же JPMS-конфликт системного модуля, что и с java.awt,
 * см. PATCHES.md "Пакет net.minecraft.client.awtshim"). `java.net`
 * принадлежит модулю `java.base`, который присутствует всегда — писать
 * туда свои классы нельзя точно так же, как в java.awt/javax.imageio.
 *
 * Реальный java.net.ConnectException extends SocketException extends
 * IOException — здесь упрощено до прямого extends IOException, этого
 * достаточно для единственного места, где тип используется по имени
 * (oy.java: `catch (ConnectException e)`).
 */
public class ConnectException extends IOException {
    public ConnectException(String message) {
        super(message);
    }
}
