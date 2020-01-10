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

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.edurt.trending.plugin.core.CrawlerModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * <p> GitHubProcessor </p>
 * <p> Description : GitHubProcessor </p>
 * <p> Author : qianmoQ </p>
 * <p> Version : 1.0 </p>
 * <p> Create Time : 2020-01-02 21:19 </p>
 * <p> Author Email: <a href="mailTo:shichengoooo@163.com">qianmoQ</a> </p>
 */
public class GitHubProcessor {

    private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(16));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GitHubConfig config = new GitHubConfig();
        List<String> languages = Arrays.asList("Kotlin", "Java", "Php", "Python", "JavaScript",
                "C", "C++", "C#", "Ruby", "APL", "Autolt", "Basic",
                "Lua", "Go", "ABAP", "ABNF", "ActionScript", "Ada", "Agda", "Alloy", "AMPL", "Apex", "APL"
                , "AppleScript", "Arc", "ASN.1", "ASP", "ATS", "Zeek", "Clojure", "CMake", "COBOL", "CofficeScript",
                "Cool", "Coq", "CSON", "AsciiDoc", "CSS", "CSV", "Cuda"
                , "Cython", "D", "Dart", "Diff", "DM", "Dockerfile", "EJS", "EML", "F#", "fish",
                "GLSL", "Gradle", "GraphQL", "Groovy", "Hack", "HAProxy", "Haxe", "HiveQL"
                , "Vue", "Swift", "SQL", "Ruby", "PowerShell", "Perl", "OpenCL", "Objective-J", "Kit", "JSON", "JFlex", "INI", "IDL", "Hy", "HXML", "HTTP", "HTML"
                , "YAML", "TSQL", "SVG", "Shell", "R", "PostCSS", "Org", "Objective-C", "MediaWiki", "Mask", "Markdown", "Lex", "latte");
        ListenableFuture<ListenableFuture<List<CrawlerModel>>> parallelRes = Futures.transform(
                // 数据输入
                Futures.transform(
                        Futures.allAsList(Lists.transform(languages,
                                language -> Futures.transform(
                                        executorService.submit(() -> Jsoup.connect(String.join("/", config.getTrendingPath(), language)).get()),
                                        documentInput -> Objects.requireNonNull(documentInput).select("article.Box-row"),
                                        executorService))),
                        input -> {
                            List<Element> list = Lists.newArrayList();
                            input.forEach(list::addAll);
                            return list;
                        },
                        executorService),
                response -> Futures.transform(
                        Futures.allAsList(Lists.transform(response,
                                input -> executorService.submit(
                                        () -> doMap(input)
                                ))),
                        elements -> {
                            List<CrawlerModel> list = Lists.newArrayList();
                            list.addAll(elements);
                            return list;
                        },
                        executorService),
                executorService);
//        executorService.shutdown();
    }

    private static CrawlerModel doMap(Element source) {
        CrawlerModel crawler = new CrawlerModel();
        crawler.setTime(LocalDate.now());
        String[] sourceTitle = source.selectFirst("h1.h3").text().split("/");
        if (sourceTitle.length > 1) {
            crawler.setAuthor(sourceTitle[0].trim());
            crawler.setTitle(sourceTitle[1].trim());
        }
        crawler.setDescription(source.selectFirst("p.col-9") == null ? "" : source.selectFirst("p.col-9").text().trim());
        Element element = source.selectFirst("div.f6");
        Element language = element.selectFirst("span[itemprop]");
        if (language != null) {
            crawler.setLanguage(language.text().trim());
        }
        Elements starAndFork = element.select("a.muted-link");
        if (starAndFork.size() > 1) {
            crawler.setStar(starAndFork.get(0).text().replace(",", "").trim());
            crawler.setFork(starAndFork.get(1).text().replace(",", "").trim());
        }
        crawler.setStarsToday(element.selectFirst("span.float-sm-right").text().replace("stars today", "").trim());
        return crawler;
    }

}
