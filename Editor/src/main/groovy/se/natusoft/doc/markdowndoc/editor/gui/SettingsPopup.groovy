/*
 *
 * PROJECT
 *     Name
 *         MarkdownDocEditor
 *
 *     Code Version
 *         1.4
 *
 *     Description
 *         An editor that supports editing markdown with formatting preview.
 *
 * COPYRIGHTS
 *     Copyright (C) 2012 by Natusoft AB All rights reserved.
 *
 * LICENSE
 *     Apache 2.0 (Open Source)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 * AUTHORS
 *     Tommy Svensson (tommy@natusoft.se)
 *         Changes:
 *         2014-02-01: Created!
 *
 */
package se.natusoft.doc.markdowndoc.editor.gui

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.jetbrains.annotations.NotNull
import se.natusoft.doc.markdowndoc.editor.OSTrait
import se.natusoft.doc.markdowndoc.editor.adapters.WindowListenerAdapter
import se.natusoft.doc.markdowndoc.editor.config.ConfigEntry

import javax.swing.border.EmptyBorder
import java.util.List
import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowEvent

/**
 * The settings window.
 */
@CompileStatic
@TypeChecked
class SettingsPopup extends PopupWindow implements OSTrait {
    //
    // Private Members
    //

    /** The tabbed pane containing the group panels. */
    private JPanel groupPane = null

    /** The group panels for easy lookup using group name. */
    private Map<String, List<JComponent>> groupPanels = new HashMap<>()

    // This closure have to be defined at this level to be able to call the setWindowsVisibility() method.
    // If it is defined within a method it has access to method details, but not other class methods.
    private Closure<Void> closeWindow = {
        setWindowVisibility(false)
    }

    private ColumnTopDownLeftRightLayout contentLayout = new ColumnTopDownLeftRightLayout(
            vgap: 4,
            hgap: 4,
            leftMargin: 5,
            rightMargin: 5,
            topMargin: 10,
            bottomMargin: filterBottomMargin(30) // We need to reserve space for save and cancel button.
    )

    //
    // Properties
    //

    @NotNull Closure<Void> saveSettingsProvider

    @NotNull Closure<Void> cancelSettingsProvider

    @NotNull boolean fullScreenMode

    //
    // Methods
    //

    private void saveSettings() {
        this.saveSettingsProvider?.call()
    }

    private void cancelSettings() {
        this.cancelSettingsProvider?.call()
    }

    /**
     * Called when the top margin changes.
     *
     * @param _windowTopMargin The new top margin.
     */
    @Override
    protected void updateWindowTopMargin(final int _windowTopMargin) {
        super.updateWindowTopMargin(_windowTopMargin)
        updateBounds()
    }

    /**
     * Called when the new bottom margin changes.
     *
     * @param _windowBottomMargin The new bottom margin.
     */
    @Override
    protected void updateWindowBottomMargin(final int _windowBottomMargin) {
        super.updateWindowBottomMargin(_windowBottomMargin)
        updateBounds()
    }


    private void setupWindow() {
        super.title = "MarkdownDoc Editor Settings"

        layout = new BorderLayout()

        addWindowListener(new WindowListenerAdapter() {
            @Override
            void windowClosing(final WindowEvent ignored) {
                cancelSettings()
            }
        })

        this.contentLayout.screenSize =
                getDefaultScreen_Bounds(this.windowTopMargin, this.windowBottomMargin)

        this.groupPane = new JPanel(this.contentLayout)
        this.groupPane.border = null
        updateColors(this.groupPane)

        final JScrollPane scrollPane = new JScrollPane(
                this.groupPane,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        )
        scrollPane.border = new EmptyBorder(0, 0, 0, 0)
        scrollPane.viewportBorder = new EmptyBorder(0, 0, 0, 0)
        add(scrollPane, BorderLayout.CENTER)

        final JPanel buttons = new JPanel(new FlowLayout())
        updateColors(buttons)

        final MDEButton saveButton = new MDEButton(textColor: "black", text: "Save")
        updateColors(saveButton)
        saveButton.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(final ActionEvent ignored) {
                saveSettings()
                fadeOutWindow(closeWindow)
            }
        })
        buttons.add(saveButton)

        final MDEButton cancelButton = new MDEButton(textColor: "black", text: "Cancel")
        updateColors(cancelButton)
        cancelButton.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(final ActionEvent ignored) {
                cancelSettings()
                fadeOutWindow(closeWindow)
            }
        })
        buttons.add(cancelButton)

        add(buttons, BorderLayout.SOUTH)
    }

    void updateBounds() {

//        this.contentLayout.screenSize =
//                getDefaultScreen_Bounds(this.windowTopMargin, this.windowBottomMargin)

        final Dimension ps = preferredSize

        if (defaultScreen_Bounds.width - ps.width > 0) {
            setBounds(
                    (defaultScreen_Bounds.width - ps.width) as int,
                    (this.fullScreenMode ? 0i : this.windowTopMargin) as int,
                    ps.width as int,
                    (this.fullScreenMode ? (int)defaultScreen_Bounds.height :
                            (int)defaultScreen_Bounds.height - this.windowTopMargin - this.windowBottomMargin) as int
            )
        }
        else {
            setBounds(
                    0,
                    (this.fullScreenMode ? 0i : this.windowTopMargin) as int,
                    defaultScreen_Bounds.width as int,
                    (this.fullScreenMode ? (int)defaultScreen_Bounds.height :
                            (int)defaultScreen_Bounds.height - this.windowTopMargin - this.windowBottomMargin) as int
            )
        }
    }

    void setWindowVisibility(final boolean state) {
        if (state) {

            setupWindow()

            // Add config components to window.
            if (this.groupPane.componentCount == 0) {
                this.groupPanels.keySet().each { final String key ->
                    final List<JComponent> comps = this.groupPanels[key]
                    comps.each { final JComponent comp ->
                        this.groupPane.add(comp)
                    }
                }
            }

            updateBounds()
            safeOpacity = 0.0f
            visible = true


            fadeInWindow(this.popupOpacity)
        }
        else {
            visible = false
        }
    }

    /**
     * Adds a configuration to the settings window.
     *
     * @param configEntry The config entry to add.
     */
    void addConfig(@NotNull final ConfigEntry configEntry) {
        List<JComponent> groupList = this.groupPanels.get(configEntry.getConfigGroup())
        if (groupList == null) {
            groupList = new LinkedList<JComponent>()

            final JLabel groupTitle = new JLabel("---===:{ ${configEntry.configGroup} }:===---",
                    JLabel.CENTER)
            groupTitle.font = groupTitle.font.deriveFont(Font.BOLD)
            updateColors(groupTitle)
            groupList.add(groupTitle)

            this.groupPanels.put(configEntry.configGroup, groupList)
        }

        final ConfigValueEditor configEditPanel = new ConfigValueEditor(configEntry, this)
        updateColors(configEditPanel)
        groupList.add(configEditPanel)
    }

}