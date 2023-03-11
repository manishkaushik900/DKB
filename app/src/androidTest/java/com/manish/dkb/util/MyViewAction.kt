package com.manish.dkb.util

import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class MyViewAction {
    companion object {
        fun typeTextInChildViewWithId(id: Int, textToBeTyped: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id) as EditText
                    v.setText(textToBeTyped)
                }
            }
        }

        fun selectSpinnerItemInChildViewWithId(id: Int, position: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id) as AppCompatSpinner
                    v.setSelection(position)
                }
            }
        }

        fun clickItemWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id) as View
                    v.performClick()
                }
            }
        }

        fun <T> first(matcher: Matcher<T>): Matcher<T>? {
            return object : BaseMatcher<T>() {
                var isFirst = true
                override fun matches(item: Any): Boolean {
                    if (isFirst && matcher.matches(item)) {
                        isFirst = false
                        return true
                    }
                    return false
                }

                override fun describeTo(description: Description) {
                    description.appendText("should return first matching item")
                }
            }
        }

    }
}