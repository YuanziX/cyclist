package dev.yuanzix.cyclist.dash.presentation.mycycle

sealed interface MyCycleAction {
    data object LockUnlockCycle : MyCycleAction
}