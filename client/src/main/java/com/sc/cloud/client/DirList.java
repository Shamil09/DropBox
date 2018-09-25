package com.sc.cloud.client;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

class DirList {
    DirList(String dirname) {

//        List<File> files = (List<File>) FileUtils.listFiles(new File("e:/MyDir"), null, true);
//        files.forEach(System.out::println);
//        System.out.println("Готов");

        //получить и обработать поток вывода каталога
        try (DirectoryStream<Path> dirstrm =
                     Files.newDirectoryStream(Paths.get(dirname)) )
        {
            System.out.println("Каталог " +dirname);
            for (Path entry:dirstrm) {
                BasicFileAttributes attribs=
                        Files.readAttributes(entry,BasicFileAttributes.class);
                if(attribs.isDirectory()) System.out.print("<DIR>");
                else System.out.print("faile: ");
                System.out.print(entry.getNameCount()-1+"-");
                System.out.println(entry.getName(entry.getNameCount()-1));
            }
        }catch(InvalidPathException e){
            System.out.println("Ошибка указания пути " + e);
        }catch(NotDirectoryException e){
            System.out.println(dirname+" не является каталогом");
        }catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: "+e);
        }
    }
}
