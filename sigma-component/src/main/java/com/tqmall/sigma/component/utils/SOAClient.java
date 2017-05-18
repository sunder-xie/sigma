//package com.tqmall.sigma.component.utils;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.Proxy;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.security.MessageDigest;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// *
// * Created by huangzhangting on 17/3/30.
// */
//@Deprecated
//public class SOAClient {
//    private static final String METHOD_POST = "POST";
//    private static final int BUFFER_SIZE = 1024;
//    public static final String SSO_SERVICE = "SSOService";
//    public static final String STATUS_OK = "OK";
//    public static final String STATUS_ERR = "error";
//    public static final String ERR_MESSAGE = "message";
//    public static final String ERR_CODE = "errorCode";
//    public static final String RETURN_VALUE = "returnValue";
//    private String serverAddress = "";
//    private String serverUrl = "";
//    private String sessionId;
//    private String ssoid = "ime_public_ssoid";
//    private String _signKey = "";
//    private String _sysid = "";
//    private String version = "1.0";
//    private String _signMethod = "MD5";
//    private PrivateKey privateKey;
//    private PublicKey publicKey;
//    private String _publicKey;
//    private Proxy proxy = null;
//
//    public SOAClient() {
//    }
//
//    public String getServerAddress() {
//        return this.serverAddress;
//    }
//
//    public void setServerAddress(String serverAddress) {
//        this.serverAddress = serverAddress;
//        this.serverUrl = serverAddress;
//    }
//
//    public String getSignMethod() {
//        return this._signMethod;
//    }
//
//    public void setSignMethod(String signMethod) {
//        this._signMethod = signMethod;
//    }
//
//    public String getSignKey() {
//        return this._signKey;
//    }
//
//    public void setSignKey(String signKey) {
//        this._signKey = signKey;
//    }
//
//    public void setSignKey(PrivateKey privateKey) {
//        this.privateKey = privateKey;
//    }
//
//    public void setPublicKey(String publicKey) {
//        this._publicKey = publicKey;
//
//        try {
//            this.publicKey = RSAUtil.getPublicKey(publicKey);
//        } catch (Exception var3) {
//            ;
//        }
//
//    }
//
//    public void setPublicKey(PublicKey publicKey) {
//        this.publicKey = publicKey;
//    }
//
//    public String getSysId() {
//        return this._sysid;
//    }
//
//    public void setSysId(String sysid) {
//        this._sysid = sysid;
//    }
//
//    public String getVersion() {
//        return this.version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public void setProxy(Proxy proxy) {
//        this.proxy = proxy;
//    }
//
//    private Map<String, String> parseCookie(String cookie) {
//        HashMap map = new HashMap();
//        String[] cookies = cookie.split(";");
//
//        for(int i = 0; i < cookies.length; ++i) {
//            String[] part = cookies[i].split("=");
//            if(part.length == 2) {
//                map.put(part[0].trim(), part[1].trim());
//            }
//        }
//
//        return map;
//    }
//
//    private void checkResult(JSONObject result) throws Exception {
//        if(!"OK".equals(result.optString("status"))) {
//            throw new Exception(result.optString("message"));
//        }
//    }
//
//    public void login(String userName, String password, String domainName) throws Exception {
//        JSONArray args = new JSONArray();
//        args.put(userName);
//        args.put(password);
//        args.put(domainName);
//        JSONObject rt = this.request("SSOService", "login", args);
//        this.checkResult(rt);
//        rt = rt.optJSONObject("returnValue");
//        if(rt != null) {
//            String token = rt.optString("ssoid");
//            if(token == null || token.length() == 0) {
//                token = rt.optString("token");
//            }
//
//            if(token != null && token.length() > 0) {
//                this.ssoid = token;
//            }
//        }
//
//    }
//
//    public JSONObject request(String serviceAndMethod, JSONArray args) throws Exception {
//        String[] parts = serviceAndMethod.split("\\.");
//        if(parts.length != 2) {
//            throw new Exception("服务名称错误");
//        } else {
//            return this.request(parts[0], parts[1], args);
//        }
//    }
//
//    public JSONObject request(String service, String method, JSONArray args) throws Exception {
//        JSONObject request = new JSONObject();
//        request.put("service", service);
//        request.put("method", method);
//        request.put("args", args);
//        String strRequest = request.toString();
//        strRequest = strRequest.replace("\r\n", "");
//        HashMap req = new HashMap();
//        req.put("req", strRequest);
//        req.put("ssoid", this.ssoid);
//        if((!"".equals(this._signKey) || this.privateKey != null) && !"".equals(this._sysid)) {
//            SimpleDateFormat result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String timestamp = result.format(new Date());
//            String sign = this.sign(this._sysid, strRequest, timestamp);
//            req.put("sysid", this._sysid);
//            req.put("timestamp", timestamp);
//            req.put("sign", sign);
//            req.put("v", this.version);
//        }
//
//        String result1 = this.request(req);
//        if(result1 == null) {
//            throw new Exception("返回数据错误");
//        } else {
//            return this.verifyReturn(new JSONObject(result1));
//        }
//    }
//
//    public JSONObject request(String serviceAndMethod, JSONObject param) throws Exception {
//        String[] parts = serviceAndMethod.split("\\.");
//        if(parts.length != 2) {
//            throw new Exception("服务名称错误");
//        } else {
//            return this.request(parts[0], parts[1], param);
//        }
//    }
//
//    private JSONObject verifyReturn(JSONObject result) throws Exception {
//        if(result.has("signedValue")) {
//            String value = result.getString("signedValue");
//            String sign = result.getString("sign");
//            if(!RSAUtil.verify(this.publicKey, value, sign)) {
//                throw new Exception("签名验证错误");
//            } else {
//                if("OK".equals(result.getString("status")) && !"null".equals(value)) {
//                    JSONObject ret = new JSONObject(value);
//                    if(ret.has("$PrimitiveReturn$")) {
//                        result.put("returnValue", ret.get("$PrimitiveReturn$"));
//                    } else {
//                        result.put("returnValue", ret);
//                    }
//                }
//
//                return result;
//            }
//        } else {
//            return result;
//        }
//    }
//
//    public JSONObject request(String service, String method, JSONObject param) throws Exception {
//        JSONObject request = new JSONObject();
//        request.put("service", service);
//        request.put("method", method);
//        request.put("param", param);
//        String strRequest = request.toString();
//        strRequest = strRequest.replace("\r\n", "");
//        HashMap req = new HashMap();
//        req.put("req", strRequest);
//        req.put("ssoid", this.ssoid);
//        if((!"".equals(this._signKey) || this.privateKey != null) && !"".equals(this._sysid)) {
//            SimpleDateFormat result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String timestamp = result.format(new Date());
//            String sign = this.sign(this._sysid, strRequest, timestamp);
//            req.put("sysid", this._sysid);
//            req.put("timestamp", timestamp);
//            req.put("sign", sign);
//            req.put("v", this.version);
//        }
//
//        String result1 = this.request(req);
//        if(result1 == null) {
//            throw new Exception("返回数据错误");
//        } else {
//            return this.verifyReturn(new JSONObject(result1));
//        }
//    }
//
//    private String request(Map<String, String> args) throws Exception {
//        URL url = new URL(this.serverUrl);
//        HttpURLConnection connection = null;
//        if(this.proxy == null) {
//            connection = (HttpURLConnection)url.openConnection();
//        } else {
//            connection = (HttpURLConnection)url.openConnection(this.proxy);
//        }
//
//        connection.setRequestMethod("POST");
//        connection.setAllowUserInteraction(false);
//        connection.setInstanceFollowRedirects(false);
//        connection.setUseCaches(false);
//        StringBuffer sb = new StringBuffer();
//        Object reqbody = null;
//        Iterator status = args.entrySet().iterator();
//
//        while(status.hasNext()) {
//            Map.Entry len = (Map.Entry)status.next();
//            sb.append((String)len.getKey()).append("=").append(URLEncoder.encode((String) len.getValue(), "utf-8")).append("&");
//        }
//
//        sb.append("\r\n");
//        byte[] var24 = sb.toString().getBytes();
//        if(this.sessionId != null) {
//            sb.setLength(0);
//            sb.append("JSESSIONID=").append(this.sessionId).append(";");
//            connection.addRequestProperty("Cookie", sb.toString());
//        }
//
//        connection.addRequestProperty("Content-length", Integer.toString(var24.length));
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//        connection.connect();
//        if(connection.getDoOutput()) {
//            OutputStream var26 = null;
//
//            try {
//                var26 = connection.getOutputStream();
//                var26.write(var24);
//            } finally {
//                if(var26 != null) {
//                    var26.close();
//                }
//
//            }
//        }
//
//        int var27 = connection.getResponseCode();
//        int statusPrefix = var27 / 100;
//        if(statusPrefix != 1 && statusPrefix != 3) {
//            int i = 1;
//
//            for(String inputStream = connection.getHeaderFieldKey(i); inputStream != null; inputStream = connection.getHeaderFieldKey(i)) {
//                if(inputStream != null) {
//                    String reader = connection.getHeaderField(i);
//                    if(inputStream.equalsIgnoreCase("Set-Cookie")) {
//                        Map cbuf = this.parseCookie(reader);
//                        if(cbuf.containsKey("JSESSIONID")) {
//                            this.sessionId = (String)cbuf.get("JSESSIONID");
//                        }
//                    }
//                }
//
//                ++i;
//            }
//
//            InputStream var28 = null;
//
//            try {
//                try {
//                    var28 = connection.getInputStream();
//                } catch (Exception var21) {
//                    var28 = connection.getErrorStream();
//                }
//
//                if(var28 == null) {
//                    return null;
//                } else {
//                    sb.setLength(0);
//                    InputStreamReader var29 = new InputStreamReader(var28, "utf-8");
//                    char[] var30 = new char[1024];
//
//                    int var25;
//                    while((var25 = var29.read(var30, 0, var30.length)) != -1) {
//                        sb.append(new String(var30, 0, var25));
//                    }
//
//                    var29.close();
//                    String var14 = sb.toString();
//                    return var14;
//                }
//            } finally {
//                if(var28 != null) {
//                    var28.close();
//                }
//
//            }
//        } else {
//            throw new Exception("Unexpected server response: " + var27);
//        }
//    }
//
//    private String sign(String sysid, String req, String timestamp) throws Exception {
//        StringBuilder sb = new StringBuilder();
//        if("SHA1WithRSA".equals(this._signMethod)) {
//            sb.append(sysid).append(req).append(timestamp);
//            if(this.privateKey == null) {
//                this.privateKey = RSAUtil.getPrivateKey(this._signKey);
//            }
//
//            return RSAUtil.sign(this.privateKey, sb.toString());
//        } else {
//            sb.append(this._signKey).append(sysid).append(req).append(timestamp).append(this._signKey);
//
//            MessageDigest alga;
//            try {
//                alga = MessageDigest.getInstance("MD5");
//            } catch (Exception var7) {
//                return null;
//            }
//
//            alga.update(sb.toString().getBytes("utf-8"));
//            byte[] digesta = alga.digest();
//            return this.byte2hex(digesta);
//        }
//    }
//
//    protected String byte2hex(byte[] bytes) {
//        StringBuffer retString = new StringBuffer();
//
//        for(int i = 0; i < bytes.length; ++i) {
//            retString.append(Integer.toHexString(256 + (bytes[i] & 255)).substring(1).toUpperCase());
//        }
//
//        return retString.toString();
//    }
//
//    protected byte[] hex2byte(String hex) {
//        byte[] bts = new byte[hex.length() / 2];
//
//        for(int i = 0; i < bts.length; ++i) {
//            bts[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
//        }
//
//        return bts;
//    }
//}
