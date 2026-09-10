package org.lwjgl;

/** Шим org.lwjgl.LWJGLException — реальный LWJGL кидает это исключение при ошибках инициализации Display/устройств. */
public class LWJGLException extends Exception {
    public LWJGLException() { super(); }
    public LWJGLException(String message) { super(message); }
}
