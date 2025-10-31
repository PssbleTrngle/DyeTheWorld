package com.possible_triangle.dye_the_world

import net.minecraft.util.FastColor
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cbrt
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Converted to Kotlin from Java using the
 * following source: [Biomancy](https://github.com/Elenterius/Biomancy/blob/mc1.20.1/dev/src/main/java/com/github/elenterius/biomancy/util/colors/ColorSpaces.java)
 *
 * License: MIT
 *
 * Implemented based on:
 * - [Oklab](https://bottosson.github.io/posts/oklab) by Björn Ottosson
 * - [W3C Color Module 4 - OKLab](https://www.w3.org/TR/css-color-4/#ok-lab)
 */
object ColorSpaces {

    private fun cube(x: Double): Double {
        return x * x * x
    }

    private fun cubeRoot(value: Double): Double {
        return cbrt(value)
    }

    private fun clamp(value: Double, min: Double, max: Double): Double {
        return min(max, max(value, min))
    }

    private fun clamp(value: Int, min: Int, max: Int): Int {
        return min(max, max(value, min))
    }

    private fun floor(value: Double): Int {
        val i = value.toInt()
        return if (value < i.toDouble()) i - 1 else i
    }

    /**
     * constrains angle in degrees to [0..360]
     */
    private fun constrainAngleDeg(angleDegrees: Double): Double {
        return ((angleDegrees % 360.0) + 360.0) % 360.0
    }

    object HSL {
        /**
         * @return s and l in the range of [0.0, 1.0]
         */
        fun fromARGB32(rgb: Int): DoubleArray {
            val r = FastColor.ARGB32.red(rgb) / 255.0
            val g = FastColor.ARGB32.green(rgb) / 255.0
            val b = FastColor.ARGB32.blue(rgb) / 255.0

            val min = min(r, min(g, b))
            val max = max(r, max(g, b))
            val diff = max - min

            val hue: Double
            val saturation: Double
            val luminance = (max + min) / 2.0

            if (diff == 0.0) {
                hue = 0.0
                saturation = 0.0
            } else {
                if (max == r) hue = (60.0 * (g - b) / diff + 360) % 360.0
                else if (max == g) hue = 60.0 * (b - r) / diff + 120.0
                else hue = 60.0 * (r - g) / diff + 240.0

                saturation = if (luminance <= 0.5) (max - min) / (max + min) else (max - min) / (2 - max - min)
            }

            return doubleArrayOf(hue, saturation, luminance)
        }

        /**
         * expects s and l in the range of [0.0, 1.0]
         */
        fun toARGB32(hsl: DoubleArray): Int {
            return toARGB32(hsl[0], hsl[1], hsl[2])
        }

        /**
         * expects s and l in the range of [0.0, 1.0]
         */
        fun toARGB32(h: Double, s: Double, l: Double): Int {
            var h = h
            h = (h % 360.0) / 360.0

            val r: Double
            val g: Double
            val b: Double

            if (s == 0.0) {
                r = l
                g = l
                b = l
            } else {
                val q = if (l < 0.5) l * (1.0 + s) else l + s - l * s
                val p = 2.0 * l - q

                r = hueToRgb(p, q, h + 1.0 / 3.0)
                g = hueToRgb(p, q, h)
                b = hueToRgb(p, q, h - 1.0 / 3.0)
            }

            return FastColor.ARGB32.color(
                255,
                clamp(floor(r * 255.0), 0, 255),
                clamp(floor(g * 255.0), 0, 255),
                clamp(floor(b * 255.0), 0, 255)
            )
        }

        private fun hueToRgb(p: Double, q: Double, h: Double): Double {
            var h = h
            if (h < 0.0) h += 1.0
            if (h > 1.0) h -= 1.0

            if (h < 1.0 / 6.0) return p + (q - p) * 6.0 * h
            if (h < 1.0 / 2.0) return q
            if (h < 2.0 / 3.0) return p + (q - p) * 6.0 * (2.0 / 3.0 - h)

            return p
        }
    }

    object sRGB {
        /**
         * Test if sRGB values are in range [0.0, 1.0]
         */
        fun isInGamut(linearRGB: DoubleArray): Boolean {
            val r = linearRGB[0]
            val g = linearRGB[1]
            val b = linearRGB[2]

            return r >= 0.0 && r <= 1.0 && g >= 0.0 && g <= 1.0 && b >= 0.0 && b <= 1.0
        }

        /**
         * Convert in-gamut sRGB values in range [0.0, 1.0] to linear light form
         *
         * @return linear light sRGB
         */
        fun toLinearLight(colorValue: Double): Double {
            val sign = if (colorValue < 0.0) -1.0 else 1.0
            val abs = abs(colorValue)

            if (abs <= 0.04045) {
                return colorValue / 12.92
            }

            return sign * ((abs + 0.055) / 1.055).pow(2.4)
        }

        /**
         * @return linear light sRGB
         */
        fun toLinearLight(rgb: DoubleArray): DoubleArray {
            return doubleArrayOf(
                toLinearLight(rgb[0]),
                toLinearLight(rgb[1]),
                toLinearLight(rgb[2]),
            )
        }

        /**
         * Convert linear light sRGB in the range [0.0, 1.0] to gamma corrected form
         *
         * @return gamma corrected sRGB
         * @see [SRGB](https://en.wikipedia.org/wiki/SRGB)
         */
        fun gammaFromLinear(colorValue: Double): Double {
            val sign = if (colorValue < 0.0) -1.0 else 1.0
            val abs = abs(colorValue)

            if (abs > 0.0031308) {
                return sign * (1.055 * abs.pow(1.0 / 2.4) - 0.055)
            }

            return 12.92 * colorValue
        }

        /**
         * @return gamma corrected sRGB
         */
        fun gammaFromLinear(linearRGB: DoubleArray): DoubleArray {
            return doubleArrayOf(
                gammaFromLinear(linearRGB[0]),
                gammaFromLinear(linearRGB[1]),
                gammaFromLinear(linearRGB[2]),
            )
        }

        /**
         * @return sRGB
         */
        fun fromARGB32(argb: Int): DoubleArray {
            return doubleArrayOf(
                FastColor.ARGB32.red(argb) / 255.0,
                FastColor.ARGB32.green(argb) / 255.0,
                FastColor.ARGB32.blue(argb) / 255.0
            )
        }

        /**
         * @return ARGB 32
         */
        fun toARGB32(rgb: DoubleArray): Int {
            return FastColor.ARGB32.color(
                255,
                clamp((rgb[0] * 255.0).toInt(), 0, 255),
                clamp((rgb[1] * 255.0).toInt(), 0, 255),
                clamp((rgb[2] * 255.0).toInt(), 0, 255)
            )
        }
    }

    /**
     * @see [Oklab](https://bottosson.github.io/posts/oklab)
     */
    object OkLab {

        /**
         * Convert linear light sRGB [0..1] to OKLab
         * @return Lab (Lightness, a, b)
         */
        fun fromLinearSRGB(rLinear: Double, gLinear: Double, bLinear: Double): DoubleArray {
            val cubeRootL = cubeRoot(0.4122214708 * rLinear + 0.5363325363 * gLinear + 0.0514459929 * bLinear)
            val cubeRootM = cubeRoot(0.2119034982 * rLinear + 0.6806995451 * gLinear + 0.1073969566 * bLinear)
            val cubeRootS = cubeRoot(0.0883024619 * rLinear + 0.2817188376 * gLinear + 0.6299787005 * bLinear)

            val L = 0.2104542683093140 * cubeRootL + 0.7936177747023054 * cubeRootM - 0.0040720430116193 * cubeRootS
            val a = 1.9779985324311684 * cubeRootL - 2.4285922420485799 * cubeRootM + 0.4505937096174110 * cubeRootS
            val b = 0.0259040424655478 * cubeRootL + 0.7827717124575296 * cubeRootM - 0.8086757549230774 * cubeRootS

            return doubleArrayOf(L, a, b)
        }

        /**
         * @return Lab (Lightness, a, b)
         */
        fun fromSRGB(r: Double, g: Double, b: Double): DoubleArray {
            return fromLinearSRGB(sRGB.toLinearLight(r), sRGB.toLinearLight(g), sRGB.toLinearLight(b))
        }

        /**
         * @return Lab (Lightness, a, b)
         */
        fun fromSRGB(rgb: DoubleArray): DoubleArray {
            return fromSRGB(rgb[0], rgb[1], rgb[2])
        }

        /**
         * @return Lab (Lightness, a, b)
         */
        fun fromARGB32(rgb: Int): DoubleArray {
            return fromSRGB(sRGB.fromARGB32(rgb))
        }

        /**
         * @return linear sRGB (r, g, b)
         */
        fun toLinearSRGB(Lab: DoubleArray): DoubleArray {
            return toLinearSRGB(Lab[0], Lab[1], Lab[2])
        }

        /**
         * @param L Lightness from 0.0 to 1.0
         * @param a from -1.0 to 1.0
         * @param b from -1.0 to 1.0
         * @return linear-light sRGB (r, g, b)
         */
        fun toLinearSRGB(L: Double, a: Double, b: Double): DoubleArray {
            val l = cube(L + 0.3963377773761749 * a + 0.2158037573099136 * b)
            val m = cube(L - 0.1055613458156586 * a - 0.0638541728258133 * b)
            val s = cube(L - 0.0894841775298119 * a - 1.2914855480194092 * b)

            return doubleArrayOf(
                4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s,
                -1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s,
                -0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s,
            )
        }

        /**
         * @return sRGB (r, g, b)
         */
        fun toSRGB(Lab: DoubleArray): DoubleArray {
            val linearRGB: DoubleArray = toLinearSRGB(Lab)
            return sRGB.gammaFromLinear(linearRGB)
        }

        /**
         * @return ARGB (alpha, r, g, b)
         */
        fun toARGB32(Lab: DoubleArray): Int {
            return sRGB.toARGB32(toSRGB(Lab))
        }

        /**
         * @return LCh (Lightness, Chroma, hue)
         */
        fun toOkLCh(Lab: DoubleArray): DoubleArray {
            val Lightness = Lab[0] // 0.0 - 1.0
            val a = Lab[1] // -1.0 - 1.0
            val b = Lab[2] // -1.0 - 1.0
            //            double Chroma = Math.min(Math.hypot(a, b), 0.37); // 0.0 - 0.37 // hypot = sqrt(a * a + b * b)
            val Chroma = hypot(a, b) // 0.0 - 0.37 // hypot = sqrt(a * a + b * b)
            val hue = constrainAngleDeg(Math.toDegrees(atan2(b, a))) // 0.0 - 360.0

            return doubleArrayOf(Lightness, Chroma, hue)
        }

        /**
         * Calculate difference (i.e. Euclidean distance) between color sample and reference
         *
         * @param reference reference OKLab color
         * @param sample    sample  OKLab color
         * @return deltaE OK
         */
        fun deltaEOK(reference: DoubleArray, sample: DoubleArray): Double {
            val dL = reference[0] - sample[0]
            val da = reference[1] - sample[1]
            val db = reference[2] - sample[2]
            return sqrt(dL * dL + da * da + db * db)
        }

        /**
         * Clip OKLab color to the sRGB gamut.
         *
         * @return clipped OKLab color
         */
        fun clipToSRGBGamut(Lab: DoubleArray): DoubleArray {
            val linearRGB: DoubleArray = toLinearSRGB(Lab)
            return fromLinearSRGB(
                clamp(linearRGB[0], 0.0, 1.0),
                clamp(linearRGB[1], 0.0, 1.0),
                clamp(linearRGB[2], 0.0, 1.0)
            )
        }
    }

    /**
     * Oklch is the cylindrical representation of Oklab
     */
    object OkLCh {

        /**
         * @return LCh
         * <br></br>(
         * <br></br> Lightness: 0.0 - 1.0
         * <br></br> Chroma: 0.0 - 0.37
         * <br></br> hue: 0.0 - 360.0 )
         */
        fun fromLinearSRGB(rLinear: Double, gLinear: Double, bLinear: Double): DoubleArray {
            return OkLab.toOkLCh(OkLab.fromLinearSRGB(rLinear, gLinear, bLinear))
        }

        /**
         * @return LCh (Lightness, Chroma, hue)
         */
        fun fromSRGB(r: Double, g: Double, b: Double): DoubleArray {
            return fromLinearSRGB(sRGB.toLinearLight(r), sRGB.toLinearLight(g), sRGB.toLinearLight(b))
        }

        /**
         * @return LCh (Lightness, Chroma, hue)
         */
        fun fromSRGB(rgb: DoubleArray): DoubleArray {
            return fromSRGB(rgb[0], rgb[1], rgb[2])
        }

        /**
         * @return LCh (Lightness, Chroma, hue)
         */
        fun fromARGB32(rgb: Int): DoubleArray {
            return fromSRGB(sRGB.fromARGB32(rgb))
        }

        /**
         * @return linear sRGB (r, g, b)
         */
        fun toLinearSRGB(LCh: DoubleArray): DoubleArray {
            return OkLab.toLinearSRGB(toOkLab(LCh))
        }

        /**
         * @return sRGB (r, g, b)
         */
        fun toSRGB(LCh: DoubleArray): DoubleArray {
            val linearRGB: DoubleArray = toLinearSRGB(LCh)
            return sRGB.gammaFromLinear(linearRGB)
        }

        /**
         * @return aRGB (r, g, b)
         */
        fun toARGB32(LCh: DoubleArray): Int {
            return sRGB.toARGB32(toSRGB(LCh))
        }

        /**
         * @return Lab (Lightness, a, b)
         */
        fun toOkLab(LCh: DoubleArray): DoubleArray {
            return toOkLab(LCh[0], LCh[1], LCh[2])
        }

        /**
         * @return Lab (Lightness, a, b)
         */
        fun toOkLab(L: Double, C: Double, h: Double): DoubleArray {
            val hue = Math.toRadians(h)
            val a = C * cos(hue)
            val b = C * sin(hue)

            return doubleArrayOf(L, a, b)
        }

        fun gamutMapToARGB32(LCh: DoubleArray): Int {
            return sRGB.toARGB32(gamutMapToSRGB(LCh))
        }

        /**
         * Binary Search Gamut Mapping Algorithm with Local MINDE
         *
         * @param LCh OKLCh color
         * @return color mapped to the gamut of sRGB
         * @see [OKLab Gamut Mapping](https://www.w3.org/TR/css-color-4/#css-gamut-mapping)
         */
        fun gamutMapToSRGB(LCh: DoubleArray): DoubleArray {
            val L = LCh[0]

            if (L >= 1.0) return OkLab.toSRGB(doubleArrayOf(1.0, 0.0, 0.0))
            if (L <= 0.0) return OkLab.toSRGB(doubleArrayOf(0.0, 0.0, 0.0))

            val linearRGB: DoubleArray = toLinearSRGB(LCh)
            if (sRGB.isInGamut(linearRGB)) {
                return sRGB.gammaFromLinear(linearRGB)
            }

            val JND = 0.02
            val epsilon = 1.0E-04

            val currentLab: DoubleArray = toOkLab(LCh)
            var clippedLab: DoubleArray = OkLab.clipToSRGBGamut(currentLab)
            var E: Double = OkLab.deltaEOK(clippedLab, currentLab)

            if (E < JND) return OkLab.toSRGB(clippedLab)

            var min = 0.0
            var max = LCh[1] // chroma
            var minInGamut = true

            val hRad = Math.toRadians(LCh[2])
            val cosHue = cos(hRad)
            val sinHue = sin(hRad)

            while (max - min > epsilon) {
                val chroma = (min + max) / 2.0

                currentLab[1] = chroma * cosHue
                currentLab[2] = chroma * sinHue

                if (minInGamut && sRGB.isInGamut(OkLab.toLinearSRGB(currentLab))) {
                    min = chroma
                    continue
                }

                clippedLab = OkLab.clipToSRGBGamut(currentLab)
                E = OkLab.deltaEOK(clippedLab, currentLab)

                if (E < JND) {
                    if (JND - E < epsilon) return OkLab.toSRGB(clippedLab)
                    else {
                        minInGamut = false
                        min = chroma
                    }
                } else {
                    max = chroma
                }
            }

            return OkLab.toSRGB(clippedLab)
        }
    }

}