package net.minecraft.client.netshim;

/**
 * Замена java.net.InetAddress — см. PATCHES.md (тот же JPMS-конфликт с
 * java.base, что и Socket/ConnectException в этом пакете; подтверждено
 * реальной ошибкой сборки: "Class java.net.InetAddress was not found").
 *
 * Реальное DNS-разрешение не нужно: Socket-конструктор в этом пакете
 * ВСЕГДА бросает ConnectException независимо от адреса (см. Socket.java),
 * так что getByName(String) достаточно просто вернуть непустой объект,
 * не выполняя настоящего разрешения имени.
 */
public class InetAddress {
    private final String host;

    private InetAddress(String host) {
        this.host = host;
    }

    public static InetAddress getByName(String host) {
        return new InetAddress(host);
    }

    public String getHostAddress() {
        return host;
    }

    @Override
    public String toString() {
        return host;
    }
}
