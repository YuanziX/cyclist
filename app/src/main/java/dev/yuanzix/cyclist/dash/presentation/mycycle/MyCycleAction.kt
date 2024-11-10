package dev.yuanzix.cyclist.dash.presentation.mycycle

import android.content.Context

sealed interface MyCycleAction {
    data object LockUnlockCycle : MyCycleAction
    data class LocateCycle(val ctx: Context) : MyCycleAction
}