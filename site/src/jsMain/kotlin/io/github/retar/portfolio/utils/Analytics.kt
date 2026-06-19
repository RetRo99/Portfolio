package io.github.retar.portfolio.utils

import kotlinx.browser.window

/**
 * Umami analytics tracking utilities.
 *
 * Umami script is loaded via build.gradle.kts and provides a global `window.umami` object.
 */
external interface Umami {
    fun track(eventName: String)
    fun track(eventName: String, eventData: dynamic)
}

/**
 * Access to the Umami analytics object.
 * Returns null if Umami is not loaded (e.g., ad blockers).
 */
val umami: Umami?
    get() = window.asDynamic().umami as? Umami

/**
 * Track a custom event with Umami analytics.
 *
 * @param eventName The name of the event (max 50 characters)
 * @param eventData Optional data to attach to the event
 */
fun trackEvent(eventName: String, eventData: dynamic = null) {
    try {
        if (umami == null) {
            console.warn("Umami analytics not loaded. Event not tracked: $eventName")
            return
        }

        if (eventData != null) {
            umami?.track(eventName, eventData)
        } else {
            umami?.track(eventName)
        }
    } catch (e: Throwable) {
        console.error("Failed to track event: $eventName", e)
    }
}

