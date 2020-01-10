/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.edurt.trending.plugin.github;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> GitHubConnect </p>
 * <p> Description : GitHubConnect </p>
 * <p> Author : qianmoQ </p>
 * <p> Version : 1.0 </p>
 * <p> Create Time : 2020-01-02 20:32 </p>
 * <p> Author Email: <a href="mailTo:shichengoooo@163.com">qianmoQ</a> </p>
 */
public class GitHubConnect {

    public static void main(String[] args) throws IOException {
//        GitHubConfig config = new GitHubConfig();
//        Document document = Jsoup.connect(config.getTrendingPath()).get();
//        List<Object> list = new ArrayList<>();
//        document.select("article.Box-row").forEach(v -> {
//            Map<String, String> map = new ConcurrentHashMap<>();
//            String[] sourceTitle = v.selectFirst("h1.h3").text().split("/");
//            if (sourceTitle.length > 1) {
//                map.put("author", sourceTitle[0].trim());
//                map.put("title", sourceTitle[1].trim());
//            }
//            map.put("description", v.selectFirst("p.col-9").text().trim());
//            Element element = v.selectFirst("div.f6");
//            Element language = element.selectFirst("span[itemprop]");
//            if (language != null) {
//                map.put("language", language.text().trim());
//            }
//            Elements starAndFork = element.select("a.muted-link");
//            if (starAndFork.size() > 1) {
//                map.put("star", starAndFork.get(0).text().replace(",", "").trim());
//                map.put("fork", starAndFork.get(1).text().replace(",", "").trim());
//            }
//            map.put("starsToday", element.selectFirst("span.float-sm-right").text().replace("stars today", "").trim());
//            list.add(map);
//        });
//        System.out.println(new Gson().toJson(list));
    }

}
