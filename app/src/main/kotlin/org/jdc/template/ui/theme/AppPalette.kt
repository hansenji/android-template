package org.jdc.template.ui.theme

import androidx.compose.ui.graphics.Color

// Colors based on the following colors -> https://www.figma.com/community/plugin/1034969338659738588/material-theme-builder
// Primary ba1a20
// Secondary 680008
// Tertiary a88d5b
// Neutral 998e8d


object AppPalette {
    private val primaryLight = Color(0xFF904A45)
    private val onPrimaryLight = Color(0xFFFFFFFF)
    private val primaryContainerLight = Color(0xFFFFDAD6)
    private val onPrimaryContainerLight = Color(0xFF3B0908)
    private val secondaryLight = Color(0xFF904A45)
    private val onSecondaryLight = Color(0xFFFFFFFF)
    private val secondaryContainerLight = Color(0xFFFFDAD6)
    private val onSecondaryContainerLight = Color(0xFF3B0908)
    private val tertiaryLight = Color(0xFF79590C)
    private val onTertiaryLight = Color(0xFFFFFFFF)
    private val tertiaryContainerLight = Color(0xFFFFDEA4)
    private val onTertiaryContainerLight = Color(0xFF261900)
    private val errorLight = Color(0xFFBA1A1A)
    private val onErrorLight = Color(0xFFFFFFFF)
    private val errorContainerLight = Color(0xFFFFDAD6)
    private val onErrorContainerLight = Color(0xFF410002)
    private val backgroundLight = Color(0xFFFFF8F7)
    private val onBackgroundLight = Color(0xFF231918)
    private val surfaceLight = Color(0xFFFFF8F7)
    private val onSurfaceLight = Color(0xFF231918)
    private val surfaceVariantLight = Color(0xFFF5DDDB)
    private val onSurfaceVariantLight = Color(0xFF534342)
    private val outlineLight = Color(0xFF857371)
    private val outlineVariantLight = Color(0xFFD8C2BF)
    private val scrimLight = Color(0xFF000000)
    private val inverseSurfaceLight = Color(0xFF392E2D)
    private val inverseOnSurfaceLight = Color(0xFFFFEDEB)
    private val inversePrimaryLight = Color(0xFFFFB3AC)
    private val surfaceDimLight = Color(0xFFE8D6D4)
    private val surfaceBrightLight = Color(0xFFFFF8F7)
    private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
    private val surfaceContainerLowLight = Color(0xFFFFF0EF)
    private val surfaceContainerLight = Color(0xFFFCEAE8)
    private val surfaceContainerHighLight = Color(0xFFF6E4E2)
    private val surfaceContainerHighestLight = Color(0xFFF1DEDC)

    private val primaryDark = Color(0xFFFFB3AC)
    private val onPrimaryDark = Color(0xFF571E1A)
    private val primaryContainerDark = Color(0xFF73332F)
    private val onPrimaryContainerDark = Color(0xFFFFDAD6)
    private val secondaryDark = Color(0xFFFFB3AC)
    private val onSecondaryDark = Color(0xFF571E1A)
    private val secondaryContainerDark = Color(0xFF73332F)
    private val onSecondaryContainerDark = Color(0xFFFFDAD6)
    private val tertiaryDark = Color(0xFFECC06C)
    private val onTertiaryDark = Color(0xFF412D00)
    private val tertiaryContainerDark = Color(0xFF5D4200)
    private val onTertiaryContainerDark = Color(0xFFFFDEA4)
    private val errorDark = Color(0xFFFFB4AB)
    private val onErrorDark = Color(0xFF690005)
    private val errorContainerDark = Color(0xFF93000A)
    private val onErrorContainerDark = Color(0xFFFFDAD6)
    private val backgroundDark = Color(0xFF1A1110)
    private val onBackgroundDark = Color(0xFFF1DEDC)
    private val surfaceDark = Color(0xFF1A1110)
    private val onSurfaceDark = Color(0xFFF1DEDC)
    private val surfaceVariantDark = Color(0xFF534342)
    private val onSurfaceVariantDark = Color(0xFFD8C2BF)
    private val outlineDark = Color(0xFFA08C8A)
    private val outlineVariantDark = Color(0xFF534342)
    private val scrimDark = Color(0xFF000000)
    private val inverseSurfaceDark = Color(0xFFF1DEDC)
    private val inverseOnSurfaceDark = Color(0xFF392E2D)
    private val inversePrimaryDark = Color(0xFF904A45)
    private val surfaceDimDark = Color(0xFF1A1110)
    private val surfaceBrightDark = Color(0xFF423735)
    private val surfaceContainerLowestDark = Color(0xFF140C0B)
    private val surfaceContainerLowDark = Color(0xFF231918)
    private val surfaceContainerDark = Color(0xFF271D1C)
    private val surfaceContainerHighDark = Color(0xFF322826)
    private val surfaceContainerHighestDark = Color(0xFF3D3231)

    fun lightColors(): AppColors {
        return AppColors(
            primary = primaryLight,
            onPrimary = onPrimaryLight,
            primaryContainer = primaryContainerLight,
            onPrimaryContainer = onPrimaryContainerLight,
            secondary = secondaryLight,
            onSecondary = onSecondaryLight,
            secondaryContainer = secondaryContainerLight,
            onSecondaryContainer = onSecondaryContainerLight,
            tertiary = tertiaryLight,
            onTertiary = onTertiaryLight,
            tertiaryContainer = tertiaryContainerLight,
            onTertiaryContainer = onTertiaryContainerLight,
            error = errorLight,
            onError = onErrorLight,
            errorContainer = errorContainerLight,
            onErrorContainer = onErrorContainerLight,
            background = backgroundLight,
            onBackground = onBackgroundLight,
            surface = surfaceLight,
            onSurface = onSurfaceLight,
            surfaceVariant = surfaceVariantLight,
            onSurfaceVariant = onSurfaceVariantLight,
            outline = outlineLight,
            outlineVariant = outlineVariantLight,
            scrim = scrimLight,
            inverseSurface = inverseSurfaceLight,
            inverseOnSurface = inverseOnSurfaceLight,
            inversePrimary = inversePrimaryLight,
            surfaceDim = surfaceDimLight,
            surfaceBright = surfaceBrightLight,
            surfaceContainerLowest = surfaceContainerLowestLight,
            surfaceContainerLow = surfaceContainerLowLight,
            surfaceContainer = surfaceContainerLight,
            surfaceContainerHigh = surfaceContainerHighLight,
            surfaceContainerHighest = surfaceContainerHighestLight,
        )
    }

    fun darkColors(): AppColors {
        return AppColors(
            primary = primaryDark,
            onPrimary = onPrimaryDark,
            primaryContainer = primaryContainerDark,
            onPrimaryContainer = onPrimaryContainerDark,
            secondary = secondaryDark,
            onSecondary = onSecondaryDark,
            secondaryContainer = secondaryContainerDark,
            onSecondaryContainer = onSecondaryContainerDark,
            tertiary = tertiaryDark,
            onTertiary = onTertiaryDark,
            tertiaryContainer = tertiaryContainerDark,
            onTertiaryContainer = onTertiaryContainerDark,
            error = errorDark,
            onError = onErrorDark,
            errorContainer = errorContainerDark,
            onErrorContainer = onErrorContainerDark,
            background = backgroundDark,
            onBackground = onBackgroundDark,
            surface = surfaceDark,
            onSurface = onSurfaceDark,
            surfaceVariant = surfaceVariantDark,
            onSurfaceVariant = onSurfaceVariantDark,
            outline = outlineDark,
            outlineVariant = outlineVariantDark,
            scrim = scrimDark,
            inverseSurface = inverseSurfaceDark,
            inverseOnSurface = inverseOnSurfaceDark,
            inversePrimary = inversePrimaryDark,
            surfaceDim = surfaceDimDark,
            surfaceBright = surfaceBrightDark,
            surfaceContainerLowest = surfaceContainerLowestDark,
            surfaceContainerLow = surfaceContainerLowDark,
            surfaceContainer = surfaceContainerDark,
            surfaceContainerHigh = surfaceContainerHighDark,
            surfaceContainerHighest = surfaceContainerHighestDark,
        )
    }
}