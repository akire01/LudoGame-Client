/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author ER
 */
public class ReflectionUtils {

    private static final String PACKAGE_NAME = "hr.erika.javafx.data.";
    private static final String PACKAGE_LOCATION
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\data\\";

    private static final String PACKAGE_NAME2 = "hr.erika.javafx.state.";
    private static final String PACKAGE_LOCATION2
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\state\\";

    private static final String PACKAGE_NAME3 = "hr.erika.javafx.utils.";
    private static final String PACKAGE_LOCATION3
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\utils\\";
    
    private static final String PACKAGE_NAME4 = "hr.erika.javafx.app.firstScreen.";
    private static final String PACKAGE_LOCATION4
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\app\\firstScreen\\";
    
    private static final String PACKAGE_NAME5 = "hr.erika.javafx.app.rules.";
    private static final String PACKAGE_LOCATION5
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\app\\rules\\";
    
    private static final String PACKAGE_NAME6 = "hr.erika.javafx.app.userScreen.";
    private static final String PACKAGE_LOCATION6
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\app\\userScreen\\";
    
    private static final String PACKAGE_NAME7 = "hr.erika.javafx.app.";
    private static final String PACKAGE_LOCATION7
            = "C:\\Users\\ER\\Desktop\\JavaProjekt-Erika_Raguz"
            + "\\build\\classes\\hr\\erika\\javafx\\app\\";
    

    public static void fillMaps() {

        List<Map<String, String>> maps = new ArrayList<>();
        Map<String, String> hm = new HashMap<>();
        hm.put(PACKAGE_NAME, PACKAGE_LOCATION);
        maps.add(hm);
        /*for ( Map.Entry<String, String> entry : hm.entrySet()) {
            String packageName = entry.getKey();
            String packageLocation = entry.getValue();
            try {
                writeDoc(packageName, packageLocation);
            } catch (IntrospectionException ex) {
                Logger.getLogger(Documentation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

        Map<String, String> hm2 = new HashMap<>();
        hm2.put(PACKAGE_NAME2, PACKAGE_LOCATION2);
        maps.add(hm2);

        Map<String, String> hm3 = new HashMap<>();
        hm3.put(PACKAGE_NAME3, PACKAGE_LOCATION3);
        maps.add(hm3);
        
        Map<String, String> hm4 = new HashMap<>();
        hm4.put(PACKAGE_NAME4, PACKAGE_LOCATION4);
        maps.add(hm4);
        
        Map<String, String> hm5 = new HashMap<>();
        hm5.put(PACKAGE_NAME5, PACKAGE_LOCATION5);
        maps.add(hm5);
        
        Map<String, String> hm6 = new HashMap<>();
        hm6.put(PACKAGE_NAME6, PACKAGE_LOCATION6);
        maps.add(hm6);
        
        Map<String, String> hm7 = new HashMap<>();
        hm7.put(PACKAGE_NAME7, PACKAGE_LOCATION7);
        maps.add(hm7);
       
        try {
            writeDoc(maps);
        } catch (IntrospectionException ex) {
            Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void writeDoc(List<Map<String, String>> maps) throws IntrospectionException {

        try (FileWriter docGenerator
                = new FileWriter("htmlDocumentation.html")) {
            docGenerator.write("<!DOCTYPE html>");
            docGenerator.write("<html>");
            docGenerator.write("<head>");
            docGenerator.write("<title>Class documentation</title>");
            docGenerator.write("</head>");
            docGenerator.write("<body>");
            docGenerator.write("<h1>Documentation for game \"Man, don't get Angry\"</h1>");

            maps.forEach(map -> {
                try {
                    write(map, docGenerator);
                } catch (IOException ex) {
                    Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrospectionException ex) {
                    Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void write(Map<String, String> map, FileWriter docGenerator) throws IOException, ClassNotFoundException, IntrospectionException {
        String packageName = "";
        String packageLocation = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            packageName = entry.getKey();
            packageLocation = entry.getValue();

        }

        List<String> filesInPackage = Files
                .list(Paths.get(packageLocation))
                .map(file -> file.toFile().getName())
                .collect(Collectors.toList());

        for (String fileName : filesInPackage) {

           if (!fileName.contains(".class")) {
            continue;
           }
            
            if (fileName.indexOf(".") > 0) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }

            Class<?> unknownObject = Class.forName(packageName + fileName);
            if (unknownObject.isEnum()) {
                 docGenerator.write("<h2>" + Modifier.toString(unknownObject.getModifiers()) + " Enum Class: " + unknownObject.getSimpleName()
                    + " </h2>" + "<h3>" + unknownObject.getPackage()
                    + " </h3>");
            }
            else{
                docGenerator.write("<h2>" + Modifier.toString(unknownObject.getModifiers()) + " Class: " +  unknownObject.getSimpleName()
                    + " </h2>" + "<h3>" + unknownObject.getPackage()
                    + " </h3>");
            }
            
            //extended classes
            docGenerator.write("extends:");
             Class<?> parent = unknownObject.getSuperclass();
            if (parent == null) {
                docGenerator.write("");
            }
            else{
                docGenerator.write(parent.getName());
            }
            //implemented interfaces
            if (unknownObject.getInterfaces().length > 0) {
                    docGenerator.append("\nimplements");
                }
                for (Class<?> in : unknownObject.getInterfaces()) {
                    docGenerator.append(in.getName());
             }
            
            Field[] fields = unknownObject.getDeclaredFields();

            docGenerator.write("<h4>Fields:</h4>");

            for (Field field : fields) {
                Integer modifiers = field.getModifiers();
                boolean isPublic = (modifiers % 2) == 1;
                boolean isPrivate = (modifiers % 2) == 0;
                if (isPublic) {
                    docGenerator.write("public ");
                } else if (isPrivate) {
                    docGenerator.write("private ");
                }
                docGenerator.write(field.getType().getName() + " ");
                docGenerator.write("<b>" + field.getName()+ "</b>" + "<br />");
            }
            
            Constructor[] constructors = unknownObject.getConstructors();
            docGenerator.write("<br> Number of constructors: " + constructors.length);
            for (Constructor con : constructors) {
                
                Parameter[] params = con.getParameters();

                if (params.length > 0) {

                    docGenerator.write(
                            "<h4>Constructor parameters: </h4>");

                    for (Parameter param : params) {
                        docGenerator.write("Parameter: "
                                + param.getType().getName());
                        docGenerator.write(" "
                                + param.getName() + "<br />");
                    }
                } else {
                    docGenerator.write(
                            "<h4>Default constructor without parameters"
                            + "</h4>");
                }
            }

            docGenerator.write("<h4>Getters:</h4>");
            for (PropertyDescriptor propertyDescriptor
                    : Introspector.getBeanInfo(unknownObject).getPropertyDescriptors()) {
                docGenerator.write(
                        propertyDescriptor.getReadMethod() + "<br />");
            }

            docGenerator.write("<h4>Setters:</h4>");
            for (PropertyDescriptor propertyDescriptor
                    : Introspector.getBeanInfo(unknownObject).getPropertyDescriptors()) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    docGenerator.write(propertyDescriptor.getWriteMethod() + "<br />");
                } else {
                    continue;
                }
            }

            docGenerator.write("<h4>Methods:</h4>");
            Method[] methods = unknownObject.getDeclaredMethods();
            for (Method method : methods) {
                for (Annotation annotation : method.getAnnotations()) {
                   docGenerator.write("<h4>" + annotation.toString()
                        + " </h4>");
                }
                //Parameter[] parameters = method.getParameters();
                docGenerator.write("<h4>" +  Modifier.toString(method.getModifiers()) + " " 
                        +  method.getReturnType() + " " + method.getName() + " </h4>");
                
                if (method.getParameters().length != 0) {
                    docGenerator.append("<h5>Parameters:<h5>");
                    for (Parameter parameter : method.getParameters()) {
                        docGenerator.append("<h5>" +  parameter.toString() + " </h5>");
                    }
                }
                
               Class<?>[] exceptionTypes = method.getExceptionTypes();
                if (exceptionTypes.length > 0) {
                    docGenerator.append(" throws ");
                    for (Class<?> exceptionType : exceptionTypes) {
                        docGenerator.append(exceptionType.toString());
                    }
           
            }

           

            
        }

        docGenerator.write("</body>");
        docGenerator.write("</html>");

        docGenerator.flush();

        System.out.println("Documentation generation successfuly done!");

    }

    }
}
