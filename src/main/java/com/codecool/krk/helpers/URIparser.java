package com.codecool.krk.helpers;

import java.net.URI;

public class URIparser {
    public static boolean hasIdentifier(URI uri) {
        String[] splitted = splitURI(uri);

        return splitted.length > 1;
    }

    public static boolean hasQueryString(URI uri) {
        String[] splitted = splitURI(uri);

        return splitted.length > 2;
    }

    public static String parseIdentifier(URI uri) {
        return splitURI(uri)[1];
    }

    public static int parseIdentifierToInt(URI uri) {
        return Integer.parseInt(parseIdentifier(uri));
    }

    public static String getQueryString(URI uri) {
        return splitURI(uri)[2];
    }

    private static String[] splitURI(URI uri) {
        return uri.toString().substring(1).split("/|\\?");
    }
}
