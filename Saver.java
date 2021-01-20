package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;

public class Saver {
    public static void save(Class<?>... ls) {
        try {
            for (Class<?> cls : ls) {
                if (cls.isAnnotationPresent(SaveTo.class)) {
                    TextContainer tc = new TextContainer();
                    SaveTo saveTo = cls.getAnnotation(SaveTo.class);
                    String directory = saveTo.directory();
                    Field[] fields = cls.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Save.class)) {
                            field.setAccessible(true);
                            System.out.println(field.get(tc));
                            File file = new File(directory + "/Save.txt");
                            file.createNewFile();
                            System.out.println(file.getAbsolutePath());
                            try (PrintWriter pw = new PrintWriter(file);) {
                                pw.println(field.get(tc).toString());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }
}
