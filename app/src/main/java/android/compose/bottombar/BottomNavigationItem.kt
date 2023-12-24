package android.compose.bottombar

import android.compose.R

open class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
) {
    object Cars : BottomNavigationItem(
        title = "Cars",
        route = "cars",
        selectedIcon = R.drawable.car_filled,
        unselectedIcon = R.drawable.car_outlined,
        hasNews = false,
    )

    object Bookings : BottomNavigationItem(
        title = "Bookings",
        route = "bookings",
        selectedIcon = R.drawable.book_filled,
        unselectedIcon = R.drawable.book_outlined,
        hasNews = false,
    )

    object Notifications : BottomNavigationItem(
        title = "Notifications",
        route = "notifications",
        selectedIcon = R.drawable.bell_filled,
        unselectedIcon = R.drawable.bell_outlined,
        hasNews = false,
    )

    object Settings : BottomNavigationItem(
        title = "Settings",
        route = "settings",
        selectedIcon = R.drawable.settings_filled,
        unselectedIcon = R.drawable.settings_outlined,
        hasNews = false,
    )

}


