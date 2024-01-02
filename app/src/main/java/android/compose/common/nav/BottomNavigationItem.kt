package android.compose.common.nav

import android.compose.R
import android.compose.common.Screens

sealed class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
) {
    object Cars : BottomNavigationItem(
        title = "Cars",
        route = Screens.CarScreen.route,
        selectedIcon = R.drawable.car_filled,
        unselectedIcon = R.drawable.car_outlined,
        hasNews = false,
    )

    object Bookings : BottomNavigationItem(
        title = "Bookings",
        route = Screens.BookingsScreen.route,
        selectedIcon = R.drawable.book_filled,
        unselectedIcon = R.drawable.book_outlined,
        hasNews = false,
    )

    object Notifications : BottomNavigationItem(
        title = "Notifications",
        route = Screens.NotificationsScreen.route,
        selectedIcon = R.drawable.bell_filled,
        unselectedIcon = R.drawable.bell_outlined,
        hasNews = false,
    )

    object Settings : BottomNavigationItem(
        title = "Settings",
        route = Screens.SettingsScreen.route,
        selectedIcon = R.drawable.settings_filled,
        unselectedIcon = R.drawable.settings_outlined,
        hasNews = false,
    )

}


