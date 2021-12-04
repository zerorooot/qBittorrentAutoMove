package github.zerorooot;

import github.zerorooot.bean.FileBean;
import github.zerorooot.service.qBittorrentUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(args[0]),
                    StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
            start(properties);
        } catch (ArrayIndexOutOfBoundsException | FileNotFoundException e) {
            System.err.println("File not found, use default value");
            start(properties);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public static void start(Properties properties) {
        String hosts = properties.getProperty("hosts", "127.0.0.1:8080");
        String name = properties.getProperty("username", "admin");
        String password = properties.getProperty("password", "adminadmin");
        String path = properties.getProperty("path", Path.of("").toAbsolutePath().toString());

        qBittorrentUtil qBittorrentUtil = new qBittorrentUtil(hosts);
        String login = qBittorrentUtil.login(name, password);
        ArrayList<FileBean> torrentsList = qBittorrentUtil.getTorrentsList(login);
        System.out.println("Download items : " + torrentsList.size());
        torrentsList.forEach(f -> {
            File oldFile = new File(f.getContent_path());
            //Avoid the problem that the file name has no suffix caused by the difference between "f.getContent_path()" and
            // "f.getName()"
            if (oldFile.isFile()) {
                f.setName(oldFile.getName());
            }
            String newPath = path + File.separator + f.getName();
            boolean b = oldFile.renameTo(new File(newPath));
            System.out.println("remove " + f.getContent_path() + " to " + newPath + "  " + b);
            if (b) {
                qBittorrentUtil.delete(f, login);
            }
        });
    }


}
