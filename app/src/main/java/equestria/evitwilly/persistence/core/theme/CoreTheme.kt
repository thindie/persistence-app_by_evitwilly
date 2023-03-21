package equestria.evitwilly.persistence.core.theme

import equestria.evitwilly.persistence.core.colors.CoreColors

enum class CoreTheme(
    val backgroundColor: Int,
    val cardItemBackgroundColor: Int,
    val cardItemRippleColor: Int,
    val cardItemCornerRadius: Float,
    val textColor: Int,
    val toolbarButtonRippleColor: Int,
    val toolbarButtonIconTint: Int,
    val toolbarButtonIconSize: Int,
    val toolbarButtonIconPadding: Int
) {

    LIGHT(
        backgroundColor = CoreColors.white,
        cardItemBackgroundColor = CoreColors.white,
        cardItemRippleColor = CoreColors.greenLight,
        cardItemCornerRadius = 20f,
        textColor = CoreColors.black,
        toolbarButtonRippleColor = CoreColors.greenDark,
        toolbarButtonIconTint = CoreColors.black,
        toolbarButtonIconSize = 24,
        toolbarButtonIconPadding = 8
    ),

    DARK(
        backgroundColor = CoreColors.black,
        cardItemBackgroundColor = CoreColors.gray,
        cardItemRippleColor = CoreColors.greenLight,
        cardItemCornerRadius = 20f,
        textColor = CoreColors.white,
        toolbarButtonRippleColor = CoreColors.greenDark,
        toolbarButtonIconTint = CoreColors.white,
        toolbarButtonIconSize = 24,
        toolbarButtonIconPadding = 8
    )

}