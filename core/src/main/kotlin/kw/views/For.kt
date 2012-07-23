package kw.views

import java.util.Collection


/**
    Function For concatenates all the String returned by the body closure
    calling it with each instance of the [Iterable], in order.

    @return the result of such concatenation.
*/
public fun <TItem> Iterable<TItem>.For(body:(TItem)->String):String{
    val b=StringBuilder()
    for (h in this) {
        b.append(body(h));
    }
    return b.toString().sure()
}

/**
    Function For concatenates all the String returned by the body closure
    calling it with each instance of the [Collection], in order.

    @return the result of such concatenation.
*/
public fun <TItem> Collection<TItem>.For(body:(TItem)->String):String{
    val b=StringBuilder()
    for (h in this) {
        b.append(body(h));
    }
    return b.toString().sure()
}

/**
    Function For concatenates all the String returned by the body closure
    calling it with each instance of the [Array], in order.

    @return the result of such concatenation.
*/
public fun <TItem> Array<TItem>.For(body:(TItem)->String):String{
    val b=StringBuilder()
    for (h in this) {
        b.append(body(h));
    }
    return b.toString().sure()
}