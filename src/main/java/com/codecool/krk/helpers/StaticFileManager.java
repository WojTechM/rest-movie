package com.codecool.krk.helpers;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Scanner;

public class StaticFileManager {

    private static final String PATH_TO_RESOURCES = "/WEB-INF/frontend";

    public static String loadFileAsStringByPath(HttpServletRequest request, String path) {
        ServletContext context = request.getServletContext();
        String preparedPath = preparePath(path);
        InputStream inputStream = getFileAsInputStream(context, preparedPath);
        return parseInputStreamToString(inputStream);
    }

    public static String preparePath(String path) {
        if (path.toLowerCase().contains("static")) {
            return PATH_TO_RESOURCES + path.split("/static")[1];
        } else {
            return PATH_TO_RESOURCES + path;
        }
    }

    private static InputStream getFileAsInputStream(ServletContext context, String path) {
        return context.getResourceAsStream(path);
    }

    private static String parseInputStreamToString(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine() + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return e.getClass().toString();
        }
    }
}
