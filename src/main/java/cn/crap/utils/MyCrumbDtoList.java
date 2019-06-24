package cn.crap.utils;


import cn.crap.dto.CrumbDto;

import java.util.List;

public class MyCrumbDtoList {
    private List<CrumbDto> list;

    private MyCrumbDtoList() {
    }

    public static MyCrumbDtoList getList(String... params) {
        MyCrumbDtoList myArrayList = new MyCrumbDtoList();
        myArrayList.list = Tools.getCrumbs(params);
        return myArrayList;
    }

    public MyCrumbDtoList add(String name, String url) {
        if (MyString.isEmpty(name) || MyString.isEmpty(url)) {
            return this;
        }
        CrumbDto crumb = new CrumbDto(name, url);
        list.add(crumb);
        return this;
    }

    public List<CrumbDto> getList() {
        return list;
    }
}
