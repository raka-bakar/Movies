package com.raka.movies.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * Navigation class that is the base of all navigation destinations
 * @param route destination route
 */
abstract class Navigation(open val route: String) {
    //graphId of the destination
    abstract val graphId: String

    //arguments of the destination
    protected open fun getArguments(): List<NamedNavArgument> = emptyList()

    //get the full route with all nav arguments for destination construction
    protected fun getFullRoute() = getArguments().fold(route){pre,value->
        "$pre/{${value.name}}"
    }

    // function to compose actual destination
    context(NavGraphBuilder)
    abstract fun compose(controller: NavController)
}