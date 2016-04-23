package com.mixailsednev.githubrepo.mvptabletphone.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MsCollectionsUtils {

    public interface IPredicate<T> { boolean apply(T type); }

    public static <T> List<T> filter(Collection<T> target, IPredicate<T> predicate) {
        List<T> result = new ArrayList<T>();
        for (T element: target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
