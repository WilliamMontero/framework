/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.client.ui.datefield;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.VAbstractTextualDate;
import com.vaadin.shared.ui.datefield.AbstractTextualDateFieldState;

public abstract class AbstractTextualDateConnector<R extends Enum<R>>
        extends AbstractDateFieldConnector<R> {

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        R origRes = getWidget().getCurrentResolution();
        String oldLocale = getWidget().getCurrentLocale();
        super.updateFromUIDL(uidl, client);
        if (origRes != getWidget().getCurrentResolution()
                || oldLocale != getWidget().getCurrentLocale()) {
            // force recreating format string
            getWidget().formatStr = null;
        }
        if (uidl.hasAttribute("format")) {
            getWidget().formatStr = uidl.getStringAttribute("format");
        }

        getWidget().lenient = !uidl.getBooleanAttribute("strict");

        getWidget().buildDate();
        // not a FocusWidget -> needs own tabindex handling
        getWidget().text.setTabIndex(getState().tabIndex);

        if (getWidget().isReadonly()) {
            getWidget().text.addStyleDependentName("readonly");
        } else {
            getWidget().text.removeStyleDependentName("readonly");
        }

    }

    @Override
    public VAbstractTextualDate<R> getWidget() {
        return (VAbstractTextualDate<R>) super.getWidget();
    }

    @Override
    public AbstractTextualDateFieldState getState() {
        return (AbstractTextualDateFieldState) super.getState();
    }

}
