package com.mahmoudbashir.sharedtransitionexample.transition_content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmoudbashir.sharedtransitionexample.R
import com.mahmoudbashir.sharedtransitionexample.ui.theme.LavenderLight
import com.mahmoudbashir.sharedtransitionexample.ui.theme.RoseLight

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedContentScreen() {
    var showDetails by remember {
        mutableStateOf(false)
    }

    SharedTransitionLayout {
        AnimatedContent(
            showDetails,
            label = "basic_transition"
        ) { targetState ->
            if (!targetState) {
                //mainContent
                MainContent(
                    onShowDetails = { showDetails = true },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent,
                )
            } else {
                //detailsContent
                DetailsContent(
                    onBack = { showDetails = false },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent,
                )
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContent(
    onShowDetails: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(
                1.dp, Color.Gray.copy(alpha = 0.5f),
                RoundedCornerShape(8.dp)
            )
            .background(
                LavenderLight,
                RoundedCornerShape(8.dp)
            )
            .clickable {
                onShowDetails()
            }
            .padding(8.dp)
    ) {
        with(sharedTransitionScope) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter =
                painterResource(id = R.drawable.img),
                contentDescription = null
            )

            Text(
                text = "CupCake", fontSize = 21.sp,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(key = "title"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = modifier
            .padding(top = 200.dp, start = 16.dp, end = 16.dp)
            .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .background(RoseLight, RoundedCornerShape(8.dp))
            .clickable {
                onBack()
            }
            .padding(8.dp)
    ) {
        with(sharedTransitionScope) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Cupcake",
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "image"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                "Cupcake", fontSize = 28.sp,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(key = "title"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet lobortis velit. " +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        " Curabitur sagittis, lectus posuere imperdiet facilisis, nibh massa " +
                        "molestie est, quis dapibus orci ligula non magna. Pellentesque rhoncus " +
                        "hendrerit massa quis ultricies. Curabitur congue ullamcorper leo, at maximus"
            )
        }
    }
}