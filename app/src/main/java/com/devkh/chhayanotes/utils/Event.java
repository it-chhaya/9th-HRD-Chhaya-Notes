package com.devkh.chhayanotes.utils;

public class Event<T> {

    private final T content;

    public Event(T content) {
        this.content = content;
    }

    private boolean hasBeenHandled = false;

    /*
    * Allow external read but not write (READ ONLY)
    * */
    public boolean getHasBeenHandled() {
        return hasBeenHandled;
    }

    /*
    * Returns the content and prevents its use again
    * */
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    /*
    * Returns the content, even if it's already been handled
    * */
    public T peekContent() {
        return content;
    }

}
