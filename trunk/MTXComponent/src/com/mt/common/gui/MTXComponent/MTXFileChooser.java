/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.settingManager.SettingDataManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 文件选择组件
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-4-27
 * Time:9:23:40
 */
public class MTXFileChooser extends JFileChooser {

    private String name = "GlobalFileChooserPath";

    public MTXFileChooser() {
        this(null);
    }

    public MTXFileChooser(String name) {
        super();
        if (name != null) {
            this.name = name + "FileChooserPath";
        }

        super.setSelectedFile(getSettingPath());

    }

    @Override
    public int showSaveDialog(Component parent) {
        int rs = super.showSaveDialog(parent);
        if (rs == JFileChooser.APPROVE_OPTION) {
            saveSettingPath();
        }
        return rs;
    }

    @Override
    public int showOpenDialog(Component parent) {
        int rs = super.showOpenDialog(parent);
        if (rs == JFileChooser.APPROVE_OPTION) {
            saveSettingPath();
        }
        return rs;
    }

    private File getSettingPath() {
        File file = null;
        FieldMap fm = (FieldMap) SettingDataManager.readLocalSetting(this.name, FieldMap.class);
        if (fm != null) {
            file = new File(fm.getFieldValue("Path").toString());
        }
        return file;
    }

    private void saveSettingPath() {
        File sf = super.getSelectedFile();
        String path = sf.getAbsolutePath();
        if (sf.isDirectory()) {
            path = path + "/test";//便于定位到目录本身
        }
        FieldMap para = new FieldMap(this.name).putField("Path", path);
        SettingDataManager.saveLocalSetting(para.getName(), para);
    }
}
