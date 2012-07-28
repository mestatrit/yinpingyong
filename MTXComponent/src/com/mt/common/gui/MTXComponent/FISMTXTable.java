package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.ColorLib;
import com.mt.common.gui.table.FISSimpleTableCellRenderer;
import com.mt.common.gui.table.MCDate;
import com.mt.common.gui.table.MCNumber;
import com.mt.common.gui.table.MCTerm;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * 一个整合了列宽自适应，排序，导出复制等功能的表格
 *
 * @author hanhhui
 */
@SuppressWarnings("serial")
public class FISMTXTable extends MTXTable {


    public FISMTXTable() {
        super();
        setDefaultColor();
        /**
         * 去除设置导出和拷贝权限
         * 2012.07.28 yinpy
         */
       /* if (!OrganizationInfoManager.isEnableFISCopy())
        {
		    setExportEnable(false);
		    setCopyEnable(false);
        }*/
    }

    public FISMTXTable(boolean isAuto, boolean isSort) {
        super(null, isAuto, isSort);
        setDefaultColor();
       /* if (!OrganizationInfoManager.isEnableFISCopy())
        {
		    setExportEnable(false);
		    setCopyEnable(false);
        }*/
    }

    public FISMTXTable(TableModel model)
    {
        super(model, true, true);
        setDefaultColor();
        /*if (!OrganizationInfoManager.isEnableFISCopy())
        {
		    setExportEnable(false);
		    setCopyEnable(false);
        }*/
    }

    public void setDefaultColor()
    {
        FISSimpleTableCellRenderer left = new FISSimpleTableCellRenderer(SwingConstants.LEFT);
        FISSimpleTableCellRenderer right = new FISSimpleTableCellRenderer(SwingConstants.RIGHT);
        this.setDefaultRenderer(Object.class, left);
        this.setDefaultRenderer(String.class, left);
        this.setDefaultRenderer(MCNumber.class, right);
        this.setDefaultRenderer(MCDate.class, left);
        this.setDefaultRenderer(MCTerm.class, right);
        this.setBackground(ColorLib.FIS_TABLE_BACKGROUND_COLOR);
    }
}
