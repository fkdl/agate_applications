/**
 * Copyright 2003-2007 Jive Software.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.smackx.workgroup.settings;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smackx.workgroup.util.ModelUtil;
import org.xmlpull.v2.XmlPullParser;

public class SearchSettings extends IQ {
	/**
	 * Packet extension provider for AgentStatusRequest packets.
	 */
	public static class InternalProvider implements IQProvider {

		@Override
		public IQ parseIQ(XmlPullParser parser) throws Exception {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				throw new IllegalStateException(
						"Parser not in proper position, or bad XML.");
			}

			final SearchSettings settings = new SearchSettings();

			boolean done = false;
			String kb = null;
			String forums = null;

			while (!done) {
				final int eventType = parser.next();
				if ((eventType == XmlPullParser.START_TAG)
						&& ("forums".equals(parser.getName()))) {
					forums = parser.nextText();
				} else if ((eventType == XmlPullParser.START_TAG)
						&& ("kb".equals(parser.getName()))) {
					kb = parser.nextText();
				} else if (eventType == XmlPullParser.END_TAG
						&& "search-settings".equals(parser.getName())) {
					done = true;
				}
			}

			settings.setForumsLocation(forums);
			settings.setKbLocation(kb);
			return settings;
		}
	}

	private String forumsLocation;

	private String kbLocation;

	/**
	 * Element name of the packet extension.
	 */
	public static final String ELEMENT_NAME = "search-settings";

	/**
	 * Namespace of the packet extension.
	 */
	public static final String NAMESPACE = "http://jivesoftware.com/protocol/workgroup";

	@Override
	public String getChildElementXML() {
		final StringBuilder buf = new StringBuilder();

		buf.append("<").append(ELEMENT_NAME).append(" xmlns=");
		buf.append('"');
		buf.append(NAMESPACE);
		buf.append('"');
		buf.append("></").append(ELEMENT_NAME).append("> ");
		return buf.toString();
	}

	public String getForumsLocation() {
		return forumsLocation;
	}

	public String getKbLocation() {
		return kbLocation;
	}

	public boolean hasForums() {
		return ModelUtil.hasLength(getForumsLocation());
	}

	public boolean hasKB() {
		return ModelUtil.hasLength(getKbLocation());
	}

	public boolean isSearchEnabled() {
		return ModelUtil.hasLength(getForumsLocation())
				&& ModelUtil.hasLength(getKbLocation());
	}

	public void setForumsLocation(String forumsLocation) {
		this.forumsLocation = forumsLocation;
	}

	public void setKbLocation(String kbLocation) {
		this.kbLocation = kbLocation;
	}
}