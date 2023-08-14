package com.example.weatherapp.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle.Event.ON_ANY
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleCallbacks(
  onResume: () -> Unit = {},
  onStart: () -> Unit = {},
  onStop: () -> Unit = {},
  lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
  val currentOnStart by rememberUpdatedState(onStart)
  val currentOnStop by rememberUpdatedState(onStop)
  val currentOnResume by rememberUpdatedState(onResume)

  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      when (event) {
        ON_START -> currentOnStart()
        ON_RESUME -> currentOnResume()
        ON_STOP -> currentOnStop()
        ON_CREATE, ON_PAUSE, ON_DESTROY, ON_ANY -> {}
      }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose {
      lifecycleOwner.lifecycle.removeObserver(observer)
    }
  }
}