package com.tqmall.sigma.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by huangzhangting on 15/8/26.
 */
public class SigmaServer {
    public static void main(String[] args) {
        Server server = new Server(8089);

        WebAppContext context = new WebAppContext();


        //项目部署根路径
        context.setContextPath("/");

        /*
        *  因为配置了 working directory
        *  所以下面配置相对路径即可
        * */
        context.setDescriptor("./sigma-web/src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("./sigma-web/src/main/webapp");

        context.setMaxFormContentSize(10485760);
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
