package com.dannymarsland.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.CompositeTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import info.magnolia.module.blossom.render.RenderContext;
import info.magnolia.rendering.context.RenderingContext;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.renderer.AbstractRenderer;
import info.magnolia.rendering.template.RenderableDefinition;
import info.magnolia.rendering.util.AppendableWriter;

import javax.inject.Inject;
import javax.jcr.Node;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsRenderer extends AbstractRenderer {

    private Handlebars handlebars;

    @Inject
    public HandlebarsRenderer(RenderingEngine renderingEngine) {
        super(renderingEngine);

        TemplateLoader loader = new CompositeTemplateLoader(
                new FileTemplateLoader(new File("src/main/resources/templates")),
                new ClassPathTemplateLoader("/templates")
        );
        handlebars = new Handlebars(loader);

        handlebars.with(new ConcurrentMapTemplateCache());
        handlebars.registerHelper("cms-init", new CmsInitTemplateHelper());
        handlebars.registerHelper("cms-area", new CmsAreaTemplateHelper());
        handlebars.registerHelper("cms-component", new CmsComponentTemplateHelper());
    }

    @Override
    protected Map<String, Object> newContext() {
        return new HashMap<String, Object>();
    }

    @Override
    protected String resolveTemplateScript(Node content, RenderableDefinition definition, RenderingModel<?> model,
                                           String actionResult) {
        return RenderContext.get().getTemplateScript();
    }

    @Override
    protected void onRender(Node content, RenderableDefinition definition, RenderingContext renderingContext,
                            Map<String, Object> context, String templateScript) throws RenderException {

        // final Locale locale = MgnlContext.getAggregationState().getLocale();
        // @todo localization support in Handlebars ?
        final AppendableWriter out;
        try {
            out = renderingContext.getAppendable();
            // Was previously not creating a new context and just passing the context directly,
            // however https://github.com/eiswind/handlebars-magnolia-renderer created a new new context as shown below
            Context localContext = Context.newBuilder(context)
                    .resolver(JavaBeanValueResolver.INSTANCE, FieldValueResolver.INSTANCE, MapValueResolver.INSTANCE)
                    .build();
            try {
                Template template = handlebars.compile(templateScript);
                template.apply(localContext, out);
            } finally {
                localContext.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
