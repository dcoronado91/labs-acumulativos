package com.example.lab8moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab8moviles.navigation.Characters
import com.example.lab8moviles.navigation.CharacterDetail
import com.example.lab8moviles.navigation.Login
import com.example.lab8moviles.ui.characterdetail.CharacterDetailScreen
import com.example.lab8moviles.ui.characters.CharactersScreen
import com.example.lab8moviles.ui.login.LoginScreen
import com.example.lab8moviles.ui.theme.Lab8MovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab8MovilesTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onStart = {
                    navController.navigate(Characters) {
                        popUpTo<Login> { inclusive = true }
                    }
                },
                logoUrl = "https://1000logos.net/wp-content/uploads/2022/03/Rick-and-Morty.png"
            )
        }
        composable<Characters> {
            CharactersScreen(
                onCharacterClick = { id ->
                    navController.navigate(CharacterDetail(id))
                }
            )
        }
        composable<CharacterDetail> { backStackEntry ->
            val detail = backStackEntry.toRoute<CharacterDetail>()
            CharacterDetailScreen(
                characterId = detail.characterId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}