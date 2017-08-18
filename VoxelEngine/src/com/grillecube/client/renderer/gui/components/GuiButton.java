package com.grillecube.client.renderer.gui.components;

import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextAlignLeft;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextCenterYBox;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import com.grillecube.common.maths.Vector4f;

public class GuiButton extends GuiLabel {

	private static final Vector4f HOVERED_COLOR = new Vector4f(0.6f, 0.6f, 1.0f, 1.0f);
	private static final Vector4f OUT_COLOR = new Vector4f(0.87f, 0.87f, 0.87f, 1.0f);
	private static final Vector4f PRESSED_COLOR = new Vector4f(0.5f, 0.5f, 0.9f, 1.0f);
	private static final Vector4f DISABLED_COLOR = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f);

	private final GuiColoredQuad bg;
	private final Vector4f hoveredColor;
	private final Vector4f outColor;
	private final Vector4f pressedColor;
	private final Vector4f disabledColor;

	public GuiButton() {
		super();

		this.hoveredColor = new Vector4f();
		this.outColor = new Vector4f();
		this.pressedColor = new Vector4f();
		this.disabledColor = new Vector4f();

		this.bg = new GuiColoredQuad();
		this.addChild(0, this.bg);

		this.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
		this.addTextParameter(new GuiTextParameterTextCenterYBox());
		this.addTextParameter(new GuiTextParameterTextAlignLeft(0.1f));

		this.setHoveredColor(HOVERED_COLOR);
		this.setOutColor(OUT_COLOR);
		this.setPressedColor(PRESSED_COLOR);
		this.setDisabledColor(DISABLED_COLOR);

		this.setSelectable(false);
		this.setSelected(false);
		this.setEnabled(true);
	}

	@Override
	public final void onUpdate(float mx, float my, boolean pressed) {
		super.onUpdate(mx, my, pressed);
		this.updateColor();
	}

	private final void updateColor() {
		this.bg.setColor(!this.isEnabled() ? this.disabledColor
				: this.isHovered() ? this.hoveredColor : this.isSelected() ? this.pressedColor : this.outColor);
	}

	public final void setHoveredColor(Vector4f color) {
		this.setHoveredColor(color.x, color.y, color.z, color.w);
	}

	public final void setHoveredColor(float r, float g, float b, float a) {
		this.hoveredColor.set(r, g, b, a);
		this.updateColor();
	}

	public final void setOutColor(Vector4f color) {
		this.setOutColor(color.x, color.y, color.z, color.w);
	}

	public final void setOutColor(float r, float g, float b, float a) {
		this.outColor.set(r, g, b, a);
		this.updateColor();
	}

	public final void setPressedColor(Vector4f color) {
		this.setPressedColor(color.x, color.y, color.z, color.w);
	}

	public final void setPressedColor(float r, float g, float b, float a) {
		this.pressedColor.set(r, g, b, a);
		this.updateColor();
	}

	public final void setDisabledColor(Vector4f color) {
		this.setDisabledColor(color.x, color.y, color.z, color.w);
	}

	public final void setDisabledColor(float r, float g, float b, float a) {
		this.disabledColor.set(r, g, b, a);
		this.updateColor();
	}
}
