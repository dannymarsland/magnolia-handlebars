package com.dannymarsland.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Options;
import info.magnolia.templating.elements.InitElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CmsInitTemplateHelper extends AbstractTemplateHelper<InitElement> {

    private static final Logger log = LoggerFactory.getLogger(CmsInitTemplateHelper.class);

    public CharSequence apply(Object context, Options options) throws IOException {
        final InitElement templatingElement = createTemplatingElement();
        initContentElement(options, templatingElement);

        String dialog = options.hash("dialog");
        templatingElement.setDialog(dialog);

        return render(templatingElement);
    }
}
