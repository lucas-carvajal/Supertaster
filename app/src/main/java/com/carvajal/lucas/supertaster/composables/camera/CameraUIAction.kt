package com.carvajal.lucas.supertaster.composables.camera

sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    object OnBackClick : CameraUIAction()
    object OnSwitchCameraClick : CameraUIAction()
}