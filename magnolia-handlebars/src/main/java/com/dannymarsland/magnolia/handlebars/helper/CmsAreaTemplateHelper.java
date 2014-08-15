package com.dannymarsland.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Options;
import info.magnolia.rendering.template.AreaDefinition;
import info.magnolia.templating.elements.AreaElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class CmsAreaTemplateHelper extends AbstractTemplateHelper<AreaElement> {

    private static final Logger log = LoggerFactory.getLogger(CmsInitTemplateHelper.class);

    public CharSequence apply(Object context, Options options) throws IOException {

        final AreaElement templatingElement = createTemplatingElement();
        initContentElement(options,templatingElement);

        AreaDefinition area = options.hash("area");
        String name = options.hash("name");
        String availableComponents = options.hash("components");
        String dialog = options.hash("dialog");
        String type = options.hash("type");
        String label = options.hash("label");
        String description = options.hash("description");
        Boolean editable = options.hash("editable");
        Map<String,Object> contextAttributes = options.hash("contextAttributes");

        templatingElement.setArea(area);
        templatingElement.setName(name);
        templatingElement.setAvailableComponents(availableComponents);
        templatingElement.setDialog(dialog);
        templatingElement.setType(type);
        templatingElement.setLabel(label);
        templatingElement.setDescription(description);
        templatingElement.setEditable(editable);

        templatingElement.setContextAttributes(contextAttributes);

        return render(templatingElement);
    }
}
