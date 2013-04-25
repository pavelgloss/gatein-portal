/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.portal;

import java.io.File;

import javax.portlet.Portlet;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.portletapp20.PortletDescriptor;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 */
public class AbstractPortalTestCase {

    public static WebArchive createPortal() {
        WebArchive portal = ShrinkWrap.create(WebArchive.class, "portal.war");
        portal.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
        portal.merge(ShrinkWrap.
                create(GenericArchive.class).
                as(ExplodedImporter.class).
                importDirectory("src/test/resources").
                as(GenericArchive.class), "/WEB-INF", Filters.includeAll());
        return portal;
    }

    public static PortletDescriptor portletXML() {
        return Descriptors.create(PortletDescriptor.class);
    }

    public static PortletDescriptor descriptor(PortletDescriptor descriptor, Class<? extends Portlet> portlet) {
        return descriptor.
                createPortlet().
                portletName(portlet.getSimpleName()).
                portletClass(portlet.getName()).
                createSupports().mimeType("text/html").portletMode("edit", "help").up().
                getOrCreatePortletInfo().title("Hello").up().
                up();
    }

    public static PortletDescriptor descriptor(Class<? extends Portlet> portlet) {
        return descriptor(portletXML(), portlet);
    }

    public static PortletDescriptor descriptor(Class<? extends Portlet> portlet1, Class<? extends Portlet> portlet2) {
        return descriptor(descriptor(portlet1), portlet2);
    }
}
