/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2018 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package org.jkiss.dbeaver.ext.erd.model;

import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.palette.*;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.jkiss.dbeaver.ext.erd.ERDActivator;
import org.jkiss.dbeaver.ext.erd.ERDMessages;
import org.jkiss.dbeaver.ext.erd.editor.ERDEditPartFactory;
import org.jkiss.dbeaver.ui.DBeaverIcons;
import org.jkiss.dbeaver.ui.UIIcon;

/**
 * ERD object adapter
 */
public class ERDDecoratorDefault implements ERDDecorator {

    public static final ImageDescriptor CONNECT_IMAGE = ERDActivator.getImageDescriptor("icons/connect.png");
    public static final ImageDescriptor NOTE_IMAGE = ERDActivator.getImageDescriptor("icons/note.png");

    @Override
    public boolean showCheckboxes() {
        return false;
    }

    @Override
    public EditPartFactory createPartFactory() {
        return new ERDEditPartFactory();
    }

    @Override
    public void fillPalette(PaletteRoot paletteRoot, boolean readOnly) {
        {
            // a group of default control tools
            PaletteDrawer controls = createToolsDrawer(paletteRoot);

            // the selection tool
            ToolEntry selectionTool = new SelectionToolEntry();
            controls.add(selectionTool);

            // use selection tool as default entry
            paletteRoot.setDefaultEntry(selectionTool);

            if (!readOnly) {
                // separator
                PaletteSeparator separator = new PaletteSeparator("tools");
                separator.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
                controls.add(separator);

                controls.add(new ConnectionCreationToolEntry(ERDMessages.erd_tool_create_connection, ERDMessages.erd_tool_create_connection_tip, null, CONNECT_IMAGE, CONNECT_IMAGE));
                controls.add(new CreationToolEntry(
                    ERDMessages.erd_tool_create_note,
                    ERDMessages.erd_tool_create_note_tip,
                    new CreationFactory() {
                        @Override
                        public Object getNewObject()
                        {
                            return new ERDNote(ERDMessages.erd_tool_create_default);
                        }
                        @Override
                        public Object getObjectType()
                        {
                            return RequestConstants.REQ_CREATE;
                        }
                    },
                    NOTE_IMAGE,
                    NOTE_IMAGE));
            }
        }
    }

    protected PaletteDrawer createToolsDrawer(PaletteRoot paletteRoot) {
        PaletteDrawer controls = new PaletteDrawer("Tools", DBeaverIcons.getImageDescriptor(UIIcon.CONFIGURATION));

        paletteRoot.add(controls);

        return controls;
    }

}
