package com.doolean.dooguard.client.gui;

import net.minecraft.client.gui.*;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GuiScreenDooGuard extends GuiScreen {
    public static final int VALUE_BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 20;
    public static final int LOCATION_INPUT_WIDTH = 50;
    public static final int MAX_STRING_LENGTH = 200;
    public static final int MAX_NUMBER_LENGTH = 50;
    public static final int MAX_POSITION_LENGTH = 10;
    public static final int MAX_SET_SIZE = 24;
    public static final int SET_VALUES_PER_ROW = 8;
    private GuiTextField regionNameInput;
    private String regionName = "spawn";
    private final List<String> memberNames = new ArrayList<>(Arrays.asList("ALL", "MEMBERS", "OWNERS", "NONMEMBERS", "NONOWNERS"));
    private GuiTextField commandOutputField;

    GuiTextField flagValueInput = null;
    GuiTextField xInput = null;
    GuiTextField yInput = null;
    GuiTextField zInput = null;
    GuiTextField firstField = null;

    String value = "";
    String xValue, yValue, zValue;
    String members = "";

    private final List<GuiTextField> flagValueSetList = new ArrayList<>();

    private String selectedTab;
    private Flag selectedFlag = new Flag();

    private String errorMessage = "";

    private static final int FLAGS_PER_ROW = 5;
    private static final int FLAG_BUTTON_WIDTH = 100;
    private static final int FLAG_BUTTON_HEIGHT = BUTTON_HEIGHT;
    private static final int PADDING = 10;

    private final int REGION_NAME_STARTX = 10;
    private static final int REGION_NAME_STARTY = 60;
    private static final int REGION_NAME_WIDTH = 150;

    private static final int DESCRIPTION_STARTX = 10;
    private static final int DESCRIPTION_STARTY_BOTTOM = 200;
    private static final int ERROR_STARTX = 10;
    private static final int ERROR_STARTY_BOTTOM = 80;
    private static final int COMMAND_OUTPUT_STARTY_BOTTOM = 40;

    private static final int COPY_WIDTH = 100;

    private static final int VALUE_INPUT_STARTX = 10;
    private static final int VALUE_INPUT_STARTY = 300;

    private static final int TABS_STARTX = 10;
    private static final int TABS_STARTY = BUTTON_HEIGHT;

    private final List<Flag> flags = new ArrayList<>();
    private final List<String> tabs = new ArrayList<>();

    public static void addCategoriesToTabs(List<Flag> flags, List<String> tabs) {
        for (Flag flag : flags) {
            String category = flag.getCategory();
            if (!tabs.contains(category)) {
                tabs.add(category);
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

        for (int i = 0; i < tabs.size(); i++) {
            this.buttonList.add(new GuiButton(400 + i, TABS_STARTX + (i * 130), TABS_STARTY, 120, BUTTON_HEIGHT, tabs.get(i)));
        }

        regionNameInput = new GuiTextField(10, this.fontRenderer, REGION_NAME_STARTX, REGION_NAME_STARTY, 150, BUTTON_HEIGHT);
        regionNameInput.setText(regionName);
        regionNameInput.setMaxStringLength(50);

        this.buttonList.add(new GuiButton(800, REGION_NAME_STARTX + REGION_NAME_WIDTH + PADDING + VALUE_BUTTON_WIDTH, REGION_NAME_STARTY, 70, BUTTON_HEIGHT, memberNames.get(0)));
        this.buttonList.add(new GuiButton(801, REGION_NAME_STARTX + REGION_NAME_WIDTH + PADDING + VALUE_BUTTON_WIDTH + PADDING + 70, REGION_NAME_STARTY, 70, BUTTON_HEIGHT, memberNames.get(1)));
        this.buttonList.add(new GuiButton(802, REGION_NAME_STARTX + REGION_NAME_WIDTH + PADDING + VALUE_BUTTON_WIDTH + 2*(PADDING+70), REGION_NAME_STARTY, 70, BUTTON_HEIGHT, memberNames.get(2)));
        this.buttonList.add(new GuiButton(803, REGION_NAME_STARTX + REGION_NAME_WIDTH + PADDING + VALUE_BUTTON_WIDTH + 3*(PADDING+70), REGION_NAME_STARTY, 70, BUTTON_HEIGHT, memberNames.get(3)));
        this.buttonList.add(new GuiButton(804, REGION_NAME_STARTX + REGION_NAME_WIDTH + PADDING + VALUE_BUTTON_WIDTH + 4*(PADDING+70), REGION_NAME_STARTY, 70, BUTTON_HEIGHT, memberNames.get(4)));


        updateFlagButtons();

        updateValueInput();

        commandOutputField = new GuiTextField(2, this.fontRenderer, PADDING, this.height - COMMAND_OUTPUT_STARTY_BOTTOM, this.width - PADDING * 3 - COPY_WIDTH, BUTTON_HEIGHT);
        commandOutputField.setMaxStringLength(100);
        commandOutputField.setEnabled(false);

        this.buttonList.add(new GuiButton(300, PADDING + (this.width - PADDING * 3 - COPY_WIDTH) + PADDING, this.height - COMMAND_OUTPUT_STARTY_BOTTOM, COPY_WIDTH, BUTTON_HEIGHT, "Copy"));
    }

    private void updateValueInput() {
        this.buttonList.removeIf(button -> button.id >= 500 && button.id < 702);
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
                    flagValueInput.setMaxStringLength(MAX_STRING_LENGTH);
                    break;
                }
                case "integer":
                case "double":
                {
                    flagValueInput = new GuiTextField(1, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, 50, BUTTON_HEIGHT);
                    flagValueInput.setMaxStringLength(MAX_NUMBER_LENGTH);
                    break;
                }
                case "location":
                {
                    xInput = new GuiTextField(1, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, LOCATION_INPUT_WIDTH, BUTTON_HEIGHT);
                    yInput = new GuiTextField(2, this.fontRenderer, VALUE_INPUT_STARTX + LOCATION_INPUT_WIDTH + PADDING, VALUE_INPUT_STARTY, LOCATION_INPUT_WIDTH, BUTTON_HEIGHT);
                    zInput = new GuiTextField(3, this.fontRenderer, VALUE_INPUT_STARTX + LOCATION_INPUT_WIDTH * 2 + PADDING * 2, VALUE_INPUT_STARTY, LOCATION_INPUT_WIDTH, BUTTON_HEIGHT);
                    xInput.setMaxStringLength(MAX_POSITION_LENGTH);
                    yInput.setMaxStringLength(MAX_POSITION_LENGTH);
                    zInput.setMaxStringLength(MAX_POSITION_LENGTH);
                    break;
                }
                case "boolean":
                {
                    this.buttonList.add(new GuiButton(500, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "FALSE"));
                    this.buttonList.add(new GuiButton(501, VALUE_INPUT_STARTX + VALUE_BUTTON_WIDTH + PADDING, VALUE_INPUT_STARTY, 100, BUTTON_HEIGHT, "TRUE"));
                    break;
                }
                case "set of strings":
                {
                    firstField = new GuiTextField(0, this.fontRenderer, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, 100, BUTTON_HEIGHT);
                    firstField.setMaxStringLength(50);
                    this.flagValueSetList.add(firstField);

                    this.buttonList.add(new GuiButton(700, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY - 30, 100, BUTTON_HEIGHT, "Add"));
                    this.buttonList.add(new GuiButton(701, VALUE_INPUT_STARTX + 110, VALUE_INPUT_STARTY - 30, 100, BUTTON_HEIGHT, "Remove"));

                    break;
                }
                case "gamemode": {
                    this.buttonList.add(new GuiButton(502, VALUE_INPUT_STARTX, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "CREATIVE"));
                    this.buttonList.add(new GuiButton(503, VALUE_INPUT_STARTX + VALUE_BUTTON_WIDTH + PADDING, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "SURVIVAL"));
                    this.buttonList.add(new GuiButton(504, VALUE_INPUT_STARTX + VALUE_BUTTON_WIDTH * 2 + PADDING * 2, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "ADVENTURE"));
                    this.buttonList.add(new GuiButton(505, VALUE_INPUT_STARTX + VALUE_BUTTON_WIDTH * 3 + PADDING * 3, VALUE_INPUT_STARTY, VALUE_BUTTON_WIDTH, BUTTON_HEIGHT, "SPECTATOR"));
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

    private void setDefaultValue(String selectedFlagType){
        switch (selectedFlagType){
            case "state":{
                value = "deny";
                break;
            }
            case "string":
            case "set of strings": {
                value = "";
                break;
            }
            case "integer":
            case "double":{
                value = "0";
                break;
            }
            case "location":{
                xValue = yValue = zValue = "0";
                break;
            }
            case "boolean":{
                value = "false";
                break;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 300: {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(commandOutputField.getText()), null);
                mc.player.sendMessage(new TextComponentString("Command copied to clipboard: " + commandOutputField.getText()));
                break;
            }
            case 700: {
                if (flagValueSetList.size() < MAX_SET_SIZE) {
                    int rows = flagValueSetList.size() / SET_VALUES_PER_ROW;
                    int cols = flagValueSetList.size() % SET_VALUES_PER_ROW;

                    int startX = VALUE_INPUT_STARTX + (cols * 110);
                    int startY = VALUE_INPUT_STARTY + (rows * 30);

                    GuiTextField newField = new GuiTextField(flagValueSetList.size(), this.fontRenderer, startX, startY, 100, BUTTON_HEIGHT);
                    newField.setMaxStringLength(50);
                    flagValueSetList.add(newField);
                }
                break;
            }
            case 701: {
                if (flagValueSetList.size() > 1) {
                    flagValueSetList.remove(flagValueSetList.size() - 1);
                } else {
                    flagValueSetList.get(0).setText("");
                }
                break;
            }
            case 502: {
                value = "creative";
                generateCommand();
                break;
            }
            case 503: {
                value = "survival";
                generateCommand();
                break;
            }
            case 504: {
                value = "adventure";
                generateCommand();
                break;
            }
            case 505: {
                value = "spectator";
                generateCommand();
                break;
            }
            default:
                if (button.id >= 400 && button.id < 400 + tabs.size()) {
                    selectedTab = tabs.get(button.id - 400);
                    updateFlagButtons();
                } else if (button.id >= 200 && button.id < 300) {
                    List<Flag> categoryFlags = FlagUtils.filterFlagsByCategory(flags, selectedTab);
                    selectedFlag = categoryFlags.get(button.id - 200);
                    updateValueInput();
                    setDefaultValue(selectedFlag.getType());
                    generateCommand();
                } else if (button.id == 500 || button.id == 501) {
                    if (!selectedFlag.getName().isEmpty()) {
                        if (Objects.equals(selectedFlag.getType(), "state")) {
                            value = (button.id == 500) ? "deny" : "allow";
                        } else if (Objects.equals(selectedFlag.getType(), "boolean")) {
                            value = (button.id == 500) ? "false" : "true";
                        }
                        generateCommand();
                    }
                } else if (button.id >= 800 && button.id < 806) {
                    members = "-g " + memberNames.get(button.id - 800).toLowerCase();
                    generateCommand();
                }
                break;
        }
    }


    private void generateCommand() {
        if (selectedFlag.getName().isEmpty()) return;

        regionName = regionNameInput.getText().isEmpty() ? "spawn" : regionNameInput.getText();

        errorMessage = "";

        String flagType = selectedFlag.getType();

        switch (flagType) {
            case "string": {
                value = flagValueInput.getText();
                break;
            }
            case "integer": {
                value = flagValueInput.getText();
                if (!value.matches("\\d+")) {
                    errorMessage = "Specified integer value is invalid";
                    return;
                }
                break;
            }
            case "double": {
                value = flagValueInput.getText();
                if (!value.matches("\\d+(\\.\\d+)?")) {
                    errorMessage = "Specified double value is invalid";
                    return;
                }
                break;
            }
            case "location":{
                xValue = xInput.getText();
                yValue = yInput.getText();
                zValue = zInput.getText();
                if (!xValue.matches("-?\\d+(\\.\\d+)?") || !yValue.matches("-?\\d+(\\.\\d+)?") || !zValue.matches("-?\\d+(\\.\\d+)?")) {
                    errorMessage = "Specified location value is invalid";
                    return;
                }
                value = xValue + " " + yValue + " " + zValue;
                break;
            }
            case "set of strings": {
                StringBuilder valueBuilder = new StringBuilder();
                int size = flagValueSetList.size();
                for (int i = 0; i < size; i++) {
                    GuiTextField field = flagValueSetList.get(i);
                    if (!field.getText().isEmpty()) {
                        valueBuilder.append(field.getText());
                        if (i < size - 1) {
                            valueBuilder.append(", ");
                        }
                    }
                }
                value = valueBuilder.toString();
                break;
            }


        }
        if (members.isEmpty()) {
            members = "-g all";
        }
        String command = "/region flag " + regionName + " " + selectedFlag.getName() + " " + members + " " + value;

        commandOutputField.setText(command);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        regionNameInput.textboxKeyTyped(typedChar, keyCode);
        if (flagValueInput != null) flagValueInput.textboxKeyTyped(typedChar, keyCode);
        if (xInput != null) xInput.textboxKeyTyped(typedChar, keyCode);
        if (yInput != null) yInput.textboxKeyTyped(typedChar, keyCode);
        if (zInput != null) zInput.textboxKeyTyped(typedChar, keyCode);
        if (flagValueSetList.isEmpty()){ // to prevent double values
            if (firstField != null) firstField.textboxKeyTyped(typedChar, keyCode);
        }

        for (GuiTextField field : flagValueSetList) {
            field.textboxKeyTyped(typedChar, keyCode);
        }

        generateCommand();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        regionNameInput.mouseClicked(mouseX, mouseY, mouseButton);
        if (flagValueInput != null) flagValueInput.mouseClicked(mouseX, mouseY, mouseButton);
        if (xInput != null) xInput.mouseClicked(mouseX, mouseY, mouseButton);
        if (yInput != null) yInput.mouseClicked(mouseX, mouseY, mouseButton);
        if (zInput != null) zInput.mouseClicked(mouseX, mouseY, mouseButton);
        if (firstField != null) firstField.mouseClicked(mouseX, mouseY, mouseButton);

        for (GuiTextField field : flagValueSetList) {
            field.mouseClicked(mouseX, mouseY, mouseButton);
        }
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

        for (GuiTextField field : flagValueSetList) {
            field.drawTextBox();
            field.setEnabled(true);
        }

        if (!errorMessage.isEmpty()) {
            this.drawString(this.fontRenderer, errorMessage, ERROR_STARTX, this.height - ERROR_STARTY_BOTTOM, 0xFF0000);
        }

        commandOutputField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
