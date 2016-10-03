package cn.crap.monitor;

import cn.crap.utils.DateFormartUtil;

public class InterfaceMonitor implements Task {
    @Override
	public void doTask() {
    	System.out.println("定时检查1"+ DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm));
    }
}