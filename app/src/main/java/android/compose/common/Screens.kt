package android.compose.common

sealed class Screens(val route: String) {
    object CarScreen : Screens("cars")
    object BookingsScreen : Screens("bookings")
    object NotificationsScreen : Screens("notifications")
    object SettingsScreen : Screens("settings")
    object LoginScreen : Screens("settings")
    object SignUpScreen : Screens("register")

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}