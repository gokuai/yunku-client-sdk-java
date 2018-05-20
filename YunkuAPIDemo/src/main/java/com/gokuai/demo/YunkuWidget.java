package com.gokuai.demo;

import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.data.YunkuException;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.model.FileData;
import com.gokuai.demo.model.LibraryData;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * 这是一个实例代码, 演示了用户登录, 通过键盘操作, 获取用户库列表, 获取文件列表, 查看文件信息
 */
public class YunkuWidget {

    private List<LibraryData> libraries;
    private List<FileData> files;
    private int mountId;
    private String orgName;
    private String fullpath = "";
    private Stack<String> paths;

    public static void main(String[] args) {

        try {
            YunkuWidget widget = new YunkuWidget();
            widget.run();
        } catch (YunkuException e) {
            e.printStackTrace();
        }
    }

    public void run() throws YunkuException {
        YunkuAuth.auth();
        this.getLibraries();

        int ret = 2;
        while (true) {
            if (ret == 1) {
                //显示当前目录文件列表
                this.getFiles();
                this.showFiles();
            } else if (ret == 2) {
                //显示库列表
                this.showLibraries();
                this.chooseLibrary();
                ret = 1;
                continue;
            }
            //选择文件, 或进入目录
            ret = this.chooseFile();
        }
    }

    //获取库列表
    private void getLibraries() throws YunkuException {
        ReturnResult result = YKHttpEngine.getInstance().getLibraries();

        if (!result.isOK()) {
            throw new YunkuException("fail to get user libraries", result);
        }
        this.libraries = LibraryData.createList(result);
    }

    //获取文件列表
    private void getFiles() throws YunkuException {
        ReturnResult result = YKHttpEngine.getInstance().getLibraryFiles(this.mountId, this.fullpath, 0, 20);

        if (!result.isOK()) {
            throw new YunkuException("fail to get library files", result);
        }
        this.files = FileData.createList(result);
    }

    //显示文件信息
    private void showFile(FileData file) throws YunkuException {
        ReturnResult result = YKHttpEngine.getInstance().getFileUrlByHash(this.mountId, file.getHash(), file.getFileHash());
        System.out.println(result.getBody());
    }

    //显示库列表
    private void showLibraries() {
        System.out.println();
        for (int i = 0; i < this.libraries.size(); i++) {
            LibraryData data = this.libraries.get(i);
            System.out.printf("%d. %s\n", i + 1, data.getName());
        }
    }

    //选择一个库
    private void chooseLibrary() throws YunkuException {
        System.out.print("choose a library: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        if (index <= 0 || index > this.libraries.size()) {
            System.out.println("error: library index not found");
            this.chooseLibrary();
            return;
        }

       LibraryData data = this.libraries.get(index - 1);
       if (data == null) {
            throw new YunkuException("library is null: " + index);
       }
       this.mountId = data.getId();
       this.orgName = data.getName();
       this.paths = new Stack<String>();
    }

    //改变当前目录
    private String changeFolder() {
        String fullpath = "";
        if (!this.paths.isEmpty()) {
            for(String path: this.paths) {
                fullpath += "/" + path;
            }
            fullpath = fullpath.substring(1);
        }
        this.fullpath = fullpath;
        return fullpath;
    }

    //显示文件列表
    private void showFiles() {
        System.out.println("\n0. ../");
        for (int i = 0; i < this.files.size(); i++) {
            FileData data = this.files.get(i);
            System.out.printf("%d. %s", i + 1, data.getFileName());
            if (data.isDir()) {
                System.out.println("/");
            } else {
                System.out.println("");
            }
        }
    }

    //选择一个文件或进入目录
    private int chooseFile() throws YunkuException {
        System.out.println("choose a file or enter a folder:");
        System.out.print(this.orgName + "/" + this.fullpath + "> ");

        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        if (index < 0 || index > this.files.size()) {
            System.out.println("error: file index not found");
            return 0;
        }

        //返回上级
        if (index == 0) {
            if (this.paths.isEmpty()) {
                //返回库列表
                return 2;
            } else {
                //返回上级文件夹
                this.paths.pop();
                this.changeFolder();
                return 1;
            }
        }

        FileData file = this.files.get(index - 1);
        if (file == null) {
            throw new YunkuException("file is null: " + index);
        }

        if (file.isDir()) {
            //进入下级
            this.paths.push(file.getFileName());
            this.changeFolder();
            return 1;
        } else {
            //显示选中文件的信息
            this.showFile(file);
            return 0;
        }
    }
}
