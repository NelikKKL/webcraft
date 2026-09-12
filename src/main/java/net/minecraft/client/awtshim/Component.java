package net.minecraft.client.awtshim;

/**
 * Минимальная замена java.awt.Component (см. PATCHES.md — почему не в
 * пакете java.awt: конфликт с системным модулем java.desktop в JDK 9+).
 *
 * Существует только для совместимости сигнатур: Minecraft.java принимает
 * Component как параметр конструктора, но никогда не вызывает на нём
 * реальных методов (проверено — единственная содержательная работа с
 * "канвасом окна" идёт через Canvas, см. Canvas.java в этом пакете).
 */
public class Component {
}
