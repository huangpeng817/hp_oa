package cn.hp.oa.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import cn.hp.oa.domain.Privilege;
import cn.hp.oa.service.PrivilegeService;

/**
 * Application Lifecycle Listener implementation class InitListener
 *
 */
@WebListener
public class InitListener implements ServletContextListener {

    public InitListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
    	PrivilegeService privilegeService = new PrivilegeService();
    	List<Privilege> topPrivilegeList = privilegeService.findTopList();
    	sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
    	System.out.println("ServletContext已准备顶级权限(包含子权限)");
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
	
}
