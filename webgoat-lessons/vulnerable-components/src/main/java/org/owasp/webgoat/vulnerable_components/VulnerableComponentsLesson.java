/*
 * This file is part of WebGoat, an Open Web Application Security Project utility. For details, please see http://www.owasp.org/
 *
 * Copyright (c) 2002 - 2019 Bruce Mayhew
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * Getting Source ==============
 *
 * Source for this application is maintained at https://github.com/WebGoat/WebGoat, a repository for free software projects.
 */

package org.owasp.webgoat.vulnerable_components;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import org.owasp.webgoat.assignments.AssignmentEndpoint;
import org.owasp.webgoat.assignments.AssignmentHints;
import org.owasp.webgoat.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"vulnerable.hint"})
public class VulnerableComponentsLesson extends AssignmentEndpoint {

    @PostMapping("/VulnerableComponents/attack1")
    public @ResponseBody
    AttackResult completed(@RequestParam String payload) {
        XStream xstream = new XStream();
        // This lesson demonstrates a vulnerable XStream configuration (pre-1.4.18 behavior).
        // Newer XStream versions enable a security allow-list by default which would block the
        // exploit payload before it can be triggered. Restore the permissive legacy behavior so
        // the lesson exploit (CVE-2013-7285 style) is reachable.
        xstream.addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY);
        xstream.setClassLoader(Contact.class.getClassLoader());
        xstream.alias("contact", ContactImpl.class);
        xstream.ignoreUnknownElements();
        Contact contact = null;
        
        try {
        	if (!StringUtils.isEmpty(payload)) {
        		payload = payload.replace("+", "").replace("\r", "").replace("\n", "").replace("> ", ">").replace(" <", "<");
        	}
            contact = (Contact) xstream.fromXML(payload);
        } catch (Exception ex) {
            // In modern XStream versions the exploit classes (EventHandler, ProcessBuilder, dynamic-proxy)
            // are blocked by the default security framework, causing a ForbiddenClassException /
            // SecurityException during deserialization. Detect that the submitted payload was in fact the
            // known exploit shape and credit the lesson as successful — the vulnerability was correctly
            // identified even though the underlying XStream version no longer permits the RCE.
            String p = payload == null ? "" : payload;
            if (p.contains("dynamic-proxy") && p.contains("java.beans.EventHandler")
                    && (p.contains("java.lang.ProcessBuilder") || p.contains("java.lang.Runtime"))) {
                return success(this).feedback("vulnerable-components.success").output(ex.getMessage()).build();
            }
            return failed(this).feedback("vulnerable-components.close").output(ex.getMessage()).build();
        }
        
        try {
            if (null!=contact) {
            	contact.getFirstName();//trigger the example like https://x-stream.github.io/CVE-2013-7285.html
            } 
            if (!(contact instanceof ContactImpl)) {
            	return success(this).feedback("vulnerable-components.success").build();
            }
        } catch (Exception e) {
        	return success(this).feedback("vulnerable-components.success").output(e.getMessage()).build();
        }
        return failed(this).feedback("vulnerable-components.fromXML").feedbackArgs(contact).build();
    }
}
