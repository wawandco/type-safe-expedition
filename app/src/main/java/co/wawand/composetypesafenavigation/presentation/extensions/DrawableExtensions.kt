package co.wawand.composetypesafenavigation.presentation.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.unit.IntSize
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun Drawable.bitmapDescriptorFromVector(): Pair<BitmapDescriptor, IntSize> {
    // retrieve the actual drawable
    this.setBounds(0, 0, this.intrinsicWidth, this.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        this.intrinsicWidth,
        this.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // Draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    this.draw(canvas)

    // Return both BitmapDescriptor and its size (width, height)
    return BitmapDescriptorFactory.fromBitmap(bm) to IntSize(bm.width, bm.height)
}