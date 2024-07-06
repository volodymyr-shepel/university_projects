package com.rogue.travelguru.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleBottomSheetScaffold(
    padding : PaddingValues,
    title : String = "Recommended Place to visit",
    content: @Composable (PaddingValues) -> Unit,
    sheetContent : @Composable () -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 180.dp,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxHeight(fraction = 0.8f).padding(
                    top = 0.dp,
                    bottom = padding.calculateBottomPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 10.dp)
                ) {
                    item {
                        sheetContent()
                    }
                }
            }
        }) {padding ->
        content(padding)
    }

    }

