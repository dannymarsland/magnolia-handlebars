package com.ogilvy.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Options;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.templating.elements.InitElement;
import org.apache.log4j.Logger;

import java.io.IOException;

public class CmsInitTemplateHelper extends AbstractTemplateHelper<InitElement> {

    private static final Logger log = Logger.getLogger(CmsInitTemplateHelper.class);

    public CharSequence apply(Object context, Options options) throws IOException {
        final InitElement templatingElement = createTemplatingElement();
        initContentElement(options, templatingElement);

        String dialog = options.hash("dialog");
        templatingElement.setDialog(dialog);

        return render(templatingElement);
    }
}
