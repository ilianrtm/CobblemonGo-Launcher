package dev.iln.cobblemongo.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.login_logo
import dev.iln.cobblemongo.resources.logo
import dev.iln.cobblemongo.ui.navigation.LocalNavigator
import dev.iln.cobblemongo.ui.navigation.Screen
import dev.iln.cobblemongo.ui.screen.login.components.LoginCard
import dev.iln.cobblemongo.ui.screen.login.components.LoginFooter
import dev.iln.cobblemongo.ui.screen.main.components.LandscapeLevel
import dev.iln.cobblemongo.ui.screen.main.components.hillsHeight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen() {
    val navigator = LocalNavigator.current
    val model = remember { LoginModel() }


    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = stringResource(Res.string.login_logo),
                modifier = Modifier
                    .weight(1f, fill = false)
                    .size(290.dp, 178.dp)
                    .overlapBelow(14.dp)
                    .zIndex(1f)
            )

            LoginCard(
                model = model,
                onLogin = { if (model.login()) navigator.replace(Screen.Home) }
            )
        }


        Spacer(Modifier.height(LandscapeLevel.Low.hillsHeight + 24.dp))

        LoginFooter()
    }
}


private fun Modifier.overlapBelow(overlap: Dp): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height - overlap.roundToPx()) {
        placeable.place(0, 0)
    }
}