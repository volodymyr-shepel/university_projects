package com.rogue.travelguru.ui.presentation.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.rogue.travelguru.data.local.Sight
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SightPhotosScreen(
    sights: LazyPagingItems<Sight>,
    navigateToSight: (sight: Sight) -> () -> Unit,
    component: (sight: Sight) -> @Composable () -> Unit,
    func:  (sight: Sight) -> () -> Unit,
    modifier: Modifier = Modifier,
    isSwipeAble: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = sights.loadState) {
        if(sights.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (sights.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    if(sights.loadState.refresh is LoadState.Loading) {
        AuthLoginProgressIndicator()
    } else {

            LazyColumn(modifier = modifier) {
                items(items = sights) { sight ->
                    if (sight != null) {
                        if(isSwipeAble) {
                            SwipeToDeleteContainer(item = sight, onDelete = func,
                            //                            modifier = Modifier.animateItem(
//                                fadeInSpec = null,
//                                fadeOutSpec = null,
//                                placementSpec = tween(100)
//                            )
                            ) {
                                SightPhotoCard(
                                    sight = sight,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                                    onClick = navigateToSight(sight),
                                    component = {}
                                )
                            }
                        }
                        else {
                            SightPhotoCard(
                                sight = sight,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                                onClick = navigateToSight(sight),
                                component = component(sight)
                            )
                        }
                    }
                }
                item {
                    if (sights.loadState.append is LoadState.Loading) {
                        AuthLoginProgressIndicator()
                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> () -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)()
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBack(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = setOf(SwipeToDismissBoxValue.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBack(swipeDismissState: SwipeToDismissBoxState) {
    val color = if(swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
        Color.Red
    } else {
        Color.Transparent
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.CenterEnd
    ) {
//        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", tint = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissItem(
    item: Sight,
    onRemove: (sight: Sight) -> () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val coroutine = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            state ->
            if(state == SwipeToDismissBoxValue.EndToStart) {
                coroutine.launch {
                    delay(2000)
                    onRemove(item)()
                }
                true
            } else {
                false
            }
        }
    )
    
    SwipeToDismissBox(state = swipeToDismissState, backgroundContent = {
        val back by animateColorAsState(
            targetValue = when(swipeToDismissState.currentValue) {
                SwipeToDismissBoxValue.StartToEnd -> Color.Green
                SwipeToDismissBoxValue.EndToStart -> Color.Red
                SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.background
            }, label = "Animate Background"
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(back)
        )
    },
        modifier = modifier
        ) {
        content()
    }
}