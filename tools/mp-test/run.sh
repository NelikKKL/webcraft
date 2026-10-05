#!/bin/sh
# Проверка мультиплеера на обычной JVM (без браузера и TeaVM).
#   - PktTest: все пакеты, которые строит HostServer, проходят через родные read/write классы игры;
#     пакет чанка (сжатый Zlib.java) разворачивается родным Inflater'ом клиента.
#   - MpIntegration: настоящий мир (генерация) + HostServer + "гость" на подменённом сокете:
#     логин, чанки, блоки в обе стороны, чат, игроки, мобы, предметы, выход.
# Запуск из корня проекта:  sh tools/mp-test/run.sh
set -e
# javac может быть недоступен как команда (только модуль jdk.compiler) — тогда используем его напрямую
if command -v javac >/dev/null 2>&1; then JAVAC=javac; else JAVAC="java -m jdk.compiler/com.sun.tools.javac.Main"; fi
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
OUT="$(mktemp -d)"
# заглушки для TeaVM JSO (нужны только для компиляции вне TeaVM)
STUBS="$OUT/jso"; mkdir -p "$STUBS/org/teavm/jso/typedarrays" "$STUBS/org/teavm/jso/dom/html" "$STUBS/org/teavm/jso/canvas" "$STUBS/org/teavm/jso/webgl"
cd "$STUBS/org/teavm/jso"
echo 'package org.teavm.jso; public interface JSObject {}' > JSObject.java
echo 'package org.teavm.jso; import java.lang.annotation.*; @Retention(RetentionPolicy.RUNTIME) public @interface JSBody { String[] params() default {}; String script() default ""; }' > JSBody.java
echo 'package org.teavm.jso; import java.lang.annotation.*; @Retention(RetentionPolicy.RUNTIME) public @interface JSMethod { String value() default ""; }' > JSMethod.java
echo 'package org.teavm.jso; import java.lang.annotation.*; @Retention(RetentionPolicy.RUNTIME) public @interface JSFunctor {}' > JSFunctor.java
echo 'package org.teavm.jso.typedarrays; public class ArrayBuffer implements org.teavm.jso.JSObject { public ArrayBuffer(int n){} }' > typedarrays/ArrayBuffer.java
echo 'package org.teavm.jso.typedarrays; public class Float32Array implements org.teavm.jso.JSObject { public Float32Array(ArrayBuffer b){} public void set(float[] a,int o){} public int getLength(){return 0;} public float get(int i){return 0;} }' > typedarrays/Float32Array.java
echo 'package org.teavm.jso.typedarrays; public class Uint8Array implements org.teavm.jso.JSObject { public Uint8Array(ArrayBuffer b){} public void set(byte[] a,int o){} public int getLength(){return 0;} public int get(int i){return 0;} }' > typedarrays/Uint8Array.java
echo 'package org.teavm.jso.dom.html; public interface HTMLCanvasElement extends org.teavm.jso.JSObject {}' > dom/html/HTMLCanvasElement.java
echo 'package org.teavm.jso.canvas; public interface CanvasRenderingContext2D extends org.teavm.jso.JSObject {}' > canvas/CanvasRenderingContext2D.java
echo 'package org.teavm.jso.webgl; public interface WebGLRenderingContext extends org.teavm.jso.JSObject {}' > webgl/WebGLRenderingContext.java
cd "$ROOT"
find src/main/java -name '*.java' > "$OUT/srcs.txt"
mkdir -p "$OUT/game" "$OUT/stubs" "$OUT/tests"
$JAVAC -nowarn -proc:none -d "$OUT/game" -sourcepath "$STUBS" @"$OUT/srcs.txt" 2>&1 | grep -v '^Note:' || true
$JAVAC -nowarn -proc:none -d "$OUT/stubs" tools/mp-test/stubs/net/minecraft/client/web/SkinStore.java
$JAVAC -nowarn -proc:none -cp "$OUT/game" -d "$OUT/tests" tools/mp-test/net/minecraft/client/*.java 2>&1 | grep -v '^Note:' || true
CP="$OUT/stubs:$OUT/game:$OUT/tests"
echo "== PktTest =="; java -cp "$CP" net.minecraft.client.PktTest
echo "== MpIntegration =="; java -cp "$CP" net.minecraft.client.MpIntegration 2>&1 | grep -v 'Loading texture\|Player count'
