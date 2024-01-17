package android.compose.common

sealed class Screens(val route: String) {
    data object CarScreen : Screens("cars")
    data object BookingsScreen : Screens("bookings")
    data object NotificationsScreen : Screens("notifications")
    data object Profile : Screens("profile")
    data object LoginScreen : Screens("login")
    data object DamageFormScreen : Screens("damageForm")
    data object FaqScreen : Screens("faq")
    data object RegisterScreen : Screens("register")

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}