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
package com.github.dactiv.universe.freemarker;

import freemarker.cache.TemplateLookupContext;
import freemarker.cache.TemplateLookupResult;
import freemarker.cache.TemplateLookupStrategy;

import java.io.IOException;

/**
 * 没有 Template local 策略，免得先国际化后在到指定文件
 *
 * @author maurice
 */
public class NoTemplateLocalStrategy extends TemplateLookupStrategy{

    @Override
    public TemplateLookupResult lookup(TemplateLookupContext ctx) throws IOException {
        return ctx.lookupWithLocalizedThenAcquisitionStrategy(ctx.getTemplateName(), null);
    }
}
