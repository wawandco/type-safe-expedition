package co.wawand.composetypesafenavigation.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.presentation.extensions.bitmapDescriptorFromVector
import co.wawand.composetypesafenavigation.presentation.extensions.centerOnLocation
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun Map() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val wawandcoCoords = LatLng(11.00023065512785, -74.78773200436935)
        val cameraPositionState = rememberCameraPositionState()
        val markerState = rememberMarkerState(position = wawandcoCoords)
        val (markerIcon, markerSize) = ContextCompat.getDrawable(
            LocalContext.current,
            R.drawable.map_marker
        )?.bitmapDescriptorFromVector() ?: (BitmapDescriptorFactory.defaultMarker() to IntSize.Zero)

        LaunchedEffect(key1 = wawandcoCoords) {
            cameraPositionState.centerOnLocation(wawandcoCoords)
        }

        var markerString by remember { mutableStateOf("Wawandco") }

        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = false),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                compassEnabled = false,
                zoomControlsEnabled = false,
            )
        ) {
            MarkerInfoWindow(
                state = markerState,
                icon = markerIcon,
            ) { marker ->
                // Mutable state for the info window size
                var infoWindowSize by remember { mutableStateOf(IntSize.Zero) }

                val horizontalSeparation = with(LocalDensity.current) { 8.dp.toPx() }

                // Adjust anchor based on the actual bitmap size
                val anchorX = if (markerSize.width > 0) {
                    -((infoWindowSize.width.toFloat() / 2) + horizontalSeparation) / markerSize.width.toFloat()
                } else {
                    0.5f
                }

                val anchorY = 1.0f

                marker.setInfoWindowAnchor(anchorX, anchorY)

                if (!marker.isInfoWindowShown) markerString = strings.random()

                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            infoWindowSize = coordinates.size
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = markerString,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = dpToSp(12.dp)
                    )
                }
            }
        }
    }
}

val strings = listOf(
    "Joe",
    "Design",
    "Wawandco",
    "Development",
    "Barranquilla",
    "Collaborative",
    "Growth-oriented",
    "Jetpack Compose",
    "Staff Augmentation",
    "Android Mobile Application",
    "International Solutions by Wawandco"
)

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }