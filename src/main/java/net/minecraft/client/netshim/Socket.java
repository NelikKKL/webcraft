package net.minecraft.client.netshim;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;

/**
 * Замена java.net.Socket — см. PATCHES.md (JPMS-конфликт с java.base,
 * та же причина, что и ConnectException в этом пакете).
 *
 * Браузер физически не может открыть сырой TCP-сокет — конструктор
 * ВСЕГДА бросает ConnectException, что семантически корректно (а не
 * компромисс): любая попытка подключения к серверу мультиплеера в
 * текущей версии веб-порта ДОЛЖНА провалиться предсказуемо, ровно как
 * если бы сервер был недоступен. Реальная поддержка мультиплеера
 * потребует WebSocket-прокси на бэкенде (см. TODO.md) — отдельная,
 * не начатая подсистема.
 *
 * Остальные методы (getInputStream/getOutputStream/getRemoteSocketAddress/
 * setTrafficClass/close) существуют только ради компилируемости кода,
 * который получает уже сконструированный Socket как параметр (см.
 * jq.java) — физически недостижимы, т.к. конструктор всегда бросает.
 */
public class Socket {

    public Socket(InetAddress address, int port) throws ConnectException {
        throw new ConnectException("Multiplayer is not yet supported in the web version");
    }

    public SocketAddress getRemoteSocketAddress() {
        return null;
    }

    public void setTrafficClass(int tc) {
    }

    public InputStream getInputStream() {
        return null;
    }

    public OutputStream getOutputStream() {
        return null;
    }

    public void close() {
    }
}
