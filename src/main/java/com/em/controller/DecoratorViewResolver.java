package com.em.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecoratorViewResolver extends InternalResourceViewResolver {

    private static final Log log = LogFactory.getLog(DecoratorViewResolver.class);

    private String defaultTemplate;
    private String templateFolder;

    private final Map<String, String> templateMapping = new HashMap<String, String>();

    private final List<String> noTemplateViews = new ArrayList<String>();

    private String getTemplateUrlForViewName(String viewName) {
        viewName = viewName.toLowerCase();

        if (templateMapping.isEmpty() || !templateMapping.containsKey(viewName)) {
            return getUrlForTemplate(defaultTemplate);
        }
        else {
            return getUrlForTemplate(templateMapping.get(viewName));
        }
    }

    public void setDefaultTemplate(String defaultTemplate){
        this.defaultTemplate = defaultTemplate;
    }

    private String getUrlForTemplate(String template) {
        return getPrefix() + templateFolder + template + getSuffix();
    }

    public void setTemplateFolder(String templateFolder){
        this.templateFolder = templateFolder;
    }

    public void setTemplateMapping(Map<String, List<String>> templateMapping) {
        for (String template : templateMapping.keySet()) {
            for (String viewName : templateMapping.get(template)) {
                this.templateMapping.put(viewName.toLowerCase(), template);
            }
        }
    }

    public void setNoTemplateViews(List<String> noTemplateViews) {
        this.noTemplateViews.addAll(noTemplateViews);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {

        log.info("ViewName: " + viewName);

        if (this.noTemplateViews.contains(viewName)) {
            return super.buildView(viewName);
        }
        //return super.buildView(viewName);
        InternalResourceView view = (InternalResourceView) super.buildView(viewName);

        view.getAttributesMap().put("pageToInclude", view.getUrl());
        view.setUrl(getTemplateUrlForViewName(viewName));

        return view;
    }
}
