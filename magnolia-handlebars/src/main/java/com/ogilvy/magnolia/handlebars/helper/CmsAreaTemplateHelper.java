package com.ogilvy.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Options;
import freemarker.template.TemplateModelException;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.template.AreaDefinition;
import info.magnolia.templating.elements.AreaElement;
import info.magnolia.templating.elements.ComponentElement;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Created by danny on 11/06/2014.
 */
public class CmsAreaTemplateHelper extends AbstractTemplateHelper<AreaElement> {

    private static final Logger log = Logger.getLogger(CmsInitTemplateHelper.class);

    public CharSequence apply(Object context, Options options) throws IOException {

        final AreaElement templatingElement = createTemplatingElement();
        initContentElement(options,templatingElement);

        AreaDefinition area = (AreaDefinition) options.hash("area");
        String name = options.hash("name");
        String availableComponents = options.hash("components");
        String dialog = options.hash("dialog");
        String type = options.hash("type");
        String label = options.hash("label");
        String description = options.hash("description");
        // @todo - how ot convert this to a boolean? will this work?
        Boolean editable = options.hash("editable");

        Map<String,Object> contextAttributes = (Map<String, Object>) options.hash("contextAttributes");

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
