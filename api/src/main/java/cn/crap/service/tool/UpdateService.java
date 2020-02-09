package cn.crap.service.tool;

import cn.crap.adapter.DebugAdapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dto.DebugDto;
import cn.crap.model.Debug;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.Module;
import cn.crap.query.DebugQuery;
import cn.crap.service.DebugService;
import cn.crap.service.InterfaceService;
import cn.crap.service.ModuleService;
import cn.crap.utils.TableField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UpdateService{
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private DebugService debugService;


    public void mergerDebug() throws Exception{
        DebugQuery debugQuery = new DebugQuery().setSort(TableField.SORT.ID_ASC).setPageSize(2);
        int i = 0;
        while (true) {
            i++;
            debugQuery.setCurrentPage(i);
            List<Debug> debugs = debugService.query(debugQuery);
            if (CollectionUtils.isEmpty(debugs)) {
                return;
            }

            for (Debug d : debugs) {
                Module module = moduleService.getById(d.getModuleId());
                InterfaceWithBLOBs interfaceWithBLOBs = InterfaceAdapter.getInit();
                interfaceWithBLOBs.setProjectId(d.getProjectId());
                DebugDto debugDto = new DebugDto();
                BeanUtils.copyProperties(d, debugDto);
                if (interfaceService.getById(d.getId()) == null) {
                    interfaceService.insert(DebugAdapter.getInterfaceByDebug(module, interfaceWithBLOBs, debugDto));
                }
            }
        }
    }

}
