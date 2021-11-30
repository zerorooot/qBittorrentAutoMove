package github.zerorooot.service;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

import github.zerorooot.bean.ApiBean;
import github.zerorooot.bean.FileBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class qBittorrentUtil {
    private final String hosts;
    public qBittorrentUtil(String hosts) {
        if (!hosts.startsWith("https://")) {
            hosts = "http://" + hosts;
        }
        this.hosts = hosts;
    }

    public String login(String userName, String password) {
        String url = hosts + ApiBean.LOGIN;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", userName);
        paramMap.put("password", password);
        HttpResponse execute = HttpUtil.createPost(url).form(paramMap).execute();
        return execute.header("Set-Cookie");
    }

    public ArrayList<FileBean> getTorrentsList(String cookie) {
        ArrayList<FileBean> fileBeans = new ArrayList<>();
        String url = hosts + ApiBean.TORRENT_LIST;
        HttpResponse execute = HttpUtil.createGet(url).cookie(cookie).execute();
        JSONObject torrentsJson = new JSONObject(execute.body()).getJSONObject("torrents");
        for (String s : torrentsJson.keySet()) {
            String state = torrentsJson.getJSONObject(s).getStr("state");
            if ("stalledUP".equalsIgnoreCase(state) || "pausedUP".equalsIgnoreCase(state) || "uploading".equalsIgnoreCase(state)) {
                FileBean fileBean = torrentsJson.getJSONObject(s).toBean(FileBean.class);
                fileBean.setId(s);
                fileBeans.add(fileBean);
            }
        }
        return fileBeans;
    }

    public void delete(FileBean fileBean,String cookie) {
        String url = hosts + ApiBean.DELETE;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hashes", fileBean.getId());
        paramMap.put("deleteFiles", false);
        HttpUtil.createPost(url).form(paramMap).cookie(cookie).execute();
    }
}
