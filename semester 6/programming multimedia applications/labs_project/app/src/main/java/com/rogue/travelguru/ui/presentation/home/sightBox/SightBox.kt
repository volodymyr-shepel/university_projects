import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rogue.travelguru.R
import com.rogue.travelguru.data.local.Sight
import com.rogue.travelguru.model.SightToSee
import com.rogue.travelguru.ui.theme.WhiteGradientEnd
import com.rogue.travelguru.ui.theme.WhiteGradientStart
import com.rogue.travelguru.ui.theme.WhiteStrokeEnd
import com.rogue.travelguru.ui.theme.WhiteStrokeStart


const val WIDTH_OF_SIGHT_BOX = 350f
const val HEIGHT_OF_SIGHT_BOX = 140f
private const val MARGIN_OF_SIGHT_BOX = 10f

private const val SHADOW_ELEVATION = 2f

const val WIDTH_OF_BORDER = 1f

const val PADDING_OF_SIGHT_BOX_COEF = 1f
const val PADDING_OF_SIGHT_BOX = 2f
const val BORDER_RADIUS_OF_SIGHT_BOX = 20f


@Composable
fun Modifier.build(): Modifier {
    return SightBox().buildModifier(this)
}

private class SightBox {
    private fun Modifier.sizeModifier() = this.height(
//        width = (WIDTH_OF_SIGHT_BOX + MARGIN_OF_SIGHT_BOX).dp,
        height = (HEIGHT_OF_SIGHT_BOX + MARGIN_OF_SIGHT_BOX).dp
    )

    private fun Modifier.paddingOfSightBox() = this.padding(PADDING_OF_SIGHT_BOX.dp)

    private fun Modifier.innerContentWrapper() = this.wrapContentSize(Alignment.Center)

    private fun Modifier.shadowOfSightBox() = this.shadow(
        elevation = SHADOW_ELEVATION.dp,
        shape = RoundedCornerShape((BORDER_RADIUS_OF_SIGHT_BOX - PADDING_OF_SIGHT_BOX).dp),
        spotColor = Color(0x99000000)
    )

    private fun Modifier.shadowPadding() = this.padding(
        start = 0.dp,
        top = 0.dp,
        end = PADDING_OF_SIGHT_BOX.dp,
        bottom = PADDING_OF_SIGHT_BOX.dp
    )

    private fun Modifier.innerBackground() = this.background(
        color = Color(0xFFEBEBF0),
        shape = RoundedCornerShape((BORDER_RADIUS_OF_SIGHT_BOX - PADDING_OF_SIGHT_BOX).dp)
    )

    @Composable
    private fun Modifier.gradientBackgroundFilter() = this.background(
        brush = Brush.linearGradient(
            colors = listOf(WhiteGradientStart, WhiteGradientEnd),
            start = Offset(10f, 30f),
            end = Offset(WIDTH_OF_SIGHT_BOX, HEIGHT_OF_SIGHT_BOX)
        ),
        shape = RoundedCornerShape((BORDER_RADIUS_OF_SIGHT_BOX - PADDING_OF_SIGHT_BOX).dp)
    )

    @Composable
    private fun Modifier.gradientBorder() = this.border(
        width = WIDTH_OF_BORDER.dp,
        brush = Brush.linearGradient(
            colors = listOf(MaterialTheme.colorScheme.outlineVariant, MaterialTheme.colorScheme.outline),
            start = Offset(10f, 30f),
            end = Offset(WIDTH_OF_SIGHT_BOX, HEIGHT_OF_SIGHT_BOX)
        ),
        shape = RoundedCornerShape((BORDER_RADIUS_OF_SIGHT_BOX - (PADDING_OF_SIGHT_BOX)).dp)
    )

    @Composable
    fun buildModifier(modifier: Modifier): Modifier {
        return modifier
            .sizeModifier()
            .paddingOfSightBox()
            .gradientBorder()
            .gradientBackgroundFilter()
    }
}