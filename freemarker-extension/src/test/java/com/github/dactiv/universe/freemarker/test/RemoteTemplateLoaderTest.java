/*
 * Copyright 2015 dactiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.dactiv.universe.freemarker.test;

import com.github.dactiv.universe.freemarker.NoTemplateLocalStrategy;
import com.github.dactiv.universe.freemarker.RemoteTemplateLoader;
import freemarker.template.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;

/**
 * 测试远程模板加载器
 *
 * @author maurice
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class RemoteTemplateLoaderTest {

    @Test
    public void test() {
        /*Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

        configuration.setTemplateLoader(new RemoteTemplateLoader());
        configuration.setTemplateLookupStrategy(new NoTemplateLocalStrategy());

        try {
            configuration.getTemplate("http://localhost:8084/index/get-ftl-template?name=header.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
