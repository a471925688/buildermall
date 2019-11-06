//package com.noah_solutions.util.sqlssh;
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//
//import java.util.Properties;
//
//public class SSHConnection {
//
//    private final static String S_PATH_FILE_PRIVATE_KEY = "/home/lijun/.ssh/buildermall_lizhi_key";
//    private final static String S_PATH_FILE_KNOWN_HOSTS = "/home/lijun/.ssh/known_hosts";
//    private final static String S_PASS_PHRASE = "";
//    private final static int LOCAl_PORT = 3307;
//    private final static int REMOTE_PORT = 3306;
//    private final static int SSH_REMOTE_PORT = 22;
//    private final static String SSH_USER = "lizhi";
//    private final static String SSH_PASSWORD = "weilai123";
//    private final static String SSH_REMOTE_SERVER = "www.noahsolutions.net";
//    private final static String MYSQL_REMOTE_SERVER = "localhost";
//
//    private Session sesion; //represents each ssh session
//
//    public void closeSSH ()
//    {
//        sesion.disconnect();
//    }
//
//    public SSHConnection () throws Throwable
//    {
//
//        JSch jsch = null;
//
//        jsch = new JSch();
////        jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
////        jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);
//
//        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
//
//        sesion.setPassword(SSH_PASSWORD);
//
//        Properties config = new Properties();
//        config.put("StrictHostKeyChecking", "no");
//        sesion.setConfig(config);
//
//        try {
//            sesion.connect(100000);
//        } catch (Exception e) {
//            throw new Exception("连接远程端口无效或用户名密码错误");
//        } //ssh connection established!
//
//        //by security policy, you must connect through a fowarded port
//        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);
//
//    }
//}
