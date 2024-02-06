package android.compose.common.nav

import android.compose.R
import android.compose.common.Screens

enum class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
) {
    Cars(
        title = "Cars",
        route = Screens.CarScreen.route,
        selectedIcon = R.drawable.car_filled,
        unselectedIcon = R.drawable.car_outlined,
        hasNews = false,
    ),

    Bookings(
        title = "Bookings",
        route = Screens.BookingsScreen.route,
        selectedIcon = R.drawable.book_filled,
        unselectedIcon = R.drawable.book_outlined,
        hasNews = false,
    ),

    Favorites(
        title = "Favorites",
        route = Screens.FavoritesScreen.route,
        selectedIcon = R.drawable.star_filled,
        unselectedIcon = R.drawable.star_outlined,
        hasNews = false,
    ),

    Profile(
        title = "Profile",
        route = Screens.Profile.route,
        selectedIcon = R.drawable.account_circle,
        unselectedIcon = R.drawable.outline_account_circle_24,
        hasNews = false,
    )

}


