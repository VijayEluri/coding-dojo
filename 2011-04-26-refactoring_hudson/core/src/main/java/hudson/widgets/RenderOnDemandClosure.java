/*
 * The MIT License
 *
 * Copyright (c) 2011, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.widgets;

import hudson.Util;
import hudson.model.Descriptor;
import hudson.model.DescriptorByNameOwner;
import hudson.util.IOException2;
import hudson.util.PackedMap;
import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.Script;
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;
import org.kohsuke.stapler.jelly.DefaultScriptInvoker;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Captured Jelly {@link Script} that can be rendered later on demand from JavaScript.
 *
 * @author Kohsuke Kawaguchi
 */
public class RenderOnDemandClosure {
    /**
     * Captures the recursive taglib call stack.
     */
    private final Script[] bodyStack;
    private final Map<String,Object> variables;
    private final String currentDescriptorByNameUrl;

    public RenderOnDemandClosure(JellyContext context, String attributesToCapture) {
        List<Script> bodyStack = new ArrayList<Script>();
        for (JellyContext c = context; c!=null; c=c.getParent()) {
            Script script = (Script) c.getVariables().get("org.apache.commons.jelly.body");
            if(script!=null) bodyStack.add(script);
        }
        this.bodyStack = bodyStack.toArray(new Script[bodyStack.size()]);
        assert !bodyStack.isEmpty();    // there must be at least one, which is the direct child of <l:renderOnDemand>

        Map<String,Object> variables = new HashMap<String, Object>();
        for (String v : Util.fixNull(attributesToCapture).split(","))
            variables.put(v,context.getVariable(v));

        // capture the current base of context for descriptors
        currentDescriptorByNameUrl = Descriptor.getCurrentDescriptorByNameUrl();

        this.variables = PackedMap.of(variables);
    }

    /**
     * Renders the captured fragment.
     */
    @JavaScriptMethod
    public HttpResponse render() {
        return new HttpResponse() {
            public void generateResponse(StaplerRequest req, StaplerResponse rsp, Object node) throws IOException, ServletException {
                try {
                    new DefaultScriptInvoker() {
                        @Override
                        protected JellyContext createContext(StaplerRequest req, StaplerResponse rsp, Script script, Object it) {
                            JellyContext context = super.createContext(req, rsp, script, it);
                            for (int i=bodyStack.length-1; i>0; i--) {// exclude bodyStack[0]
                                context = new JellyContext(context);
                                context.setVariable("org.apache.commons.jelly.body",bodyStack[i]);
                            }
                            return context;
                        }

                        @Override
                        protected void exportVariables(StaplerRequest req, StaplerResponse rsp, Script script, Object it, JellyContext context) {
                            super.exportVariables(req, rsp, script, it, context);
                            context.setVariables(variables);
                            req.setAttribute("currentDescriptorByNameUrl",currentDescriptorByNameUrl);
                        }
                    }.invokeScript(req,rsp,bodyStack[0],null);
                } catch (JellyTagException e) {
                    LOGGER.log(Level.WARNING, "Failed to evaluate the template closure", e);
                    throw new IOException2("Failed to evaluate the template closure",e);
                }
            }
        };
    }

    private static final Logger LOGGER = Logger.getLogger(RenderOnDemandClosure.class.getName());
}
