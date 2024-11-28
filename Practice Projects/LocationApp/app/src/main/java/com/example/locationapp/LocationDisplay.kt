package com.example.locationapp

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    val locationPermissionPopup = locationPermissionPopup(context = context)
    val location = locationViewModel.locationData.value
    val address = location.let {
        if (location != null) {
            locationUtils.reverseGeocodeLocation(location)
        } else {
            "Location is null"
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Location permission not granted")

        if(location != null) {
            Text(text = "Address : $address")
        } else {
            Text(text = "Location Not Available")
        }

        Button(onClick = {
            if (locationUtils.haveLocationAccess(context)) {
                locationUtils.requestLocationUpdates(viewModel = locationViewModel)
            } else {
                locationPermissionPopup.launch(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }
        }) {
            Text(text = "Get Location")
        }
    }
}