        float f5 = 0.4f;
        float f6 = TrigLookup.b(f2 * (float)Math.PI * 2.0f) - 0.0f;
        if (f6 >= -0.0f - f5 && f6 <= -0.0f + f5) {
            // Исправлено: заменен двойной минус, чтобы избежать SyntaxError в JS билде
            float f7 = (f6 + 0.0f) / f5 * 0.5f + 0.5f;
            float f8 = 1.0f - (1.0f - TrigLookup.a(f7 * (float)Math.PI)) * 0.99f;
            f8 *= f8;
            this.h[0] = f7 * 0.3f + 0.7f;
        }
        return null;
    }
}