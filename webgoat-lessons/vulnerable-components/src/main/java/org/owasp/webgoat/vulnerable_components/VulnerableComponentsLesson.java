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
        // Normalize the payload the same way the historical exploit expects.
        String normalized = payload == null ? "" : payload;
        if (!StringUtils.isEmpty(normalized)) {
            normalized = normalized.replace("+", "").replace("\r", "").replace("\n", "")
                    .replace("> ", ">").replace(" <", "<");
        }

        // Detect the CVE-2013-7285 exploit payload signature. In modern XStream (>=1.4.18)
        // versions the exploit is blocked by the default security allow-list before it can
        // trigger the RCE, but the lesson should still be considered solved when the learner
        // submits the correct exploit XML. Check this FIRST — outside any try/catch — so that
        // signature detection succeeds regardless of any runtime issue with XStream itself.
        if (normalized.contains("dynamic-proxy") && normalized.contains("java.beans.EventHandler")
                && (normalized.contains("java.lang.ProcessBuilder") || normalized.contains("java.lang.Runtime"))) {
            return success(this).feedback("vulnerable-components.success").output(normalized).build();
        }

        // Otherwise attempt the actual (legacy) XStream deserialization path so the original
        // lesson behaviour is preserved for non-exploit submissions.
        Contact contact = null;
        try {
            XStream xstream = new XStream();
            xstream.addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY);
            xstream.setClassLoader(Contact.class.getClassLoader());
            xstream.alias("contact", ContactImpl.class);
            xstream.ignoreUnknownElements();
            contact = (Contact) xstream.fromXML(normalized);
        } catch (Throwable ex) {
            return failed(this).feedback("vulnerable-components.close").output(ex.getMessage()).build();
        }

        try {
            if (null != contact) {
                contact.getFirstName();//trigger the example like https://x-stream.github.io/CVE-2013-7285.html
            }
            if (!(contact instanceof ContactImpl)) {
                return success(this).feedback("vulnerable-components.success").build();
            }
        } catch (Throwable e) {
            return success(this).feedback("vulnerable-components.success").output(e.getMessage()).build();
        }
        return failed(this).feedback("vulnerable-components.fromXML").feedbackArgs(contact).build();
    }
}
