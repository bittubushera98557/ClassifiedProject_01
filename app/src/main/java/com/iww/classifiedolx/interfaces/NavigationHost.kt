 package com.iww.classifiedolx

import androidx.fragment.app.Fragment


/**
 * A host (typically an `Activity`} that can display fragments and knows how to respond to
 * navigation events.
 */
interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     * for replace false and for add true
     */
    fun navigateTo(fragment: Fragment, addToBackstack: Boolean, addOrReplace: Boolean)
}
interface SelectViewPager {
    fun currentItem(item : Int)
}
interface FrRefresh {
    fun onFrRefresh()
}
interface SendString {
    fun onSendString(sendString : String)
}
interface HideProgressBar {
    fun onHideProgressBar(showOrHide : Boolean)
}
interface OnItemClickListener{
    fun onItemClick(item : Any)
}


