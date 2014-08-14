package com.dannymarsland.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Options;
import info.magnolia.templating.elements.ComponentElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class CmsComponentTemplateHelper extends AbstractTemplateHelper<ComponentElement> {

    private static final Logger log = LoggerFactory.getLogger(CmsInitTemplateHelper.class);

    public CharSequence apply(Object context, Options options) throws IOException {

        final ComponentElement templatingElement = createTemplatingElement();
        initContentElement(options, templatingElement);

        String dialog = options.hash("dialog");
        // @todo how to cast to boolean? will ths work?
        Boolean editable = options.hash("editable");
        Map<String,Object> contextAttributes = (Map<String, Object>) options.hash("contextAttributes");

        templatingElement.setDialog(dialog);
        templatingElement.setContextAttributes(contextAttributes);
        templatingElement.setEditable(editable);

        return render(templatingElement);
    }
}
