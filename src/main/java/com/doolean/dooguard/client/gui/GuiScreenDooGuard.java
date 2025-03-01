package com.doolean.dooguard.client.gui;

import net.minecraft.client.gui.*;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.*;
import java.util.List;

// TODO: реализовать функционал получения значений флагов
// TODO: не работают поля ввода значений флагов
// TODO: проверить, что поля ввода значений локации не перекрывают друг друга

public class GuiScreenDooGuard extends GuiScreen {
    public static final int VALUE_BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 20;
    public static final int LOCATION_INPUT_WIDTH = 50;
    private GuiTextField regionNameInput;
    private GuiTextField commandOutputField;

    GuiTextField flagValueInput = null;
    GuiTextField xInput = null;
    GuiTextField yInput = null;
    GuiTextField zInput = null;
    GuiTextField firstField = null;

    private final List<GuiTextField> flagValueSetList = new ArrayList<>();

    private String selectedTab = "";
    private Flag selectedFlag = new Flag();

    private String errorMessage = "";

    private static final int FLAGS_PER_ROW = 5;
    private static final int FLAG_BUTTON_WIDTH = 100;
    private static final int FLAG_BUTTON_HEIGHT = BUTTON_HEIGHT;
    private static final int PADDING = 10;


    private final int REGION_NAME_STARTX = 10;
    private static final int REGION_NAME_STARTY = 60;
    private static final int REGION_NAME_WIDTH = 150;

    private final int DESCRIPTION_STARTX = 10;
    private static final int DESCRIPTION_STARTY_BOTTOM = 200;
    private final int ERROR_STARTX = 10;
    private static final int ERROR_STARTY_BOTTOM = 60;
    private final int COMMAND_OUTPUT_WIDTH = 200;
    private final int COMMAND_OUTPUT_STARTX = 10;
    private static final int COMMAND_OUTPUT_STARTY_BOTTOM = 40;

    private static final int GENERATE_WIDTH = 100;
    private final int GENERATE_STARTX = COMMAND_OUTPUT_STARTX + COMMAND_OUTPUT_WIDTH + PADDING;


    private static final int VALUE_INPUT_STARTX = 10;
    private static final int VALUE_INPUT_STARTY = 300;

    private static final int TABS_STARTX = 10;
    private static final int TABS_STARTY = BUTTON_HEIGHT;

    private final List<Flag> flags = new ArrayList<>();
    private final List<String> tabs = new ArrayList<>();

    public static void addCategoriesToTabs(List<Flag> flags, List<String> tabs) {
        for (Flag flag : flags) {
            String category = flag.getCategory();  // Получаем категорию из объекта Flag
            if (!tabs.contains(category)) {  // Проверяем, есть ли уже такая категория в списке
                tabs.add(category);  // Если нет, добавляем категорию в список
            }
        }
    }

    public GuiScreenDooGuard() {
        FlagUtils.addFlags(flags);
        addCategoriesToTabs(flags, tabs);
        selectedTab = tabs.get(0);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Кнопки для вкладок
        for (int i = 0; i < tabs.size(); i++) {
            this.buttonList.add(new GuiButton(400 + i, TABS_STARTX + (i * 130), TABS_STARTY, 120, BUTTON_HEIGHT, tabs.get(i)));
        }

        // Поле ввода названия региона
        regionNameInput = new GuiTextField(0, this.fontRenderer, REGION_NAME_STARTX, REGION_NAME_STARTY, 150, BUTTON_HEIGHT);
        regionNameInput.setMaxStringLength(50);

        // Кнопки флагов
        updateFlagButtons();

        updateValueInput();

        // Поле вывода команды
        commandOutputField = new GuiTextField(2, this.fontRenderer, COMMAND_OUTPUT_STARTX, this.height - COMMAND_OUTPUT_STARTY_BOTTOM, COMMAND_OUTPUT_WIDTH, BUTTON_HEIGHT);
        commandOutputField.setMaxStringLength(100);
        commandOutputField.setEnabled(false);


        // Кнопка "Generate"
        this.buttonList.add(new GuiButton(300, GENERATE_STARTX, this.height - COMMAND_OUTPUT_STARTY_BOTTOM, GENERATE_WIDTH, BUTTON_HEIGHT, "Generate"));
    }

    private void updateValueInput() {
        this.buttonList.removeIf(button -> button.id >= 500 && button.id < 600);
        flagValueInput = null;
        xInput = null;
        yInput = null;
        zInput = null;
        firstField = null;
        flagValueSetList.clear();
        if (!selectedFlag.getType().isEmpty()) {
            errorMessage = "";
            String type = selectedFlag.getType();
            switch (type) {
                case "state":
                {
                    this.buttonList.add(new GuiButton(500, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "DENY"));
                    this.buttonList.add(new GuiButton(501, VALUE_INPUT_STARTX + VALUE_BUTTON_WIDTH + PADDING, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "ALLOW"));
                    break;
                }
                case "string":
                {
                    flagValueInput = new GuiTextField(1, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, 200, BUTTON_HEIGHT);
                    flagValueInput.setMaxStringLength(200);
                    break;
                }
                case "integer":
                case "double":
                {
                    flagValueInput = new GuiTextField(1, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, 50, BUTTON_HEIGHT);
                    flagValueInput.setMaxStringLength(50);
                    break;
                }
                case "location":
                {
                    xInput = new GuiTextField(1, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, LOCATION_INPUT_WIDTH, BUTTON_HEIGHT);
                    yInput = new GuiTextField(2, this.fontRenderer, VALUE_INPUT_STARTX + LOCATION_INPUT_WIDTH + PADDING, VALUE_INPUT_STARTY, LOCATION_INPUT_WIDTH, BUTTON_HEIGHT);
                    zInput = new GuiTextField(3, this.fontRenderer, VALUE_INPUT_STARTX + LOCATION_INPUT_WIDTH * 2 + PADDING * 2, VALUE_INPUT_STARTY, LOCATION_INPUT_WIDTH, BUTTON_HEIGHT);
                    xInput.setMaxStringLength(10);
                    yInput.setMaxStringLength(10);
                    zInput.setMaxStringLength(10);
                    break;
                }
                case "boolean":
                {
                    this.buttonList.add(new GuiButton(500, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "TRUE"));
                    this.buttonList.add(new GuiButton(501, VALUE_INPUT_STARTX + VALUE_BUTTON_WIDTH + PADDING, VALUE_INPUT_STARTY, 100, BUTTON_HEIGHT, "FALSE"));
                    break;
                }
                case "set":
                {
                    firstField = new GuiTextField(0, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, 100, BUTTON_HEIGHT);
                    firstField.setMaxStringLength(50);
                    this.flagValueSetList.add(firstField);
                    this.buttonList.add(new GuiButton(500, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY + 30, 100, BUTTON_HEIGHT, "Add more..."));
                    break;
                }
                default:{
                    errorMessage = "Flag is not specified. Flag type: " + type;
                    break;
                }
            }
        }
    }

    private void updateFlagButtons() {
        this.buttonList.removeIf(button -> button.id >= 200 && button.id < 300);

        int startX = this.width / 2 - (FLAGS_PER_ROW * FLAG_BUTTON_WIDTH) / 2;
        int startY = 100;
        int index = 0;

        for (Flag flag : flags) {
            if (flag.getCategory().equals(selectedTab)){
                int row = index / FLAGS_PER_ROW;
                int col = index % FLAGS_PER_ROW;
                this.buttonList.add(new GuiButton(200 + index, startX + (col * (FLAG_BUTTON_WIDTH + PADDING)), startY + (row * (FLAG_BUTTON_HEIGHT + PADDING)), FLAG_BUTTON_WIDTH, FLAG_BUTTON_HEIGHT, flag.getName()));
                index++;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id >= 400 && button.id < 400 + tabs.size()) { // Переключение вкладки
            selectedTab = tabs.get(button.id - 400);
            updateFlagButtons();
        } else if (button.id >= 200 && button.id < 300) { // Выбор флага
            List<Flag> categoryFlags = FlagUtils.filterFlagsByCategory(flags, selectedTab);
            selectedFlag = categoryFlags.get(button.id - 200);
            updateValueInput();
        } else if (button.id == 300) { // "Generate"
            generateCommand();
        }
    }

    private void generateCommand() {
        if (regionNameInput.getText().isEmpty() || selectedFlag.getName().isEmpty()) return;

        String value = "";
        String command = "/region flag " + regionNameInput.getText() + " " + selectedFlag.getName() + " " + value;

        commandOutputField.setText(command);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(command), null);
        mc.player.sendMessage(new TextComponentString("Command copied to clipboard: " + command));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        regionNameInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        regionNameInput.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int centerX = this.width / 2;

        this.drawCenteredString(this.fontRenderer, "DooGuard Configuration", centerX, 5, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Region Name", REGION_NAME_STARTX + REGION_NAME_WIDTH + PADDING, REGION_NAME_STARTY, 0xA0A0A0);
        regionNameInput.drawTextBox();

        if (selectedFlag != null) {
            this.drawString(this.fontRenderer, selectedFlag.getDescription(), DESCRIPTION_STARTX, DESCRIPTION_STARTY_BOTTOM, 0xFFFFFF);
        }

        if (flagValueInput != null) {
            flagValueInput.drawTextBox();
            flagValueInput.setEnabled(true);
        }
        if (xInput != null) {
            xInput.drawTextBox();
            xInput.setEnabled(true);
        }
        if (yInput != null) {
            yInput.drawTextBox();
            yInput.setEnabled(true);
        }
        if (zInput != null) {
            zInput.drawTextBox();
            zInput.setEnabled(true);
        }
        if (firstField != null) {
            firstField.drawTextBox();
            firstField.setEnabled(true);
        }

        if (!errorMessage.isEmpty()) {
            this.drawString(this.fontRenderer, errorMessage, ERROR_STARTX, ERROR_STARTY_BOTTOM, 0xFF0000);
        }

        commandOutputField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
