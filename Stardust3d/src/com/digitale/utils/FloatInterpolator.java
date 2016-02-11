package com.digitale.utils;

public class FloatInterpolator {

public static float interpolate(float a, float b, float t) {
return interpolate(a, b, t, InterpolationFunctions.linear());
}

public static float interpolate(float a, float b, float t, InterpolationFunction function) {
t = function.interpolate(t);
return a * (1 - t) + b * t;
}

}