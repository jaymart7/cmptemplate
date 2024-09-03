package common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import cmptemplate.shared.generated.resources.Res
import cmptemplate.shared.generated.resources.image_error
import cmptemplate.shared.generated.resources.image_placeholder

@Composable
internal fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = url,
        placeholder = painterResource(Res.drawable.image_placeholder),
        error = painterResource(Res.drawable.image_error),
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}