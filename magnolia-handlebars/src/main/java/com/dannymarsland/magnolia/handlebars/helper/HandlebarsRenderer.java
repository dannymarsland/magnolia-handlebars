package com.dannymarsland.magnolia.handlebars.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import info.magnolia.rendering.context.RenderingContext;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.renderer.AbstractRenderer;
import info.magnolia.rendering.template.RenderableDefinition;
import info.magnolia.rendering.util.AppendableWriter;

import javax.jcr.Node;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsRenderer extends AbstractRenderer {

    private Handlebars handlebars;

    public HandlebarsRenderer() {
        TemplateLoader loader = new FileTemplateLoader(new File("src/main/webapp/"),".hbs");
        this.handlebars = new Handlebars(loader);
        // Caching - there are several cache options, the standard one has been used. see https://github.com/jknack/handlebars.java
        this.handlebars.with(new ConcurrentMapTemplateCache());

        this.handlebars.registerHelper("cms-init", new CmsInitTemplateHelper());
        this.handlebars.registerHelper("cms-area", new CmsAreaTemplateHelper());
        this.handlebars.registerHelper("cms-component", new CmsComponentTemplateHelper());
    }

    @Override
    protected Map<String, Object> newContext() {
        return new HashMap<String, Object>();
    }

    @Override
    protected void onRender(Node content, RenderableDefinition definition, RenderingContext renderingCtx,
                            Map<String, Object> ctx, String templateScript) throws RenderException {

        // final Locale locale = MgnlContext.getAggregationState().getLocale();
        // @todo localization support in Handlebars ?
        final AppendableWriter out;
        try {
            out = renderingCtx.getAppendable();
            // Was previously not creating a new context and just passing the context directly,
            // however https://github.com/eiswind/handlebars-magnolia-renderer created a new new context as shown below
            Context context = Context.newBuilder(ctx)
                    .resolver(JavaBeanValueResolver.INSTANCE, FieldValueResolver.INSTANCE, MapValueResolver.INSTANCE)
                    .build();
            try {
                Template template = handlebars.compile(templateScript);
                template.apply(ctx,out);
            } finally {
                context.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
