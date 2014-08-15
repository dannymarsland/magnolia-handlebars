package com.dannymarsland.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.context.RenderingContext;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.templating.elements.AbstractContentTemplatingElement;
import info.magnolia.templating.elements.TemplatingElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractTemplateHelper<C extends TemplatingElement> implements Helper {

    private static final Logger log = LoggerFactory.getLogger(CmsInitTemplateHelper.class);

    public static final String PATH_ATTRIBUTE = "path";
    public static final String UUID_ATTRIBUTE = "uuid";
    public static final String WORKSPACE_ATTRIBUTE = "workspace";
    public static final String CONTENT_ATTRIBUTE = "content";

    protected C createTemplatingElement() {
        // FIXME use scope instead of fetching the RenderingContext for passing it as an argument
        final RenderingEngine renderingEngine = Components.getComponent(RenderingEngine.class);
        final RenderingContext renderingContext = renderingEngine.getRenderingContext();

        return Components.getComponentProvider().newInstance(getTemplatingElementClass(), renderingContext);
    }

    protected Class<C> getTemplatingElementClass() {
        // TODO does this support more than one level of subclasses?
        return (Class<C>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected void initContentElement(Options options, AbstractContentTemplatingElement component)  {
        // @todo The freemarker code ensured that options could be cast to the correct type - here I am just assuming
        ContentMap contentMap = options.hash(CONTENT_ATTRIBUTE);
        Node contentNode = contentMap != null ? contentMap.getJCRNode(): null;
        String workspace = options.hash(WORKSPACE_ATTRIBUTE);
        String nodeIdentifier = options.hash(UUID_ATTRIBUTE);
        String path = options.hash(PATH_ATTRIBUTE);

        component.setContent(contentNode);
        component.setWorkspace(workspace);
        component.setNodeIdentifier(nodeIdentifier);
        component.setPath(path);
    }

    protected CharSequence render(AbstractContentTemplatingElement templatingElement) {
        StringBuffer buffer = new StringBuffer();
        try {
            templatingElement.begin(buffer);
            templatingElement.end(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO Error rendering:",e);
        } catch (RenderException e) {
            e.printStackTrace();
            log.warn("Render Error rendering:t",e);
        }
        return buffer;
    }
}
